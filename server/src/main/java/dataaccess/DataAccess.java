package dataaccess;

import handlers.AuthData;
import handlers.UserData;
import server.AuthTokenSet;

import java.util.ArrayList;

public class DataAccess {

    public DataAccess() {
    }

    public UserData getUser(String username) {
    }

    public AuthData getAuth(AuthTokenSet.AuthToken authToken) {
    }

    public GameData getGame(int gameID) {
    }

    public ArrayList<GameData> listGames() {
    }

    public GameData createGame(String gameName) {
    }

    public void createUser(UserData userData) {
    }

    public void updateGame(GameData gameData) {
    }

    public void deleteAuth(AuthTokenSet.AuthToken authToken) {
    }

    public boolean clearData() {
    }

}
