package user;

import java.util.Map;

public class ListUsersCommand {
    public static void execute(Map<String, String> users) {
        System.out.println("Registered Users:");
        for (String user : users.keySet()) {
            System.out.println("- " + user);
        }
    }
}