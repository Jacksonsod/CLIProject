import java.util.HashMap;
import java.util.Map;

public class ManualCommand {

    private static Map<String, String> manuals = new HashMap<>();

    // Load commands and descriptions
    static {
        manuals.put("help", "Displays all available commands and their descriptions.");
        manuals.put("manual", "Shows detailed info about a specific command. Usage: manual [command]");
        manuals.put("exit", "Closes the Help & Learning system.");
        manuals.put("login", "Authenticate and start a session.");
        manuals.put("logout", "End the current session.");
        manuals.put("create user", "Add a new user to your CLI system.");
        manuals.put("delete user", "Remove an existing user.");
        manuals.put("list users", "Display all registered users.");
        manuals.put("switch user", "Change the active user.");
    }

    // Show manual for one command
    public static void showManual(String command) {
        if (manuals.containsKey(command)) {
            System.out.println("\nManual for '" + command + "':");
            System.out.println(manuals.get(command));
        } else {
            System.out.println("\nNo manual found for '" + command + "'. Try 'help' to see available commands.\n");
        }
    }

    // Show all commands (help command)
    public static void showHelp() {
        System.out.println("\nAvailable Commands:");
        for (Map.Entry<String, String> entry : manuals.entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println();
    }
}
