package user;

import java.util.List;

public class DeleteUserCommand {
    public static void execute(String username, List<User> users, String[] activeUser) {
        if (!"admin".equals(activeUser[0])) {
            System.out.println("Only admin can delete users.");
            return;
        }

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                users.remove(i);
                if (username.equals(activeUser[0])) activeUser[0] = null;
                System.out.println("User deleted: " + username);
                return;
            }
        }

        System.out.println("User not found.");
    }
}