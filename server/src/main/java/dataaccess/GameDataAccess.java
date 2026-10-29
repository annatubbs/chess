package dataaccess;

import java.util.ArrayList;
import java.util.HashMap;

public class GameDataAccess {

    private DataTransfer dataTransfer;
    private HashMap<Integer, GameData> gameDataMap;
    private DataAccessException.GameIDGenerator gameIDGenerator;

    public GameDataAccess(DataTransfer dataTransfer) {
        this.dataTransfer = dataTransfer;
        this.gameDataMap = new HashMap<>();
        this.gameIDGenerator = new DataAccessException.GameIDGenerator();
    }

    public GameData getGame(int gameID) throws DataAccessException {
        try {
            if (gameDataMap.containsKey(gameID)) {
                return gameDataMap.get(gameID);
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
                gameList.add(gameDataMap.get(gameID)); // add GameData objects to list
            }
            return gameList;
        } catch (Exception e) {
            throw new DataAccessException("Error in game data: " + e.getMessage());
        }
    }

    public GameData createGame(String gameName) throws DataAccessException {
        try {
            int gameID = gameIDGenerator.generateID();
            GameData gameData = new GameData(gameID, null, null, gameName);

            gameDataMap.put(gameID, gameData);
            return gameData;
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }

    public void updateGame(GameData gameData) throws DataAccessException {
        try {
            int gameID = gameData.getGameID();

            if (!gameDataMap.containsKey(gameID)) {
                throw new DataAccessException("Error: Failed to update game. Game ID " + gameID +
                        " doesn't exist.");
            }

            gameDataMap.put(gameID, gameData);
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }

    public void clearData() throws DataAccessException {
        try {
            gameDataMap = new HashMap<>();
            authDataAccess.clearData();
            userDataAccess.clearData();
            gameIDGenerator = new DataAccessException.GameIDGenerator();
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }

}
