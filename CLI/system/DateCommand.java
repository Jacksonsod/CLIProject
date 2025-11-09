package system;

import app.Main;
import java.time.LocalDate;

public class DateCommand {

    public void execute(Main ui) {
        LocalDate date = LocalDate.now();
        if (ui != null) ui.appendOutput("Current Date: " + date);
    }
}
