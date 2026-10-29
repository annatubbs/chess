package dataaccess;

import handlers.AuthData;
import handlers.UserData;
import services.BadRequestException;

import java.util.HashMap;

public class MemoryDataTransfer implements DataTransfer {
    private HashMap<String, AuthData> authDataMap; // authToken: username
    private HashMap<String, UserData> userDataMap; // Username: password, email
    private HashMap<Integer, GameData> gameDataMap;

    public MemoryDataTransfer() {
        authDataMap = new HashMap<>();
        userDataMap = new HashMap<>();
        gameDataMap = new HashMap<>();
    }

    // Authorization Methods
    public boolean containsAuth(String authToken) {
        return authDataMap.containsKey(authToken);
    }

    public void addAuth(AuthData authData) throws DataAccessException {
        String authToken = authData.getAuthToken();

        if (containsAuth(authToken)) {
            throw new DataAccessException("Error: authToken already in database.");
        }
        authDataMap.put(authToken, authData);
    }

    public void removeAuth(String authToken) throws DataAccessException {
        if (!containsAuth(authToken)) {
            throw new DataAccessException("Error: authToken not in database");
        }
        authDataMap.remove(authToken);
    }

    // User Methods
    public boolean containsUsername(String username) {
        return userDataMap.containsKey(username);
    }

    public UserData getUser(String username) throws DataAccessException { 
        if (!containsUsername(username)) {
            throw new DataAccessException("Error: username not in database");

        }
        return userDataMap.get(username);
    }

    public void addUser(UserData userData) throws DataAccessException {
        String username = userData.getUsername();

        if (containsUsername(username)) {
            throw new DataAccessException("Error: username already in database.");
        }
        userDataMap.put(username, userData);
    }

    // Data Methods
    public void clearData() {
        authDataMap = new HashMap<>();
        userDataMap = new HashMap<>();
        gameDataMap = new HashMap<>();
    }

    public boolean containsGameID(int gameID) {
        return gameDataMap.containsKey(gameID);
    }

    public GameData getGameData(int gameID) throws DataAccessException {
        if (!containsGameID(gameID)) {
            throw new DataAccessException("Error: gameID not in database.");
        }
        return gameDataMap.get(gameID);
    }

    public void addGameData(GameData gameData) throws DataAccessException {
        int gameID = gameData.getGameID();

        if (containsGameID(gameID)) {
            throw new DataAccessException("Error: gameID not in database.");
        }
        gameDataMap.put(gameID, gameData);
    }
}
