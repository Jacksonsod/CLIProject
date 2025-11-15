package fileops;

import app.Main;
import java.io.File;
public class DeleteFolderCommand {
    public static void execute(String folderName, String activeUser, Main ui) {
        if (folderName == null || folderName.trim().isEmpty()) {
            ui.appendOutput("Please specify a folder name.");
            return;
        }
        File folder = new File("data/" + folderName.trim());
        if (!folder.exists()) {
            ui.appendOutput("Folder does not exist: " + folder.getPath());
        } else if (deleteRecursively(folder)) {
            ui.appendOutput("Folder deleted: " + folder.getPath());
        } else {
            ui.appendOutput("Failed to delete folder.");
        }
    }
    private static boolean deleteRecursively(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                if (!deleteRecursively(child)) {
                    return false;
                }
            }
        }
        return file.delete();
    }
}