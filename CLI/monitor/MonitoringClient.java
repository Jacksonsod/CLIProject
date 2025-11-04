package monitor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
public class MonitoringClient {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (
                // Connect to the server
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

                // Set up streams to send/receive data
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Set up a scanner to read user input from the console
                Scanner consoleScanner = new Scanner(System.in)
        ) {

            System.out.println("Connected to Monitoring Server.");

            // Read the server's welcome message
            String serverResponse = in.readLine();
            System.out.println("SERVER: " + serverResponse);

            // Loop to send commands
            String userInput;
            while (true) {
                System.out.print("Enter command (e.g., 'ping', 'status', or 'exit'): ");
                userInput = consoleScanner.nextLine();

                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }

                // Send the command to the server
                out.println(userInput);

                // Read the response from the server
                serverResponse = in.readLine();
                System.out.println("SERVER: " + serverResponse);
            }

        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
        } finally {
            System.out.println("Disconnecting from server.");
        }
    }
}