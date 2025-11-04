package user;

import db.UserRepository;
import app.Main;

public class CreateUserCommand {
    public static void execute(String input, UserRepository repo, Main ui) {
        String[] creds = input.split(":");
        if (creds.length != 2) {
            ui.appendOutput("Use format: create user username:password");
            return;
        }

        String username = creds[0];
        String password = creds[1];
        String role = "user";

        if (repo.findUser(username) != null) {
            ui.appendOutput("User already exists.");
        } else if (repo.createUser(username, password, role)) {
            ui.appendOutput("User created: " + username);
        } else {
            ui.appendOutput("User creation failed.");
        }
    }
}
