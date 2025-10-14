package com.mycli.validation;

import com.mycli.model.CliCommand;

public class CommandValidator {

    /**
     * Checks if the command name is known and has the correct number of arguments.
     * @param command The parsed CliCommand object.
     * @return true if the command is valid, false otherwise.
     */
    public boolean validate(CliCommand command) {
        String commandName = command.getName();
        int argCount = command.getArguments().size();

        switch (commandName) {
            case "login":
                // Expected: login <username> <password>
                return argCount == 2;
            case "logout":
            case "help":
            case "exit":
                // Expected: logout/help/exit (0 arguments)
                return argCount == 0;
            case "getinfo":
                // Expected: getinfo <resource_id> (1 argument)
                return argCount == 1;
            case "":
                // Empty command is also invalid
                return false;
            default:
                // Unknown command
                return false;
        }
    }
}