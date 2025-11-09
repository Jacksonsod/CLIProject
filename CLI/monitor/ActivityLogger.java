package monitor;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ActivityLogger {

    private static final String LOG_FILE = "activity.log";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 'synchronized' to make it thread-safe for multiple threads logging at once
    public static synchronized void log(String message) {
        try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = dtf.format(LocalDateTime.now());
            out.println(timestamp + " | " + message);
        } catch (IOException e) {
            System.err.println("CRITICAL: Could not write to activity log: " + e.getMessage());
        }
    }
}