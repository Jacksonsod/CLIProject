package fileops;

import app.Main;
import db.DBConnection;
import db.UserRepository;
import user.User;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupCommand {

    public static void execute(UserRepository repo, String activeUsername, Main ui) {
        if (activeUsername == null) {
            ui.appendOutput("No active user. Please log in as admin to run backup.");
            return;
        }

        User current = repo.findUser(activeUsername);
        if (current == null || current.getRole() == null || !current.getRole().equalsIgnoreCase("admin")) {
            ui.appendOutput("Only admin can perform backup.");
            return;
        }

        if (!isDatabaseReachable()) {
            ui.appendOutput("Backup aborted: database not reachable. Please check your internet/LAN connection.");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = now.format(fmt);

        Path backupRoot = Paths.get("backups");
        Path backupDir = backupRoot.resolve("backup_" + timestamp);

        try {
            Files.createDirectories(backupDir);
        } catch (IOException e) {
            ui.appendOutput("Failed to create backup directory: " + e.getMessage());
            return;
        }

        boolean filesOk = backupDataFolder(backupDir.resolve("data"), ui);
        boolean dbOk = backupEntireDatabase(backupDir.resolve("db"), ui);

        if (filesOk && dbOk) {
            ui.appendOutput("Backup completed successfully at " + backupDir.toAbsolutePath());
        } else if (!filesOk && !dbOk) {
            ui.appendOutput("Backup failed: both file and database backup encountered errors.");
        } else if (!filesOk) {
            ui.appendOutput("Backup completed with warnings: file backup failed, database backup succeeded.");
        } else {
            ui.appendOutput("Backup completed with warnings: database backup failed, file backup succeeded.");
        }
    }

    private static boolean isDatabaseReachable() {
        try (Connection conn = DBConnection.getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    private static boolean backupDataFolder(Path targetDir, Main ui) {
        Path sourceDir = Paths.get("data");
        if (!Files.exists(sourceDir)) {
            ui.appendOutput("Data folder does not exist. Skipping file backup.");
            return true; // Not a hard failure if there is simply no data yet
        }

        try {
            Files.walk(sourceDir).forEach(path -> {
                try {
                    Path relative = sourceDir.relativize(path);
                    Path dest = targetDir.resolve(relative);
                    if (Files.isDirectory(path)) {
                        Files.createDirectories(dest);
                    } else {
                        if (dest.getParent() != null) {
                            Files.createDirectories(dest.getParent());
                        }
                        Files.copy(path, dest, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                    }
                } catch (IOException e) {
                    ui.appendOutput("Error backing up file/folder: " + path + " - " + e.getMessage());
                }
            });
            return true;
        } catch (IOException e) {
            ui.appendOutput("Failed to backup data folder: " + e.getMessage());
            return false;
        }
    }

    private static boolean backupEntireDatabase(Path dbBackupDir, Main ui) {
        try (Connection conn = DBConnection.getConnection()) {
            if (dbBackupDir.getParent() != null) {
                Files.createDirectories(dbBackupDir);
            }

            String catalog = conn.getCatalog();
            if (catalog == null) {
                ui.appendOutput("Unable to determine current database catalog for backup.");
                return false;
            }

            java.sql.DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet tables = meta.getTables(catalog, null, "%", new String[]{"TABLE"})) {
                boolean anyTable = false;
                while (tables.next()) {
                    anyTable = true;
                    String tableName = tables.getString("TABLE_NAME");
                    Path tableFile = dbBackupDir.resolve(tableName + ".csv");
                    if (!backupSingleTable(conn, tableName, tableFile, ui)) {
                        ui.appendOutput("Warning: failed to backup table " + tableName);
                    }
                }
                if (!anyTable) {
                    ui.appendOutput("No tables found to backup in database " + catalog + ".");
                }
            }

            return true;
        } catch (SQLException | IOException e) {
            ui.appendOutput("Failed to backup database: " + e.getMessage());
            return false;
        }
    }

    private static boolean backupSingleTable(Connection conn, String tableName, Path outputFile, Main ui) {
        String sql = "SELECT * FROM " + tableName;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (outputFile.getParent() != null) {
                Files.createDirectories(outputFile.getParent());
            }

            try (BufferedWriter writer = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

                java.sql.ResultSetMetaData rsMeta = rs.getMetaData();
                int columnCount = rsMeta.getColumnCount();

                // header
                StringBuilder header = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    if (i > 1) header.append(',');
                    header.append(safeCsv(rsMeta.getColumnLabel(i)));
                }
                writer.write(header.toString());
                writer.newLine();

                // rows
                while (rs.next()) {
                    StringBuilder row = new StringBuilder();
                    for (int i = 1; i <= columnCount; i++) {
                        if (i > 1) row.append(',');
                        Object value = rs.getObject(i);
                        row.append(safeCsv(value == null ? null : String.valueOf(value)));
                    }
                    writer.write(row.toString());
                    writer.newLine();
                }
            }

            return true;
        } catch (SQLException | IOException e) {
            ui.appendOutput("Failed to backup table " + tableName + ": " + e.getMessage());
            return false;
        }
    }

    private static String safeCsv(String value) {
        if (value == null) return "";
        String escaped = value.replace("\"", "\"\"");
        if (escaped.contains(",") || escaped.contains("\n") || escaped.contains("\r")) {
            return "\"" + escaped + "\"";
        }
        return escaped;
    }
}
