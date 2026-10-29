package dataaccess;

import java.util.ArrayList;
import java.util.HashMap;

public class GameDataAccess {

    private DataTransfer dataTransfer;
    private DataAccessException.GameIDGenerator gameIDGenerator;

    public GameDataAccess(DataTransfer dataTransfer) {
        this.dataTransfer = dataTransfer;
        this.gameIDGenerator = new DataAccessException.GameIDGenerator();
    }

    public GameData getGame(int gameID) throws DataAccessException {
        try {
            if (dataTransfer.containsGameID(gameID)) {
                return dataTransfer.getGameData(gameID);
            }
            return null; // null if game id doesn't exist, bad request
        } catch (Exception e) {
            throw new DataAccessException("Error in game data: " + e.getMessage());
        }
    }

    public ArrayList<GameData> listGames() throws DataAccessException {
        try {
            ArrayList<GameData> gameList = new ArrayList<>();
            for (Integer gameID : dataTransfer.getGameIDKeySet()) {
                gameList.add(dataTransfer.getGameData(gameID)); // add GameData objects to list
            }
            return gameList;
        } catch (Exception e) {
            throw new DataAccessException("Error in game data: " + e.getMessage());
        }
    }

    public GameData createGame(String gameName) throws DataAccessException {
        try {
            int gameID = gameIDGenerator.generateID();
            if (dataTransfer.containsGameID(gameID)) {
                throw new DataAccessException("Error: gameID already in database.");
            }

            GameData gameData = new GameData(gameID, null, null, gameName);
            dataTransfer.addGameData(gameData);

            return gameData;
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }

    public void updateGame(GameData gameData) throws DataAccessException {
        try {
            int gameID = gameData.getGameID();
            if (!dataTransfer.containsGameID(gameID)) {
                throw new DataAccessException("Error: Failed to update game. Game ID " + gameID +
                        " doesn't exist.");
            }
            dataTransfer.addGameData(gameData);
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }

    public void clearData() throws DataAccessException {
        try {
            dataTransfer.clearData();
            gameIDGenerator = new DataAccessException.GameIDGenerator();
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }

}
