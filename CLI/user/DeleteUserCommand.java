package user;

import db.UserRepository;
import app.Main;

public class DeleteUserCommand {
    public static void execute(String input, UserRepository repo, String[] activeUser, Main ui) {
        User current = repo.findUser(activeUser[0]);
        if (current == null || current.getRole() == null || !current.getRole().equalsIgnoreCase("admin")) {
            ui.appendOutput("Only admin can delete users.");
            return;
        }

        if (repo.deleteUser(input)) {
            ui.appendOutput("User deleted: " + input);
        } else {
            ui.appendOutput("User not found.");
        }
    }
}
