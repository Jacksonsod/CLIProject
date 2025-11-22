package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLogRepository {
    private static final UserLogRepository INSTANCE = new UserLogRepository();

    private UserLogRepository() {
    }

    public static UserLogRepository getInstance() {
        return INSTANCE;
    }

    public void logEvent(String username, String eventDetail, String eventType) {
        Integer userId = null;

        if (username != null && !username.isEmpty()) {
            userId = findUserIdByUsername(username);
        }

        String sql = "INSERT INTO user_log (user_id, event_detail, event_type, timestamp) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (userId != null) {
                stmt.setInt(1, userId);
            } else {
                stmt.setNull(1, java.sql.Types.INTEGER);
            }

            stmt.setString(2, truncate(eventDetail, 50));
            stmt.setString(3, truncate(eventType, 20));

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error logging user event: " + e.getMessage());
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
            System.err.println("Error finding user id for user_log: " + e.getMessage());
        }
        return null;
    }

    private String truncate(String value, int maxLength) {
        if (value == null) return null;
        if (value.length() <= maxLength) return value;
        return value.substring(0, maxLength);
    }
}
