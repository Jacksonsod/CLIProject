package db;

import user.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRepository {

    public User findUser(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getString("role"),
                    rs.getString("status"),
                    rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error finding user: " + e.getMessage());
        }
        return null;
    }

    public boolean createUser(String username, String passwordHash, String role) {
        String query = "INSERT INTO users (user_id, username, password_hash, role, status, created_at) " +
                       "VALUES (users_seq.NEXTVAL, ?, ?, ?, 'active', CURRENT_TIMESTAMP)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            stmt.setString(3, role);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteUser(String username) {
        String query = "DELETE FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
        return false;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getString("role"),
                    rs.getString("status"),
                    rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error listing users: " + e.getMessage());
        }
        return users;
    }

    public boolean validateLogin(String username, String passwordHash) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password_hash = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Error validating login: " + e.getMessage());
        }
        return false;
    }

    public boolean updatePassword(String username, String newPasswordHash) {
        String query = "UPDATE users SET password_hash = ? WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPasswordHash);
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
        }
        return false;
    }

    public boolean promoteUserToAdmin(String username) {
        String query = "UPDATE users SET role = 'admin' WHERE username = ? AND role != 'admin'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error promoting user: " + e.getMessage());
        }
        return false;
    }

    public boolean updateUser(User user) {
        String query = "UPDATE users SET password_hash = ?, role = ?, status = ? WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getPasswordHash());
            stmt.setString(2, user.getRole());
            stmt.setString(3, user.getStatus());
            stmt.setString(4, user.getUsername());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
        return false;
    }
}
