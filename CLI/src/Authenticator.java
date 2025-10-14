package com.mycli.auth;

public class Authenticator {

    // Simple mock user credentials
    private static final String VALID_USER = "kenny";
    private static final String VALID_PASS = "tuyisenge";
    private String currentUser = null;

    public boolean login(String username, String password) {
        if (VALID_USER.equals(username) && VALID_PASS.equals(password)) {
            this.currentUser = username;
            return true;
        }
        return false;
    }

    public void logout() {
        this.currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public String getCurrentUser() {
        return currentUser;
    }
}