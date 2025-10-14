import java.util.Arrays;
import java.util.List;

public class CommandValidator {
    private List<String> validCommands = Arrays.asList("login", "help", "exit", "status");

    public boolean isValid(String command) {
        String cmd = command.split(" ")[0]; // take first word
        return validCommands.contains(cmd);
    }
}
