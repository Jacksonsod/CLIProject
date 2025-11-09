package monitor;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ClientTracker {

    // Use a thread-safe Set to store active client handlers
    private Set<ClientHandler> connectedClients = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public void addClient(ClientHandler clientHandler) {
        connectedClients.add(clientHandler);
        System.out.println("Client added: " + clientHandler.getClientId() + ". Total clients: " + connectedClients.size());
        ActivityLogger.log("Client connected: " + clientHandler.getClientId());
    }

    public void removeClient(ClientHandler clientHandler) {
        connectedClients.remove(clientHandler);
        System.out.println("Client removed: " + clientHandler.getClientId() + ". Total clients: " + connectedClients.size());
        ActivityLogger.log("Client disconnected: " + clientHandler.getClientId());
    }

    public int getConnectedClientCount() {
        return connectedClients.size();
    }

    public void notifyAllClients(String message) {
        // Iterate over a snapshot to avoid ConcurrentModificationException
        for (ClientHandler client : connectedClients) {
            client.sendMessage(message);
        }
    }
}