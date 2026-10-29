package dataaccess;

import handlers.AuthData;
import handlers.UserData;

import java.util.Set;

public interface DataTransfer {

    public boolean containsAuth(String AuthToken);

    public AuthData getAuth(String authToken);

    public void addAuth(AuthData authData);

    public void removeAuth(String authToken);


    public boolean containsUsername(String username);

    public UserData getUser(String username);

    public void addUser(UserData userData);

    public void clearData();


    public boolean containsGameID(int gameID);

    public Set<Integer> getGameIDKeySet();

    public GameData getGameData(int gameID);

    public void addGameData(GameData gameData);
}
