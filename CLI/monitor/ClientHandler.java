package monitor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private ClientTracker clientTracker;
    private PrintWriter out;
    private BufferedReader in;
    private String clientId;

    public ClientHandler(Socket socket, ClientTracker tracker) {
        this.clientSocket = socket;
        this.clientTracker = tracker;
        this.clientId = socket.getInetAddress().toString();
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println("Welcome to the Monitoring Server!");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from [" + clientId + "]: " + inputLine);

                // Log the command
                CommandLogger.log(clientId, inputLine);

                // Process the command
                String response = processCommand(inputLine);
                out.println(response);
            }
        } catch (IOException e) {
            System.out.println("Client [" + clientId + "] disconnected.");
        } finally {
            // Cleanup
            try {
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Remove client from tracker
            clientTracker.removeClient(this);
        }
    }

    private String processCommand(String command) {
        // TODO: Implement actual command processing logic
        if (command.equalsIgnoreCase("status")) {
            return "Server status: OK";
        } else if (command.equalsIgnoreCase("ping")) {
            return "pong";
        }
        return "Unknown command: " + command;
    }

    // Method for the manager to send messages to this client
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    public String getClientId() {
        return clientId;
    }
}