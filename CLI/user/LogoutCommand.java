package user;

import app.Main;

public class LogoutCommand {
    public static void execute(String[] activeUser, Main ui) {
        activeUser[0] = null;
        ui.appendOutput("Logged out.");
    }
}
