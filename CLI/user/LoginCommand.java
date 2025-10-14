package user;

import java.util.Map;

public class LoginCommand {
    public static void execute(String credentials, Map<String, String> users, String[] activeUser) {
        String[] creds = credentials.split(":");
        if (creds.length == 2 && users.containsKey(creds[0]) && users.get(creds[0]).equals(creds[1])) {
            activeUser[0] = creds[0];
            System.out.println("Logged in as " + activeUser[0]);
        } else {
            System.out.println("Invalid credentials.");
        }
    }
}