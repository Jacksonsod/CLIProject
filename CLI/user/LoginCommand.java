package user;

import db.UserRepository;

public class LoginCommand {
    public static void execute(String credentials, UserRepository repo, String[] activeUser) {
        String[] creds = credentials.split(":");
        if (creds.length != 2) {
            System.out.println("Use format: login username:password");
            return;
        }

        String username = creds[0];
        String password = creds[1];

        if (repo.validateLogin(username, password)) {
            activeUser[0] = username;
            System.out.println("Logged in as " + username);
        } else {
            System.out.println("Invalid credentials.");
        }
    }
}