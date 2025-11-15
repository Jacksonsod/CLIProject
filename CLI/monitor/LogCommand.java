package monitor;

import app.Main;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class LogCommand {
    public static void execute(Main ui, Integer lastN) {
        Path log = Paths.get("activity.log");
        if (!Files.exists(log)) {
            ui.appendOutput("Log: no log file found.");
            return;
        }
        int n = (lastN == null || lastN <= 0) ? 50 : lastN;
        try {
            Deque<String> dq = new ArrayDeque<>(n);
            try (var lines = Files.lines(log, StandardCharsets.UTF_8)) {
                lines.forEach(line -> {
                    if (dq.size() == n) dq.removeFirst();
                    dq.addLast(line);
                });
            }
            ui.appendOutput("Log: showing last " + dq.size() + " lines");
            for (String line : dq) ui.appendOutput(line);
        } catch (IOException e) {
            ui.appendOutput("Error reading log: " + e.getMessage());
        }
    }
}
