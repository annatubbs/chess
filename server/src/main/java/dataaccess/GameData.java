package dataaccess;

import chess.ChessGame;

import java.util.HashMap;
import java.util.Map;


// **** usernames must be "" when empty! not null *****

public class GameData {
    private HashMap<String, Object> gameDataMap;

    public GameData(int gameID, String blackUsername, String whiteUsername, String gameName) {
        this.gameDataMap = new HashMap<>(Map.of("gameID", gameID,
                "blackUsername", blackUsername,
                "whiteUsername", whiteUsername,
                "gameName", gameName));
    }

    public GameData(HashMap<String, Object> gameDataMap) {
        this.gameDataMap = new HashMap<>(gameDataMap);
    }

    public int getGameID() {
        return (Integer) gameDataMap.get("gameID");
    }

    public String getBlackUsername() {
        return (String) gameDataMap.get("blackUsername");
    }

    public String getWhiteUsername() {
        return (String) gameDataMap.get("whiteUsername");
    }

    public String getGameName() {
        return (String) gameDataMap.get("gameName");
    }

    public boolean isTaken(ChessGame.TeamColor color) {
        if (color == ChessGame.TeamColor.BLACK) {
            return gameDataMap.get("blackUsername") != null;
        } else { // wants white team
            return gameDataMap.get("whiteUsername") != null;
        }
    }

    public void fillTeamColor(ChessGame.TeamColor color, String username) {
        if (color == ChessGame.TeamColor.BLACK) {
            gameDataMap.put("blackUsername", username);
        } else { // wants white team
            gameDataMap.put("whiteUsername", username);
        }
    }

    public GameData deepCopy() {
        return new GameData(gameDataMap);
    }

    public String toString() {
        return gameDataMap.toString();
    }

}
