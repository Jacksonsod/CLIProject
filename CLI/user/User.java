package user;

import java.util.Date;

public class User {
    private int userId;
    private String username;
    private String passwordHash;
    private String role;
    private String status;
    private Date createdAt;

    // Full constructor
    public User(int userId, String username, String passwordHash, String role, String status, Date createdAt) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Partial constructor for lightweight usage
    public User(String username, String passwordHash, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.status = "active";
        this.createdAt = new Date(); // default to now
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean checkPassword(String inputPassword) {
        return passwordHash.equals(inputPassword); // Replace with hash comparison later
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}