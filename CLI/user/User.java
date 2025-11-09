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

    public User(String username, String passwordHash, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.status = "active";
        this.createdAt = new Date();
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPassword(String newPasswordHash) { this.passwordHash = newPasswordHash; }
    public boolean checkPassword(String inputPassword) { return passwordHash.equals(inputPassword); }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getCreatedAt() { return createdAt; }
}
