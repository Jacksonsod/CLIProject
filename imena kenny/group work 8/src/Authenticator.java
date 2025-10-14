import java.util.HashMap;
import java.util.Map;

public class Authenticator {
    private Map<String, String> users;

    public Authenticator() {
        users = new HashMap<>();
        users.put("Imenakenny", "1234");
        users.put("tuyisenge", "abcd");
    }

    public boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}
