package user;

import db.UserRepository;
import app.Main;

public class SwitchUserCommand {
    public static void execute(String input, UserRepository repo, String[] activeUser, Main ui) {
        User user = repo.findUser(input);
        if (user == null) {
            ui.appendOutput("User not found.");
        } else {
            activeUser[0] = user.getUsername();
            ui.appendOutput("Switched to user: " + user.getUsername());
        }
    }
}
