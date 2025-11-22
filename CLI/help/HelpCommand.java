package help;

import app.Main;

public class HelpCommand {
    public void execute(Main ui) {
        if (ui != null) {
            ui.appendOutput("login           - Authenticate and start a session");
            ui.appendOutput("logout          - End current session");
            ui.appendOutput("create user     - Add a new user to your CLI system");
            ui.appendOutput("delete user     - Remove a user");
            ui.appendOutput("list users      - Display all registered users");
            ui.appendOutput("switch user     - Change active user");
            ui.appendOutput("change password - User can change his/her password");
            ui.appendOutput("reset password  - Admin can reset password for users who forgot it\n");

            ui.appendOutput("create folder   - Create a new directory");
            ui.appendOutput("delete folder   - Delete a directory");
            ui.appendOutput("change folder   - Change working directory");
            ui.appendOutput("list contents   - List contents of current directory");
            ui.appendOutput("create file     - Create an empty file");
            ui.appendOutput("delete file     - Delete a file");
            ui.appendOutput("rename folder   - Rename a file or directory");
            ui.appendOutput("copy folder     - Copy file or folder");
            ui.appendOutput("read            - Display contents of a file");
            ui.appendOutput("write file      - Write text to a file\n");

            ui.appendOutput("-----------------------------------------------");
            ui.appendOutput("clear           - Clear the CLI screen");
            ui.appendOutput("time            - Show current system time");
            ui.appendOutput("date            - Show current date");
            ui.appendOutput("uptime          - Show how long CLI has been running");
            ui.appendOutput("shutdown        - Exit the CLI application");
            ui.appendOutput("restart         - Restart the CLI session\n");

            ui.appendOutput("-----------------------------------------------");
            ui.appendOutput("history         - Show command history");
            ui.appendOutput("log             - View CLI logs");
            ui.appendOutput("status          - Show current session status");
            ui.appendOutput("memory          - Show JVM memory usage\n");

            ui.appendOutput("help            - List of all available commands");
            ui.appendOutput("manual          - Show manual for a specific command");
        }
    }
}
