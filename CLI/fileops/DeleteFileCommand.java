package fileops;
import app.Main;
import java.io.File;
public class DeleteFileCommand {
    public static void execute(String fileName, String activeUser, Main ui) {
        if (fileName == null || fileName.trim().isEmpty()) {
            ui.appendOutput("Please specify a file name.");
            return;
        }
        File file = new File("data/" + fileName.trim());
        if (!file.exists()) {
            ui.appendOutput("File does not exist: " + file.getPath());
        }
        else if (file.isDirectory()) {
            ui.appendOutput("Cannot delete folder using delete file command. Use delete folder instead.");
        }
        else if (file.delete()) {
            ui.appendOutput("File deleted: " + file.getPath());
        }
        else {
            ui.appendOutput("Failed to delete file.");
        }
    }
}
