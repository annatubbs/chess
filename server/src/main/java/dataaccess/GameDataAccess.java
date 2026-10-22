package dataaccess;

import java.util.ArrayList;

public interface GameDataAccess {

    GameData getGame(int gameID) throws DataAccessException;

    ArrayList<GameData> listGames() throws DataAccessException;

    GameData createGame(String gameName) throws DataAccessException;

    void updateGame(GameData gameData) throws DataAccessException;

    void clearData() throws DataAccessException;
}
