import java.util.*;
import user.*;

public class Main {
    private static final String DEFAULT_USER = "admin";
    private static final String DEFAULT_PASS = "123";

    private static Map<String, String> users = new HashMap<>();
    private static String[] activeUser = { null };
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        users.put(DEFAULT_USER, DEFAULT_PASS);
        System.out.println("Welcome to CLI User Manager");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.startsWith("login ")) {
                LoginCommand.execute(input.substring(6), users, activeUser);
            } else if (input.equals("logout")) {
                LogoutCommand.execute(activeUser);
            } else if (input.startsWith("create user ")) {
                CreateUserCommand.execute(input.substring(12), users);
            } else if (input.startsWith("delete user ")) {
                DeleteUserCommand.execute(input.substring(12), users, activeUser);
            } else if (input.equals("list users")) {
                ListUsersCommand.execute(users);
            } else if (input.startsWith("switch user ")) {
                SwitchUserCommand.execute(input.substring(12), users, activeUser);
            } else if (input.equals("exit")) {
                System.out.println("Goodbye! " + DEFAULT_USER);
                return;
            } else {
                System.out.println("Unknown command. Try: login, logout, create user, delete user, list users, switch user, exit");
            }
        }
    }
}