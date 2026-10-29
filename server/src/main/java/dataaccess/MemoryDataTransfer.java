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
    public boolean containsAuth(String authToken) {
        return authDataMap.containsKey(authToken);
    }

    public AuthData getAuth(String authToken) {
        return authDataMap.get(authToken);
    }

    public void addAuth(AuthData authData) {
        authDataMap.put(authData.getAuthToken(), authData);
    }

    public void removeAuth(String authToken) {
        authDataMap.remove(authToken);
    }

    // User Methods
    public boolean containsUsername(String username) {
        return userDataMap.containsKey(username);
    }

    public UserData getUser(String username) {
        return userDataMap.get(username);
    }

    public void addUser(UserData userData) {
        userDataMap.put(userData.getUsername(), userData);
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

    public Set<Integer> getGameIDKeySet() {
        return gameDataMap.keySet();
    }

    public GameData getGameData(int gameID) {
        return gameDataMap.get(gameID);
    }

    public void addGameData(GameData gameData) {
        gameDataMap.put(gameData.getGameID(), gameData);
    }
}
