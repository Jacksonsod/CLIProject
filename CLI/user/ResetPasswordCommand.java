
package user;

import db.UserRepository;

public class ResetPasswordCommand {
    public static void execute(String input, User currentUser, UserRepository repo) {
        if (!currentUser.getRole().equals("admin")) {
            System.out.println("Only admins can reset passwords.");
            return;
        }

        String[] parts = input.split(":");
        if (parts.length != 2) {
            System.out.println("Use format: reset password username:newPassword");
            return;
        }

        String targetUser = parts[0];
        String newPassword = parts[1];

        boolean success = repo.updatePassword(targetUser, newPassword);
        if (success) {
            System.out.println("Password reset for user: " + targetUser);
        } else {
            System.out.println("Password reset failed.");
        }
    }
}