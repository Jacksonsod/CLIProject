public class CommandProcessor {
    private Authenticator authenticator;
    private CommandValidator validator;

    public CommandProcessor() {
        authenticator = new Authenticator();
        validator = new CommandValidator();
    }

    public void processCommand(String command) {
        if (!validator.isValid(command)) {
            System.out.println("Invalid command!");
            return;
        }

        if (command.startsWith("login")) {
            String[] parts = command.split(" ");
            if (parts.length == 3) {
                String user = parts[1];
                String pass = parts[2];
                if (authenticator.authenticate(user, pass)) {
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Login failed!");
                }
            } else {
                System.out.println("Usage: login <username> <password>");
            }
        } else {
            System.out.println("Executing command: " + command);
        }
    }
}

