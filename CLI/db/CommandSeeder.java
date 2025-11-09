package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommandSeeder {

    public static void seedCommands() {
        try (Connection conn = DBConnection.getConnection()) {

            // Insert actions
            insertActionIfMissing(conn, 1, "create", "Create a new entity");
            insertActionIfMissing(conn, 2, "delete", "Delete an existing entity");
            insertActionIfMissing(conn, 3, "list", "List entities");
            insertActionIfMissing(conn, 4, "switch", "Switch active user");
            insertActionIfMissing(conn, 5, "promote", "Promote user to admin");
            insertActionIfMissing(conn, 6, "login", "Authenticate user");
            insertActionIfMissing(conn, 7, "logout", "End session");
            insertActionIfMissing(conn, 8, "reset", "Reset user password");
            insertActionIfMissing(conn, 9, "change", "Change user password");
            insertActionIfMissing(conn, 10, "help", "Show help menu");
            insertActionIfMissing(conn, 11, "remove", "Remove an entity or item");

            // Insert objects
            insertObjectIfMissing(conn, 1, "user", "User account");
            insertObjectIfMissing(conn, 2, "folder", "File system folder");
            insertObjectIfMissing(conn, 3, "file", "File system file");
            insertObjectIfMissing(conn, 4, "password", "User password");
            insertObjectIfMissing(conn, 5, "session", "Login session");
            insertObjectIfMissing(conn, 6, "system", "System help or info");

            // Insert command map
            insertCommandIfMissing(conn, 1, "create user", "Register a new user", 1, 1);
            insertCommandIfMissing(conn, 2, "delete user", "Remove a user", 2, 1);
            insertCommandIfMissing(conn, 3, "list users", "List all users", 3, 1);
            insertCommandIfMissing(conn, 4, "switch user", "Switch active user", 4, 1);
            insertCommandIfMissing(conn, 5, "promote user", "Promote user to admin", 5, 1);
            insertCommandIfMissing(conn, 6, "login", "Authenticate and start session", 6, 5);
            insertCommandIfMissing(conn, 7, "logout", "End current session", 7, 5);
            insertCommandIfMissing(conn, 8, "reset password", "Admin resets user password", 8, 4);
            insertCommandIfMissing(conn, 9, "change password", "User changes own password", 9, 4);
            insertCommandIfMissing(conn, 10, "create folder", "Create a new folder", 1, 2);
            insertCommandIfMissing(conn, 11, "delete folder", "Delete a folder", 2, 2);
            insertCommandIfMissing(conn, 12, "list files", "List files in directory", 3, 3);
            insertCommandIfMissing(conn, 13, "create file", "Create a new file", 1, 3);
            insertCommandIfMissing(conn, 14, "delete file", "Delete a file", 2, 3);
            insertCommandIfMissing(conn, 15, "help", "Show help menu", 10, 6);
            insertCommandIfMissing(conn, 16, "remove file", "Remove a file from the system", 11, 3);

            // Monitor commands (use object: system (6); action: list (3) as generic info retrieval)
            insertCommandIfMissing(conn, 17, "history", "Show command history", 3, 6);
            insertCommandIfMissing(conn, 18, "log", "View CLI logs", 3, 6);
            insertCommandIfMissing(conn, 19, "status", "Show current session status", 3, 6);
            insertCommandIfMissing(conn, 20, "memory", "Show JVM memory usage", 3, 6);

            System.out.println("Command seeding completed.");

        } catch (SQLException e) {
            System.err.println("Error seeding commands: " + e.getMessage());
        }
    }

    private static void insertActionIfMissing(Connection conn, int id, String name, String desc) throws SQLException {
        if (!exists(conn, "command_actions", "action_name", name)) {
            String sql = "INSERT INTO command_actions (action_id, action_name, description, created_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.setString(2, name);
                stmt.setString(3, desc);
                stmt.executeUpdate();
            }
        }
    }

    private static void insertObjectIfMissing(Connection conn, int id, String name, String desc) throws SQLException {
        if (!exists(conn, "command_objects", "object_name", name)) {
            String sql = "INSERT INTO command_objects (object_id, object_name, description, created_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.setString(2, name);
                stmt.setString(3, desc);
                stmt.executeUpdate();
            }
        }
    }

    private static void insertCommandIfMissing(Connection conn, int id, String alias, String desc, int actionId, int objectId) throws SQLException {
        if (!exists(conn, "command_map", "command_alias", alias)) {
            String sql = "INSERT INTO command_map (command_id, action_id, object_id, command_alias, description, created_at) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.setInt(2, actionId);
                stmt.setInt(3, objectId);
                stmt.setString(4, alias);
                stmt.setString(5, desc);
                stmt.executeUpdate();
            }
        }
    }

    private static boolean exists(Connection conn, String table, String column, String value) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + table + " WHERE " + column + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, value);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}