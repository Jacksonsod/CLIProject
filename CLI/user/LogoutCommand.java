package user;

import app.Main;
import db.UserLogRepository;

public class LogoutCommand {
    public static void execute(String[] activeUser, Main ui) {
        String username = activeUser[0];
        activeUser[0] = null;
        ui.appendOutput("Logged out.");
        if (username != null) {
            UserLogRepository.getInstance().logEvent(username, "Logged out", "LOGOUT");
        }
    }
}
