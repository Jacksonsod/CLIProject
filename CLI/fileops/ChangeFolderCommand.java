package fileops;
import app.Main;
import java.io.File;
public class ChangeFolderCommand {
    public static void execute(String folderName, String activeUser, Main ui) {
        if (folderName == null || folderName.trim().isEmpty()) {
            ui.appendOutput("Please specify a folder name.");
            return;
        }
        File folder = new File("data/" + folderName.trim());
        if (!folder.exists()) {
            ui.appendOutput("Folder not found: " + folder.getPath());
        } else if (folder.isDirectory()) {
            ui.appendOutput("Folder changed to: " + folder.getPath());
            // Optionally log to DB here
        } else {
            ui.appendOutput("The specified path is not a folder.");
        }
    }
}
