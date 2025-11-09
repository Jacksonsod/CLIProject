package user;

import db.UserRepository;
import app.Main;

public class LoginCommand {
    public static void execute(String input, UserRepository repo, String[] activeUser, Main ui) {
        String[] creds = input.split(":");
        if (creds.length != 2) {
            ui.appendOutput("Use format: login username:password");
            return;
        }

        String username = creds[0];
        String rawPassword = creds[1];
        String hashedInput = PasswordUtil.hash(rawPassword); // Hash input

        User user = repo.findUser(username);
        if (user == null) {
            ui.appendOutput("User not found.");
        } else if (!user.getPasswordHash().equals(hashedInput)) {
            ui.appendOutput("Incorrect password.");
        } else {
            activeUser[0] = username;
            ui.appendOutput("Login successful.");
        }
    }
}