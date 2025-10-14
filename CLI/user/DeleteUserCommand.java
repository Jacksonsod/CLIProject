package user;

import java.util.Map;

public class DeleteUserCommand {
    public static void execute(String username, Map<String, String> users, String[] activeUser) {
        if (!"admin".equals(activeUser[0])) {
            System.out.println("⛔ Only admin can delete users.");
            return;
        }

        if (users.containsKey(username)) {
            users.remove(username);
            if (username.equals(activeUser[0])) activeUser[0] = null;
            System.out.println("🗑️ User deleted: " + username);
        } else {
            System.out.println("⚠️ User not found.");
        }
    }
}