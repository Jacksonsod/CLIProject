package user;

import db.UserRepository;
import app.Main;

public class ChangePasswordCommand {
    public static void execute(String input, User current, UserRepository repo, Main ui) {
        String[] parts = input.split(":");
        if (parts.length != 2) {
            ui.appendOutput("Use format: change password old:new");
            return;
        }

        String oldPass = parts[0];
        String newPass = parts[1];

        String oldHash = PasswordUtil.hash(oldPass);
        String newHash = PasswordUtil.hash(newPass);

        if (!current.getPasswordHash().equals(oldHash)) {
            ui.appendOutput("Incorrect current password.");
        } else {
            current.setPassword(newHash);
            repo.updateUser(current);
            ui.appendOutput("Password changed.");
        }
    }
}
