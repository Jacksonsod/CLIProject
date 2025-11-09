package system;

import app.Main;

public class UptimeCommand {

    private long startTime;

    // Constructor receives the CLI start time in milliseconds
    public UptimeCommand(long startTime) {
        this.startTime = startTime;
    }

    public void execute(Main ui) {
        long elapsedMillis = System.currentTimeMillis() - startTime;

        long seconds = (elapsedMillis / 1000) % 60;
        long minutes = (elapsedMillis / (1000 * 60)) % 60;
        long hours = (elapsedMillis / (1000 * 60 * 60));

        if (ui != null) ui.appendOutput(String.format("Uptime: %02d:%02d:%02d", hours, minutes, seconds));
    }
}
