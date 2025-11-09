package system;

import app.Main;

public class RestartCommand {

    public void execute(Main ui) {
        if (ui != null) ui.appendOutput("Restarting CLI...");
        // In a GUI app, a full restart would require relaunching the JVM or reinitializing state.
        // For now, we just notify the user.
    }
}
