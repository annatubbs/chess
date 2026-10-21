package dataaccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameMemDataAccess implements GameDataAccess {

    private HashMap<Integer, HashMap<String, String>> gameDataMap;
    private DataAccessException.GameIDGenerator gameIDGenerator;

    public GameMemDataAccess() {
        gameDataMap = new HashMap<>();
        gameIDGenerator = new DataAccessException.GameIDGenerator();
    }

    public GameData getGame(int gameID) throws DataAccessException {
        try {
            if (gameDataMap.containsKey(gameID)) {
                HashMap<String, String> nameMap = gameDataMap.get(gameID);
                return new GameData(gameID, nameMap.get("blackUserName"),
                        nameMap.get("whiteUserName"), nameMap.get("gameName"));
            }
            return null;
        } catch (Exception e) {
            throw new DataAccessException("Error in game data: " + e.getMessage());
        }
    }

    public ArrayList<GameData> listGames() throws DataAccessException {
        try {
            ArrayList<GameData> gameList = new ArrayList<>();
            for (Integer gameID : gameDataMap.keySet()) {
                HashMap<String, String> nameMap = gameDataMap.get(gameID);
                GameData data = new GameData(gameID, nameMap.get("blackUserName"),
                        nameMap.get("whiteUserName"), nameMap.get("gameName"));

                gameList.add(data);
            }
            return gameList;
        } catch (Exception e) {
            throw new DataAccessException("Error in game data: " + e.getMessage());
        }
    }

    public GameData createGame(String gameName) throws DataAccessException {
        try {
            int gameID = gameIDGenerator.generateID();

            gameDataMap.put(gameID, new HashMap<>(Map.of("blackUserName", "",
                    "whiteUserName", "",
                    "gameName", gameName)));

            return new GameData(gameID, "", "", gameName);
        } catch (Exception e) {
            throw new DataAccessException("Error in game data: " + e.getMessage());
        }
    }

    public void updateGame(GameData gameData) throws DataAccessException {
        try {
            int gameID = gameData.getGameID();

            if (!gameDataMap.containsKey(gameID)) {
                throw new DataAccessException("Error updating game. Game ID " + String.valueOf(gameID) + " doesn't exist.");
            }

            HashMap<String, String> updatedNameMap = new HashMap<>(Map.of("blackUserName", gameData.getBlackUsername(),
                    "whiteUserName", gameData.getWhiteUsername(),
                    "gameName", gameData.getGameName()));

            gameDataMap.put(gameID, updatedNameMap);
        } catch (Exception e) {
            throw new DataAccessException("Error in game data: " + e.getMessage());
        }
    }

    public void clearData() throws DataAccessException {
        try {
            gameDataMap = new HashMap<>();
            gameIDGenerator = new DataAccessException.GameIDGenerator();
        } catch (Exception e) {
            throw new DataAccessException("Error in game data: " + e.getMessage());
        }
    }

}
