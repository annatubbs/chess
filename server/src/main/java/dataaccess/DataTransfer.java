package dataaccess;

import handlers.AuthData;
import handlers.UserData;

public interface DataTransfer {

    public boolean containsAuth();

    public void addAuth(AuthData authData);

    public void removeAuth(String authToken);


    public boolean containsUserName(String username);

    public boolean addUser(UserData userData);

    public void clearData();


    public boolean containsGameID(int gameID);

    public GameData getGameData(int gameID);

    public void addGameData(GameData gameData);
}
