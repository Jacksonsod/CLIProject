package user;

import db.UserRepository;
import java.util.List;

public class ListUsersCommand {
    public static void execute(UserRepository repo) {
        List<User> users = repo.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users registered.");
            return;
        }

        System.out.println("Registered Users:");
        for (User u : users) {
            System.out.println("- " + u.getUsername());
        }
    }
}