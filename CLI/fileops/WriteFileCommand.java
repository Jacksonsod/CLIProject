package fileops;
import app.Main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class WriteFileCommand {
    public static void execute(String fileName, String content, String activeUser, Main ui) {
        if (fileName == null || fileName.trim().isEmpty()) {
            ui.appendOutput("Please specify a file name.");
            return;
        }
        if (content == null || content.trim().isEmpty()) {
            ui.appendOutput("Please specify the text to write.");
            return;
        }
        File file = new File("data/" + fileName.trim());
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(content + System.lineSeparator());
            ui.appendOutput("Written to file: " + file.getPath());
        } catch (IOException e) {
            ui.appendOutput("Error writing to file: " + e.getMessage());
        }
    }
}
