
package user;

import db.UserRepository;
import app.Main;
import java.util.List;

public class ListUsersCommand {
    public static void execute(UserRepository repo, Main ui) {
        List<User> users = repo.getAllUsers();
        if (users.isEmpty()) {
            ui.appendOutput("No users registered.");
            return;
        }

        ui.appendOutput("Registered Users:");
        for (User u : users) {
            ui.appendOutput("- " + u.getUsername());
        }
    }
}
