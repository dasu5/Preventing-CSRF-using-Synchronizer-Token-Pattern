import java.util.HashMap;
import java.util.Map;

public class CookieStorage {

    private static CookieStorage cookieStorage = new CookieStorage();
    private Map<String, String> map = new HashMap<>();

    private CookieStorage() {

    }

    public static CookieStorage getCookieStorage() {
        return cookieStorage;
    }

    public static void setCookieStorag(CookieStorage simpleStorage) {
        CookieStorage.cookieStorage = simpleStorage;
    }

    public void addItem(String sessionID, String token) {
        map.put(sessionID, token);
    }

    public String getItem(String sessionID) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (sessionID.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }


}


