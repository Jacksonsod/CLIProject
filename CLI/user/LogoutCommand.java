package user;

public class LogoutCommand {
    public static void execute(String[] activeUser) {
        if (activeUser[0] != null) {
            System.out.println("" + activeUser[0] + " logged out.");
            activeUser[0] = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }
}