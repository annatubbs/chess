package dataaccess;

import java.util.HashMap;
import java.util.Map;

public class GameData {
    private final HashMap<String, Object> gameDataMap;

    public GameData(int gameID, String blackUsername, String whiteUsername, String gameName) {
        this.gameDataMap = new HashMap<>(Map.of("gameID", gameID,
                                                "blackUsername", blackUsername,
                                                "whiteUsername", whiteUsername,
                                                "gameName", gameName));
    }

    public GameData(HashMap<String, Object> gameDataMap) {
        this.gameDataMap = new HashMap<>(gameDataMap);
    }

    public GameData deepCopy() {
        return new GameData(gameDataMap);
    }

}
