package user;

import java.util.Map;

public class CreateUserCommand {
    public static void execute(String input, Map<String, String> users) {
        String[] creds = input.split(":");
        if (creds.length == 2) {
            if (users.containsKey(creds[0])) {
                System.out.println("User already exists.");
            } else {
                users.put(creds[0], creds[1]);
                System.out.println("User created: " + creds[0]);
            }
        } else {
            System.out.println("Use format: create-user username:password");
        }
    }
}