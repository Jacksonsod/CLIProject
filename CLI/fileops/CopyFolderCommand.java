package fileops;
import app.Main;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
public class CopyFolderCommand {
    public static void execute(String sourceName, String destName, String activeUser, Main ui) {
        if (sourceName == null || sourceName.trim().isEmpty() ||
                destName == null || destName.trim().isEmpty()) {
            ui.appendOutput("Please specify both source and destination folder names.");
            return;
        }
        File sourceFolder = new File("data/" + sourceName.trim());
        File destFolder = new File("data/" + destName.trim());
        if (!sourceFolder.exists()) {
            ui.appendOutput("Source folder does not exist: " + sourceFolder.getPath());
        }
        else if (!sourceFolder.isDirectory()) {
            ui.appendOutput("Source path is not a folder.");
        }
        else if (destFolder.exists()) {
            ui.appendOutput("Destination folder already exists: " + destFolder.getPath());
        }
        else {
            try {
                copyFolder(sourceFolder, destFolder);
                ui.appendOutput("Folder copied from '" + sourceName + "' to '" + destName + "'.");
                // Optionally log to DB here
            } catch (IOException e) {
                ui.appendOutput("Error copying folder: " + e.getMessage());
            }
        }
    }

    private static void copyFolder(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdirs();
            }

            String[] files = source.list();
            if (files != null) {
                for (String file : files) {
                    File srcFile = new File(source, file);
                    File destFile = new File(destination, file);
                    copyFolder(srcFile, destFile);
                }
            }
        } else {
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
