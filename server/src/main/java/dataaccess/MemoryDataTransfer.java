package dataaccess;

import dataObject.GameData;
import dataObject.AuthData;
import dataObject.UserData;

import java.util.HashMap;
import java.util.Set;

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
    public boolean containsAuth(String authToken) throws DataAccessException {
        try {
            return authDataMap.containsKey(authToken);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        try {
            return authDataMap.get(authToken);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void addAuth(AuthData authData) throws DataAccessException {
        try {
        authDataMap.put(authData.getAuthToken(), authData);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void removeAuth(String authToken) throws DataAccessException {
        try {
            authDataMap.remove(authToken);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    // User Methods
    public boolean containsUsername(String username) throws DataAccessException {
        try {
            return userDataMap.containsKey(username);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
}

    public UserData getUser(String username) throws DataAccessException {
        try {
            return userDataMap.get(username);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
}

    public void addUser(UserData userData) throws DataAccessException {
        try {
            userDataMap.put(userData.getUsername(), userData);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
}

    // Data Methods
    public void clearData() throws DataAccessException {
        try {
            authDataMap = new HashMap<>();
            userDataMap = new HashMap<>();
            gameDataMap = new HashMap<>();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
}

    public boolean containsGameID(int gameID) throws DataAccessException {
        try {
            return gameDataMap.containsKey(gameID);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
}

    public Set<Integer> getGameIDKeySet() throws DataAccessException {
        try {
            return gameDataMap.keySet();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
}

    public GameData getGameData(int gameID) throws DataAccessException {
        try {
            return gameDataMap.get(gameID);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
}

    public void addGameData(GameData gameData) throws DataAccessException {
        try {
            gameDataMap.put(gameData.getGameID(), gameData);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
}
}
