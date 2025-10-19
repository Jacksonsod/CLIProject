
package db;

import user.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final String DB_URL = "jdbc:oracle:thin:@//localhost:1521/commandline";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "123";

    public boolean createUser(String username, String passwordHash) {
        String sql = "INSERT INTO users (user_id, username, password_hash, role, status, created_at) " +
             "VALUES (users_seq.NEXTVAL, ?, ?, 'user', 'active', SYSDATE)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Create error: " + e.getMessage());
            return false;
        }
    }

    public User findUser(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getString("role"),
                    rs.getString("status"),
                    rs.getDate("created_at")
                );
            }
        } catch (SQLException e) {
            System.out.println("Find error: " + e.getMessage());
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getString("role"),
                    rs.getString("status"),
                    rs.getDate("created_at")
                ));
            }
        } catch (SQLException e) {
            System.out.println("List error: " + e.getMessage());
        }
        return users;
    }

    public boolean deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Delete error: " + e.getMessage());
            return false;
        }
    }

    public boolean validateLogin(String username, String passwordHash) {
        User user = findUser(username);
        return user != null && user.checkPassword(passwordHash);
    }
}