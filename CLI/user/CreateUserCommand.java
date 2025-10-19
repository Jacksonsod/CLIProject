package user;

import db.UserRepository;

public class CreateUserCommand {
    public static void execute(String input, UserRepository repo) {
        String[] creds = input.split(":");
        if (creds.length != 2) {
            System.out.println("Use format: create user username:password");
            return;
        }

        String username = creds[0];
        String password = creds[1];
        String role = "user";

        if (repo.findUser(username) != null) {
            System.out.println("User already exists.");
        } else if (repo.createUser(username, password, role)) {
            System.out.println("User created: " + username);
        } else {
            System.out.println("User creation failed.");
        }
    }
}