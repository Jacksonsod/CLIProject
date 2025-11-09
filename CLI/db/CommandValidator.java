
package db;

import java.sql.*;

public class CommandValidator {
    public static boolean isValidCommand(Connection conn, String alias) throws SQLException {
        String sql = "SELECT COUNT(*) FROM command_map WHERE command_alias = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, alias);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}