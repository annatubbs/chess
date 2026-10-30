package dataaccess;

import dataObject.GameData;
import dataObject.AuthData;
import dataObject.UserData;

import java.util.Set;

public interface DataTransfer {
    // Authorization Methods
    public boolean containsAuth(String AuthToken) throws DataAccessException;

    public AuthData getAuth(String authToken) throws DataAccessException;

    public void addAuth(AuthData authData) throws DataAccessException;

    public void removeAuth(String authToken) throws DataAccessException;

    // User Methods
    public boolean containsUsername(String username) throws DataAccessException;

    public UserData getUser(String username) throws DataAccessException;

    public void addUser(UserData userData) throws DataAccessException;

    // Game Methods
    public void clearData() throws DataAccessException;

    public boolean containsGameID(int gameID) throws DataAccessException;

    public Set<Integer> getGameIDKeySet() throws DataAccessException;

    public GameData getGameData(int gameID) throws DataAccessException;

    public void addGameData(GameData gameData) throws DataAccessException;

    public void updateGameData(GameData gameData) throws DataAccessException;
}
