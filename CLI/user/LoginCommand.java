package user;

import java.util.List;

public class LoginCommand {
    public static void execute(String credentials, List<User> userList, String[] activeUser) {
        String[] creds = credentials.split(":");
        if (creds.length != 2) {
            System.out.println("Use format: login username:password");
            return;
        }

        String username = creds[0];
        String password = creds[1];

        for (User u : userList) {
            if (u.getUsername().equals(username) && u.checkPassword(password)) {
                activeUser[0] = username;
                System.out.println("Logged in as " + username);
                return;
            }
        }

        System.out.println("Invalid credentials.");
    }
}