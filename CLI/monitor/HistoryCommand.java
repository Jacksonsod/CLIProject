package monitor;

import app.Main;
import java.util.List;

public class HistoryCommand {
    public static void execute(List<String> history, Main ui) {
        if (history == null || history.isEmpty()) {
            ui.appendOutput("History: no commands yet.");
            return;
        }
        ui.appendOutput("History (most recent last):");
        int i = 1;
        for (String cmd : history) {
            ui.appendOutput(i + ". " + cmd);
            i++;
        }
    }
}
