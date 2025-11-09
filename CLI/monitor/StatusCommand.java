package monitor;

import app.Main;

public class StatusCommand {
    public static void execute(String activeUser, Main ui) {
        String user = (activeUser == null) ? "<none>" : activeUser;
        ui.appendOutput("Status:");
        ui.appendOutput("- Active user: " + user);

    }
}
