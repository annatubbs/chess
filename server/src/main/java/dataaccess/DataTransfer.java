package dataaccess;

import handlers.AuthData;
import handlers.UserData;

public interface DataTransfer {

    public boolean containsAuth(String AuthToken);

    public void addAuth(AuthData authData) throws DataAccessException;

    public void removeAuth(String authToken) throws DataAccessException;


    public boolean containsUsername(String username);

    public void addUser(UserData userData) throws DataAccessException;

    public void clearData();


    public boolean containsGameID(int gameID);

    public GameData getGameData(int gameID) throws DataAccessException;

    public void addGameData(GameData gameData) throws DataAccessException;
}
