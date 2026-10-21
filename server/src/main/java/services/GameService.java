package services;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.GameData;
import handlers.AuthData;
import handlers.CreateGameRequest;
import handlers.JoinGameRequest;
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

    public CreateGameResult createGame(CreateGameRequest createGameRequest) {
        AuthData authData = dataAccess.getAuth(createGameRequest.getAuthToken());
        if (authData == null) {
            throw new UnauthorizedException("Not authorized to create game.");
        }

        GameData gameData = dataAccess.createGame(createGameRequest.getGameName());

        return new CreateGameResult(gameData.getGameID());
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest) throws Exception {
        AuthData authData = dataAccess.getAuth(joinGameRequest.getAuthToken());
        if (authData == null) {
            throw new UnauthorizedException("Not authorized to join game.");
        }

        GameData gameData = dataAccess.getGame(joinGameRequest.getGameID());

        // check if color available
        ChessGame.TeamColor playerColor = joinGameRequest.getPlayerColor();

        if (gameData.isTaken(playerColor)) {
            throw new AlreadyTakenException(playerColor.toString() + " position already full.");
        }
        String username = authData.getUsername();
        gameData.fillTeamColor(playerColor, username);

        dataAccess.updateGame(gameData);

        return new JoinGameResult(playerColor, joinGameRequest.getGameID());
    }

    public boolean clear() {
        return dataAccess.clearData();
    }
}
