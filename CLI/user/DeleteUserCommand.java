package user;

import db.UserRepository;

public class DeleteUserCommand {
    public static void execute(String username, UserRepository repo, String[] activeUser) {
        if (!"admin".equals(activeUser[0])) {
            System.out.println("Only admin can delete users.");
            return;
        }

        if (repo.findUser(username) == null) {
            System.out.println("User not found.");
        } else if (repo.deleteUser(username)) {
            if (username.equals(activeUser[0])) activeUser[0] = null;
            System.out.println("User deleted: " + username);
        } else {
            System.out.println("Deletion failed.");
        }
    }
}