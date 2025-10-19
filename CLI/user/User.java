package user;

import java.util.Date;

public class User {
    private int userId;
    private String username;
    private String passwordHash;
    private String role;
    private String status;
    private Date createdAt;

    public User(int userId, String username, String passwordHash, String role, String status, Date createdAt) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
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
}