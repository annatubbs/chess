package dataaccess;

import chess.ChessGame;
import dataobject.GameData;
import dataobject.GameIDGenerator;

import java.util.ArrayList;
import java.util.Set;

public class GameDataAccess {

    private DataTransfer dataTransfer;
    private GameIDGenerator gameIDGenerator;

    public GameDataAccess(DataTransfer dataTransfer) throws DataAccessException {
        this.dataTransfer = dataTransfer;
        try {
            this.gameIDGenerator = new GameIDGenerator(dataTransfer.getGameIDKeySet());
        } catch (Exception e) {
            throw new DataAccessException("Error in game data: " + e.getMessage());
        }
    }

    public ChessGame getGame(int gameID) throws DataAccessException {
        try {
            if (dataTransfer.containsGameID(gameID)) {
                return dataTransfer.getGame(gameID);
            } else {
                throw new DataAccessException("Error: couldn't find game");
            }
        } catch (Exception e) {
            throw new DataAccessException("Error in game data: " + e.getMessage());
        }
    }

    public GameData getGameData(int gameID) throws DataAccessException {
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
            dataTransfer.addGameData(gameData, new ChessGame());

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
            dataTransfer.updateGameData(gameData);
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }

    public void clearData() throws DataAccessException {
        try {
            dataTransfer.clearData();
            gameIDGenerator = new GameIDGenerator();
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }

}
