package com.mycli.app;

import com.mycli.auth.Authenticator;
import com.mycli.core.CLIInterface;
import com.mycli.core.CommandProcessor;
import com.mycli.validation.CommandValidator;

public class Main { // File MUST be MainClass.java

    public static void main(String[] args) {

        // 1. Instantiate all core components
        Authenticator authenticator = new Authenticator();
        CommandValidator validator = new CommandValidator();
        CommandProcessor processor = new CommandProcessor(authenticator, validator);
        CLIInterface cli = new CLIInterface();

        cli.displayOutput("\n=============================================");
        cli.displayOutput("🚀 Initializing Secure CLI Application (v1.0)");
        cli.displayOutput("=============================================");
        cli.displayOutput("Type 'login kenny tuyisenge' to begin or 'help' for commands.");

        boolean running = true;
        try {
            while (running) {
                String rawCommand = cli.readCommand();

                // Process the command
                String result = processor.processCommand(rawCommand);

                // Check for the special signal from the processor
                if (result.equals("EXIT_COMMAND")) {
                    running = false;
                } else if (!result.isEmpty()) {
                    cli.displayOutput(result);
                }
            }
        } finally {
            cli.displayOutput("\n👋 Application Shutting Down. Goodbye!");
            cli.close(); // Clean up resources
        }
    }
}