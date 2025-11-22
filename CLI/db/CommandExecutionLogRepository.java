package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommandExecutionLogRepository {
    private static final CommandExecutionLogRepository INSTANCE = new CommandExecutionLogRepository();

    private CommandExecutionLogRepository() {
    }

    public static CommandExecutionLogRepository getInstance() {
        return INSTANCE;
    }

    public void logCommand(String username, String inputArgs, String output) {
        Integer userId = null;
        Integer commandId = null;

        if (username != null && !username.isEmpty()) {
            userId = findUserIdByUsername(username);
        }

        if (inputArgs != null && !inputArgs.trim().isEmpty()) {
            commandId = findActionIdByCommand(inputArgs);
        }

        String sql = "INSERT INTO command_execution_log (user_id, command_id, input_args, output, timestamp) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (userId != null) {
                stmt.setInt(1, userId);
            } else {
                stmt.setNull(1, java.sql.Types.INTEGER);
            }

            if (commandId != null) {
                stmt.setInt(2, commandId);
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }

            stmt.setString(3, truncate(inputArgs, 200));
            stmt.setString(4, truncate(output, 200));

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error logging command execution: " + e.getMessage());
        }
    }

    private Integer findUserIdByUsername(String username) {
        String sql = "SELECT user_id FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding user id for logging: " + e.getMessage());
        }
        return null;
    }

    private Integer findActionIdByCommand(String inputArgs) {
        String trimmed = inputArgs.trim();
        if (trimmed.isEmpty()) {
            return null;
        }

        String[] parts = trimmed.split("\\s+");
        if (parts.length == 0) {
            return null;
        }

        String actionName = parts[0].toLowerCase();

        String sql = "SELECT action_id FROM command_actions WHERE action_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, actionName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("action_id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding action id for command logging: " + e.getMessage());
        }
        return null;
    }

    private String truncate(String value, int maxLength) {
        if (value == null) return null;
        if (value.length() <= maxLength) return value;
        return value.substring(0, maxLength);
    }
}
