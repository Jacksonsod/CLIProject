package monitor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MonitoringManager {

    private static final int PORT = 12345; // Example port
    private ClientTracker clientTracker;
    private SystemPerformanceMonitor performanceMonitor;
    private ReportGenerator reportGenerator;
    private volatile boolean isRunning = true;

    public MonitoringManager() {
        this.clientTracker = new ClientTracker();
        this.performanceMonitor = new SystemPerformanceMonitor();
        this.reportGenerator = new ReportGenerator();
    }

    public void startMonitoring() {
        // Start system performance monitoring in a separate thread
        new Thread(performanceMonitor).start();

        // Start the server socket to accept client connections
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Monitoring server listening on port " + PORT);
            ActivityLogger.log("Server started on port " + PORT);

            while (isRunning) {
                // Wait for a new client to connect
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Create a new handler for the client
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientTracker);

                // Add client to the tracker
                clientTracker.addClient(clientHandler);

                // Start the client handler thread
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            if (isRunning) {
                System.err.println("Server error: " + e.getMessage());
                ActivityLogger.log("Server error: " + e.getMessage());
            }
        }
    }

    public void stopMonitoring() {
        this.isRunning = false;
        performanceMonitor.stop();
        clientTracker.notifyAllClients("Server is shutting down.");
        ActivityLogger.log("Server shutting down.");
        // TODO: Add logic to gracefully close ServerSocket and all client threads
    }

    // Example method to trigger a report
    public void generateReport() {
        // TODO: Gather data from trackers and monitors
        String reportData = "Client count: " + clientTracker.getConnectedClientCount();
        reportGenerator.generateReport(reportData);
    }
}