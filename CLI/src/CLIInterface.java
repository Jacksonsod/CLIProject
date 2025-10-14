package com.mycli.core;

import java.util.Scanner;

public class CLIInterface {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Reads a command line input from the user.
     * @return The trimmed command string.
     */
    public String readCommand() {
        System.out.print("cli> ");
        return scanner.hasNextLine() ? scanner.nextLine().trim() : "";
    }

    /**
     * Displays a result or message to the user.
     * @param message The string to display.
     */
    public void displayOutput(String message) {
        System.out.println(message);
    }

    // Optional: for resource management
    public void close() {
        scanner.close();
    }
}