package user;

import db.UserRepository;
import app.Main;

public class ResetPasswordCommand {
    public static void execute(String input, User current, UserRepository repo, Main ui) {
        if (!current.getRole().equals("admin")) {
            ui.appendOutput("Only admin can reset passwords.");
            return;
        }

        String[] parts = input.split(":");
        if (parts.length != 2) {
            ui.appendOutput("Use format: reset password username:newpass");
            return;
        }

        String username = parts[0];
        String newPass = parts[1];

        User user = repo.findUser(username);
        if (user == null) {
            ui.appendOutput("User not found.");
        } else {
            String newHash = PasswordUtil.hash(newPass);
            user.setPassword(newHash);
            repo.updateUser(user);
            ui.appendOutput("Password reset for user: " + username);
        }
    }
}
