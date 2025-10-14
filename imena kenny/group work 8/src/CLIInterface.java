import java.util.Scanner;

public class CLIInterface {
    private CommandProcessor processor;

    public CLIInterface() {
        processor = new CommandProcessor();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to CLI App. Type 'help' to see commands or 'exit' to quit.");

        while (true) {
            System.out.print("> ");  // CLI prompt
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            processor.processCommand(input);
        }

        scanner.close();
    }

    public static void main(String[] args) {
        CLIInterface cli = new CLIInterface();
        cli.start();
    }
}
