package fileops;

import app.Main;
import java.io.File;

public class CreateFolderCommand {

    public static void execute(String folderName, String activeUser, Main ui) {
        if (folderName == null || folderName.trim().isEmpty()) {
            ui.appendOutput("Please specify a folder name.");
            return;
        }

        File folder = new File("data/" + folderName.trim());
        if (folder.exists()) {
            ui.appendOutput("Folder already exists: " + folder.getPath());
        } else if (folder.mkdirs()) {
            ui.appendOutput("Folder created: " + folder.getPath());
            // Optionally log to DB here
        } else {
            ui.appendOutput("Failed to create folder.");
        }
    }
}