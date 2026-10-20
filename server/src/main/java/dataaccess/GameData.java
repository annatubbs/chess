package dataaccess;

import java.util.HashMap;
import java.util.Map;

public class GameData {
    private final Map<String, Object> gameDataMap;

    public GameData(int gameID, String blackUsername, String whiteUsername, String gameName) {
        this.gameDataMap = Map.of("gameID", gameID, "blackUsername", blackUsername,
                "whiteUsername", whiteUsername, "gameName", gameName);
    }

    public HashMap<String, Object> getGameDataMap() {
        return new HashMap<>(gameDataMap);
    }
}
