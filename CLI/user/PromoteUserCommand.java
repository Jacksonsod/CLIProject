package user;

import db.UserRepository;
import app.Main;

public class PromoteUserCommand {
    public static void execute(String input, UserRepository repo, String[] activeUser, Main ui) {
        if (!activeUser[0].equals("admin")) {
            ui.appendOutput("Only admin can promote users.");
            return;
        }

        User user = repo.findUser(input);
        if (user == null) {
            ui.appendOutput("User not found.");
        } else {
            user.setRole("admin");
            repo.updateUser(user);
            ui.appendOutput("User promoted to admin: " + user.getUsername());
        }
    }
}
