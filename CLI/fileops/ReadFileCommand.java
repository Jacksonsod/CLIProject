package fileops;
import app.Main;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
public class ReadFileCommand {
    public static void execute(String fileName, String activeUser, Main ui) {
        if (fileName == null || fileName.trim().isEmpty()) {
            ui.appendOutput("Please specify a file name to read.");
            return;
        }
        File file = new File("data/" + fileName.trim());

        if (!file.exists()) {
            ui.appendOutput("File does not exist: " + file.getPath());
            return;
        }
        if (file.isDirectory()) {
            ui.appendOutput("Cannot read a folder. Please specify a valid file.");
            return;
        }
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            ui.appendOutput("=== Contents of " + file.getName() + " ===");
            for (String line : lines) {
                ui.appendOutput(line);
            }
            ui.appendOutput("=== End of file ===");
        } catch (IOException e) {
            ui.appendOutput("Error reading file: " + e.getMessage());
        }
    }
}
