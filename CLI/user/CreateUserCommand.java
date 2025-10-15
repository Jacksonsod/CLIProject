package user;

import java.util.List;

public class CreateUserCommand {
    public static void execute(String input, List<User> userList) {
        String[] creds = input.split(":");
        if (creds.length != 2) {
            System.out.println("Use format: create user username:password");
            return;
        }

        String username = creds[0];
        String password = creds[1];

        for (User u : userList) {
            if (u.getUsername().equals(username)) {
                System.out.println("User already exists.");
                return;
            }
        }

        userList.add(new User(username, password));
        System.out.println("User created: " + username);
    }
}