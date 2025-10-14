package user;

import java.util.Map;

public class SwitchUserCommand {
    public static void execute(String username, Map<String, String> users, String[] activeUser) {
        if (users.containsKey(username)) {
            activeUser[0] = username;
            System.out.println("Switched to user: " + username);
        } else {
            System.out.println("User not found.");
        }
    }
}