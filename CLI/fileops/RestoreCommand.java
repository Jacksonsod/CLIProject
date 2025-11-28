package fileops;

import app.Main;
import db.UserRepository;
import user.User;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class RestoreCommand {

    public static void execute(UserRepository repo, String activeUsername, Main ui) {
        if (activeUsername == null) {
            ui.appendOutput("No active user. Please log in as admin to run restore.");
            return;
        }

        User current = repo.findUser(activeUsername);
        if (current == null || current.getRole() == null || !current.getRole().equalsIgnoreCase("admin")) {
            ui.appendOutput("Only admin can perform restore.");
            return;
        }

        Path backupsRoot = Paths.get("backups");
        if (!Files.exists(backupsRoot) || !Files.isDirectory(backupsRoot)) {
            ui.appendOutput("No backups directory found. Nothing to restore.");
            return;
        }

        Path latestBackup = findLatestBackup(backupsRoot, ui);
        if (latestBackup == null) {
            ui.appendOutput("No backup sets found to restore.");
            return;
        }

        Path backupDataDir = latestBackup.resolve("data");
        if (!Files.exists(backupDataDir)) {
            ui.appendOutput("Selected backup has no data/ folder. Skipping file restore.");
        } else {
            boolean filesOk = restoreDataFolder(backupDataDir, Paths.get("data"), ui);
            if (filesOk) {
                ui.appendOutput("File restore completed from " + latestBackup.toAbsolutePath());
            } else {
                ui.appendOutput("File restore encountered errors. Check logs above.");
            }
        }

        // NOTE: Database is backed up to CSV files under backupFolder/db,
        // but automatic DB restore from CSV is not implemented here to avoid
        // accidental data loss. You can reload those CSVs manually using MySQL tools.
    }

    private static Path findLatestBackup(Path backupsRoot, Main ui) {
        try (Stream<Path> stream = Files.list(backupsRoot)) {
            Optional<Path> latest = stream
                    .filter(Files::isDirectory)
                    .filter(p -> p.getFileName().toString().startsWith("backup_"))
                    .max(Comparator.comparing(p -> p.getFileName().toString()));
            return latest.orElse(null);
        } catch (IOException e) {
            ui.appendOutput("Error while scanning backups directory: " + e.getMessage());
            return null;
        }
    }

    private static boolean restoreDataFolder(Path sourceDir, Path targetDir, Main ui) {
        try {
            // Ensure target base exists
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
            }

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
                    ui.appendOutput("Error restoring file/folder: " + path + " - " + e.getMessage());
                }
            });

            return true;
        } catch (IOException e) {
            ui.appendOutput("Failed to restore data folder: " + e.getMessage());
            return false;
        }
    }
}
