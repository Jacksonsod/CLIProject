package fileops;
import app.Main;
import java.io.File;
public class ListContentsCommand {
    public static void execute(String folderName, String activeUser, Main ui) {
        if (folderName == null || folderName.trim().isEmpty()) {
            ui.appendOutput("Please specify a folder name.");
            return;
        }
        File folder = new File("data/" + folderName.trim());
        if (!folder.exists()) {
            ui.appendOutput("Folder not found: " + folder.getPath());
        } else if (!folder.isDirectory()) {
            ui.appendOutput("The specified path is not a folder: " + folder.getPath());
        } else {
            File[] contents = folder.listFiles();
            if (contents == null || contents.length == 0) {
                ui.appendOutput("The folder is empty: " + folder.getPath());
            } else {
                ui.appendOutput("Contents of folder: " + folder.getPath());
                for (File f : contents) {
                    ui.appendOutput("- " + f.getName());
                }
            }
        }
    }
}
