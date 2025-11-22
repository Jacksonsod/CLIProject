package user;

import db.UserRepository;
import db.UserLogRepository;
import app.Main;

public class CreateUserCommand {
    public static void execute(String input, UserRepository repo, Main ui) {
        String[] creds = input.split(":");
        if (creds.length != 2) {
            ui.appendOutput("Use format: create user username:password");
            return;
        }

        String username = creds[0];
        String rawPassword = creds[1];
        String hashedPassword = PasswordUtil.hash(rawPassword);
        String role = "user";

        if (repo.findUser(username) != null) {
            ui.appendOutput("User already exists.");
        } else if (repo.createUser(username, hashedPassword, role)) {
            ui.appendOutput("User created: " + username);
            UserLogRepository.getInstance().logEvent(username, "User created", "CREATE_USER");
        } else {
            ui.appendOutput("User creation failed.");
        }
    }
}