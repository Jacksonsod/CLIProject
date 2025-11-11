package fileops;
import app.Main;
import java.io.File;
import java.io.IOException;
public class CreateFileCommand {
    public static void execute(String fileName, String activeUser, Main ui) {
        if (fileName == null || fileName.trim().isEmpty()) {
            ui.appendOutput("Please specify a file name.");
            return;
        }
        File file = new File("data/" + fileName.trim());
        try {
            if (file.exists()) {
                ui.appendOutput("File already exists: " + file.getPath());
            } else if (file.createNewFile()) {
                ui.appendOutput("File created: " + file.getPath());
                // Optionally log to DB here
            } else {
                ui.appendOutput("Failed to create file.");
            }
        } catch (IOException e) {
            ui.appendOutput("Error creating file: " + e.getMessage());
        }
    }
}
