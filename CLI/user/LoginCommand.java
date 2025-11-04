
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
        String password = creds[1];

        User user = repo.findUser(username);
        if (user == null || !user.getPasswordHash().equals(password)) {
            ui.appendOutput("Invalid credentials.");
        } else {
            activeUser[0] = username;
            ui.appendOutput("Logged in as " + username);
        }
    }
}
