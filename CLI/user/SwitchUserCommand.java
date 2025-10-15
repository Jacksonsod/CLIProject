package user;

import java.util.List;

public class SwitchUserCommand {
    public static void execute(String username, List<User> users, String[] activeUser) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                activeUser[0] = username;
                System.out.println("Switched to user: " + username);
                return;
            }
        }

        System.out.println("User not found.");
    }
}