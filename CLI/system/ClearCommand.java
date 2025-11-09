package system;

import app.Main;

public class ClearCommand {

    // GUI-aware clear
    public void execute(Main ui) {
        if (ui != null) {
            ui.clearScreen();
        }
    }
}
