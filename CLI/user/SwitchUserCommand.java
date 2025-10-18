package user;

import db.UserRepository;

public class SwitchUserCommand {
    public static void execute(String username, UserRepository repo, String[] activeUser) {
        if (repo.findUser(username) != null) {
            activeUser[0] = username;
            System.out.println("Switched to user: " + username);
        } else {
            System.out.println("User not found.");
        }
    }
}