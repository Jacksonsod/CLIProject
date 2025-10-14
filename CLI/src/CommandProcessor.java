package com.mycli.core;

import com.mycli.auth.Authenticator;
import com.mycli.model.CliCommand;
import com.mycli.validation.CommandValidator;
import java.util.List;

public class CommandProcessor {
    private final Authenticator authenticator;
    private final CommandValidator validator;

    public CommandProcessor(Authenticator authenticator, CommandValidator validator) {
        this.authenticator = authenticator;
        this.validator = validator;
    }

    /**
     * Processes a raw command string through parsing, validation, and execution.
     * @param fullCommand The raw user input.
     * @return The result message to be displayed.
     */
    public String processCommand(String fullCommand) {
        // 1. Parse the raw input into a structured object
        CliCommand command = CliCommand.parse(fullCommand);
        String name = command.getName();
        List<String> args = command.getArguments();

        if (name.isEmpty()) {
            return ""; // Ignore empty line
        }

        // 2. Handle immediate (non-validated) commands like 'exit'
        if (name.equalsIgnoreCase("exit")) {
            return "EXIT_COMMAND"; // Special signal to the MainClass
        }

        // 3. Validation
        if (!validator.validate(command)) {
            return String.format("❌ ERROR: Unknown command '%s' or incorrect argument count. Type 'help' for usage.", name);
        }

        // 4. Execution (with integrated Authentication/Authorization)
        switch (name) {
            case "login":
                boolean success = authenticator.login(args.get(0), args.get(1));
                return success ?
                        String.format("✅ SUCCESS: Welcome, %s.", authenticator.getCurrentUser()) :
                        "❌ FAILURE: Invalid username or password.";

            case "logout":
                authenticator.logout();
                return "👋 SUCCESS: You have been logged out.";

            case "help":
                return getHelpMessage();

            case "getinfo":
                // 5. Authorization Check
                if (!authenticator.isLoggedIn()) {
                    return "🚫 ACCESS DENIED: You must be logged in to view resource information.";
                }
                String resourceId = args.get(0);
                return String.format("📈 RESULT: Showing information for resource ID: %s. Access granted to user %s.",
                        resourceId, authenticator.getCurrentUser());

            default:
                return "🛑 CRITICAL ERROR: Unhandled command in processor logic.";
        }
    }

    private String getHelpMessage() {
        return "--- CLI COMMANDS ---\n" +
                "| Command | Arguments | Description |\n" +
                "|---------|-----------|-----------------------------|\n" +
                "| login   | <user> <pass> | Authenticate a user. |\n" +
                "| logout  | -         | End the current session. |\n" +
                "| getinfo | <id>      | View info for a resource (requires login). |\n" +
                "| help    | -         | Display this help message. |\n" +
                "| exit    | -         | Close the CLI application. |\n" +
                "----------------------";
    }
}