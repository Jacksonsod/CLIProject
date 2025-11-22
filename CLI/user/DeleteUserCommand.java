package user;

import db.UserRepository;
import db.UserLogRepository;
import app.Main;

public class DeleteUserCommand {
    public static void execute(String input, UserRepository repo, String[] activeUser, Main ui) {
        if (!activeUser[0].equals("admin")) {
            ui.appendOutput("Only admin can delete users.");
            return;
        }

        if (repo.deleteUser(input)) {
            ui.appendOutput("User deleted: " + input);
            UserLogRepository.getInstance().logEvent(input, "User deleted", "DELETE_USER");
        } else {
            ui.appendOutput("User not found.");
        }
    }
}
