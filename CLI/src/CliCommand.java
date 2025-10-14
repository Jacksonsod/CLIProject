package com.mycli.model;

import java.util.Arrays;
import java.util.List;

public class CliCommand {
    private final String name;
    private final List<String> arguments;

    public CliCommand(String name, List<String> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public static CliCommand parse(String fullCommand) {
        String[] parts = fullCommand.trim().split("\\s+");
        if (parts.length == 0 || fullCommand.trim().isEmpty()) {
            return new CliCommand("", List.of());
        }
        String name = parts[0].toLowerCase();
        List<String> args = Arrays.asList(parts).subList(1, parts.length);
        return new CliCommand(name, args);
    }

    public String getName() {
        return name;
    }

    public List<String> getArguments() {
        return arguments;
    }
}