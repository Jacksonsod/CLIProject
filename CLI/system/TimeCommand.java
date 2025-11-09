package system;

import app.Main;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeCommand {

    public void execute(Main ui) {
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        if (ui != null) ui.appendOutput("Current Time: " + time.format(formatter));
    }
}
