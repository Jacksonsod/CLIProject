import java.util.Scanner;
import db.UserRepository;
import user.*;
import monitor.*;


public class Main {

    // User management by jackson
    private static final String[] activeUser = { null };
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserRepository repo = new UserRepository();


    public static void main(String[] args) 
    {
         System.out.println("Booting CLI User Manager");
        System.out.println("Please create a user and log in.");

        while (activeUser[0] == null) {
            System.out.print("setup> ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.startsWith("create user ")) {
                CreateUserCommand.execute(input.substring(12), repo);
            } else if (input.startsWith("login ")) {
                LoginCommand.execute(input.substring(6), repo, activeUser);
            } else {
                System.out.println("Available commands: create user username:password, login username:password");
            }
        }

        System.out.println("Welcome, " + activeUser[0]);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("logout")) {
                LogoutCommand.execute(activeUser);
                System.out.println("Session ended. Please log in again.");
                while (activeUser[0] == null) {
                    System.out.print("login> ");
                    input = scanner.nextLine().trim().toLowerCase();
                    if (input.startsWith("login ")) {
                        LoginCommand.execute(input.substring(6), repo, activeUser);
                    } else {
                        System.out.println("You must log in first using: login username:password");
                    }
                }
                System.out.println("Welcome back, " + activeUser[0]);
                continue;
                }

            if (input.startsWith("create user ")) {
                CreateUserCommand.execute(input.substring(12), repo);
            } else if (input.startsWith("delete user ")) {
                DeleteUserCommand.execute(input.substring(12), repo, activeUser);
            } else if (input.equals("list users")) {
                ListUsersCommand.execute(repo);
            } else if (input.startsWith("switch user ")) {
                SwitchUserCommand.execute(input.substring(12), repo, activeUser);
            }else if (input.startsWith("change password ")) {
            User current = repo.findUser(activeUser[0]);
            if (current != null) {
                ChangePasswordCommand.execute(input.substring(16), current, repo);
            } else {
                 System.out.println("No active user found.");
            }
            }else if (input.startsWith("reset password ")) {
            User current = repo.findUser(activeUser[0]);
            if (current != null) {
            ResetPasswordCommand.execute(input.substring(15), current, repo);
            }
            }

             else if (input.equals("exit")) {
                System.out.println("Goodbye.");
                return;
            } else {
                System.out.println("Unknown command.\nTry: create user, delete user, list users, switch user, logout, exit");
            }
        }


  
    }
    


        // End by here user management












        // Directory & File Operations by Laetitia














        // End Directory & File Operations By here


        // System Control & Utilities by Imena kenny













        // End System Control & Utilities by here


        // Monitoring & Diagnostics by Patrick
        MonitoringManager manager = new MonitoringManager();
        //manager.startMonitoring();















        // End Monitoring & Diagnostics by here


        // Help & Learning by Angella











        // End Help & Learning by here
    }
