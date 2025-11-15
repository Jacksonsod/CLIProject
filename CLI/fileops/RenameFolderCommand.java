package fileops;
import app.Main;
import java.io.File;
public class RenameFolderCommand {
    public static void execute(String oldName, String newName, String activeUser, Main ui) {
        if (oldName == null || oldName.trim().isEmpty() || newName == null || newName.trim().isEmpty()) {
            ui.appendOutput("Please specify both the current and new folder names.");
            return;
        }
        File oldFolder = new File("data/" + oldName.trim());
        File newFolder = new File("data/" + newName.trim());

        if (!oldFolder.exists()) {
            ui.appendOutput("Folder does not exist: " + oldFolder.getPath());
        }
        else if (newFolder.exists()) {
            ui.appendOutput("A folder with the new name already exists: " + newFolder.getPath());
        }
        else if (oldFolder.renameTo(newFolder)) {
            ui.appendOutput("Folder renamed from '" + oldName + "' to '" + newName + "'.");
        }
        else {
            ui.appendOutput("Failed to rename folder.");
        }
    }
}
