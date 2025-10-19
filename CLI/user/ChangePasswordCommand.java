
package user;

import db.UserRepository;

public class ChangePasswordCommand {
    public static void execute(String input, User currentUser, UserRepository repo) {
        String[] parts = input.split(":");
        if (parts.length != 2) {
            System.out.println("Use format: change password oldPassword:newPassword");
            return;
        }

        String oldPassword = parts[0];
        String newPassword = parts[1];

        if (!currentUser.checkPassword(oldPassword)) {
            System.out.println("Incorrect current password.");
            return;
        }

        boolean success = repo.updatePassword(currentUser.getUsername(), newPassword);
        if (success) {
            System.out.println("Password updated successfully.");
        } else {
            System.out.println("Password update failed.");
        }
    }
}