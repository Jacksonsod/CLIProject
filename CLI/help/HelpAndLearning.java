import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class HelpAndLearning {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, String> commands = new LinkedHashMap<>();
    private static final List<String> history = new ArrayList<>();
    private static long startTime = System.currentTimeMillis();
    private static File currentDirectory = new File(System.getProperty("user.dir"));

    static {
        // User & Session Management
        commands.put("login", "Authenticate and start a session.");
        commands.put("logout", "End current session.");
        commands.put("create user", "Add a new user to your CLI system.");
        commands.put("delete user", "Remove a user.");
        commands.put("list users", "Display all registered users.");
        commands.put("switch user", "Change active user.");
        commands.put("change password", "User can change their own password.");
        commands.put("reset password", "Admin resets a forgotten password.");

        // Directory & File Operations
        commands.put("create folder", "Create a new directory.");
        commands.put("delete folder", "Delete a directory.");
        commands.put("change folder", "Change working directory.");
        commands.put("list contents", "List directory contents.");
        commands.put("create file", "Create an empty file.");
        commands.put("delete file", "Delete a file.");
        commands.put("rename folder", "Rename a file or folder.");
        commands.put("copy folder", "Copy file or folder.");
        commands.put("read", "Read file contents.");
        commands.put("write file", "Write text to a file.");

        // System Control
        commands.put("clear", "Clear the CLI screen.");
        commands.put("time", "Show current system time.");
        commands.put("date", "Show current system date.");
        commands.put("uptime", "Show how long the CLI has been running.");
        commands.put("shutdown", "Exit the CLI.");
        commands.put("restart", "Restart CLI session.");

        // Monitoring
        commands.put("history", "Show command history.");
        commands.put("log", "View CLI logs (simulated).");
        commands.put("status", "Show current session status.");
        commands.put("memory", "Show JVM memory usage.");

        // Help
        commands.put("help", "List all commands.");
        commands.put("manual", "Show manual for a specific command.");
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Help & Learning CLI 🧠");
        System.out.println("Type 'help' to see all commands.\n");

        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine().trim().toLowerCase();
            history.add(input);

            if (input.equals("help")) showHelp();
            else if (input.startsWith("manual")) handleManual(input);
            else if (commands.containsKey(input)) executeCommand(input);
            else if (input.equals("exit") || input.equals("shutdown")) {
                System.out.println("Goodbye 👋");
                break;
            } else {
                System.out.println("❓ Unknown command. Type 'help' to see commands.\n");
            }
        }
    }

    // Execute command functionality
    private static void executeCommand(String cmd) {
        switch (cmd) {
            case "login" -> System.out.println("✔ Login successful (simulated).\n");
            case "logout" -> System.out.println("✔ Logged out.\n");
            case "create user" -> System.out.println("✔ User created (simulated).\n");
            case "delete user" -> System.out.println("✔ User deleted (simulated).\n");
            case "list users" -> System.out.println("[admin, guest, user1] (simulated)\n");
            case "switch user" -> System.out.println("✔ Switched user.\n");
            case "change password" -> System.out.println("✔ Password changed.\n");
            case "reset password" -> System.out.println("✔ Password reset.\n");

            case "create folder" -> {
                System.out.print("Folder name: ");
                String name = scanner.nextLine();
                new File(currentDirectory, name).mkdir();
                System.out.println("✔ Folder created.\n");
            }

            case "delete folder" -> {
                System.out.print("Folder name: ");
                String name = scanner.nextLine();
                new File(currentDirectory, name).delete();
                System.out.println("✔ Folder deleted.\n");
            }

            case "change folder" -> {
                System.out.print("Enter folder name: ");
                String name = scanner.nextLine();
                File newDir = new File(currentDirectory, name);
                if (newDir.exists() && newDir.isDirectory()) {
                    currentDirectory = newDir;
                    System.out.println("✔ Directory changed.\n");
                } else System.out.println("❌ Folder not found.\n");
            }

            case "list contents" -> {
                System.out.println("\n📂 Contents:");
                for (File f : currentDirectory.listFiles())
                    System.out.println("- " + f.getName());
                System.out.println();
            }

            case "create file" -> {
                System.out.print("Filename: ");
                try { new File(currentDirectory, scanner.nextLine()).createNewFile(); }
                catch (Exception ignored) {}
                System.out.println("✔ File created.\n");
            }

            case "delete file" -> {
                System.out.print("Filename: ");
                new File(currentDirectory, scanner.nextLine()).delete();
                System.out.println("✔ File deleted.\n");
            }

            case "read" -> {
                System.out.print("Filename: ");
                File file = new File(currentDirectory, scanner.nextLine());
                try (Scanner reader = new Scanner(file)) {
                    while (reader.hasNextLine()) System.out.println(reader.nextLine());
                } catch (Exception e) { System.out.println("❌ Unable to read file."); }
                System.out.println();
            }

            case "write file" -> {
                System.out.print("Filename: ");
                File file = new File(currentDirectory, scanner.nextLine());
                System.out.print("Enter text: ");
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(scanner.nextLine());
                } catch (Exception ignored) {}
                System.out.println("✔ Written.\n");
            }

            case "clear" -> System.out.println("\n\n\n\n================ SCREEN CLEARED ================\n");
            case "time" -> System.out.println("⏰ Time: " + new Date() + "\n");
            case "date" -> System.out.println("📅 Today: " + new Date() + "\n");
            case "uptime" -> {
                long uptime = (System.currentTimeMillis() - startTime) / 1000;
                System.out.println("⏳ Uptime: " + uptime + " seconds\n");
            }
            case "restart" -> {
                System.out.println("🔄 Restarting CLI...\n");
                history.clear();
                startTime = System.currentTimeMillis();
            }
            case "history" -> {
                System.out.println("\n📜 Command History:");
                history.forEach(h -> System.out.println("- " + h));
                System.out.println();
            }
            case "log" -> System.out.println("📄 Logs: [System OK, No errors found] (simulated)\n");
            case "status" -> System.out.println("🟢 System running normally.\n");
            case "memory" -> {
                long mem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                System.out.println("💾 JVM Memory Usage: " + mem + " bytes\n");
            }

            default -> System.out.println("⚠ Command exists but no function implemented.\n");
        }
    }

    // Manual handler
    private static void handleManual(String input) {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            System.out.println("Usage: manual [command]\n");
            return;
        }

        showManual(parts[1]);
    }

    private static void showHelp() {
        System.out.println("\n📘 ALL COMMANDS");
        System.out.println("--------------------------------------------------");
        commands.forEach((cmd, desc) ->
                System.out.printf("%-18s - %s%n", cmd, desc));
        System.out.println();
    }

    private static void showManual(String cmd) {
        if (!commands.containsKey(cmd)) {
            System.out.println("\n❌ No manual for '" + cmd + "'\n");
            return;
        }

        System.out.println("\n📖 MANUAL: " + cmd);
        System.out.println("Description:");
        System.out.println("  " + commands.get(cmd));
        System.out.println("Usage:");
        System.out.println("  " + cmd + "\n");
    }
}
