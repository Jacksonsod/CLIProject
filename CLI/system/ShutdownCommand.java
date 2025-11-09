package system;

import app.Main;
import db.UserRepository;

public class ShutdownCommand {

    public void execute(UserRepository repo, Main ui) {
        if (ui != null) ui.appendOutput("Shutting down CLI...");
        System.exit(0); // terminates the program
    }
}
