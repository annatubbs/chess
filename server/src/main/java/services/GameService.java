package services;

import dataaccess.DataAccess;
import dataaccess.GameData;
import handlers.AuthData;
import handlers.ListGamesRequest;
import server.AuthTokenSet;

import java.util.ArrayList;

public class GameService {
    private final DataAccess dataAccess;
    private AuthTokenSet authTokensSet;

    public GameService(DataAccess dataAccess, AuthTokenSet authTokensSet) {
        this.dataAccess = dataAccess;
        this.authTokensSet = authTokensSet;
    }

    public ListGamesResult listGames(ListGamesRequest listGamesRequest) {
        AuthData authData = dataAccess.getAuth(listGamesRequest.getAuthToken());
        if (authData == null) {
            throw new UnauthorizedException("Not authorized to see games.");
        }

        ArrayList<GameData> gameList = dataAccess.listGames();

        return new ListGamesResult(gameList);
    }
    

}
