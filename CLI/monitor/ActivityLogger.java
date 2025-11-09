package monitor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class ActivityLogger {
    private static final ActivityLogger INSTANCE = new ActivityLogger();
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Path logPath;
    private final Lock lock = new ReentrantLock();

    private ActivityLogger() {
        this("activity.log");
    }

    private ActivityLogger(String fileName) {
        this.logPath = Paths.get(fileName);
        ensureFileReady();
    }

    public static ActivityLogger getInstance() {
        return INSTANCE;
    }

    public void log(String message) {
        if (message == null) return;
        String line = String.format("[%s] %s", LocalDateTime.now().format(TS), message.replaceAll("\r?\n$", ""));
        lock.lock();
        try (BufferedWriter writer = Files.newBufferedWriter(
                logPath,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND
        )) {
            writer.write(line);
            writer.newLine();
        } catch (IOException ignored) {
        } finally {
            lock.unlock();
        }
    }

    private void ensureFileReady() {
        try {
            if (Files.notExists(logPath)) {
                Files.createFile(logPath);
            }
        } catch (IOException ignored) {

        }
    }
}
