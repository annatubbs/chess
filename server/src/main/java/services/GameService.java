package services;

import chess.ChessGame;
import dataaccess.*;
import handlers.AuthData;
import handlers.CreateGameRequest;
import handlers.JoinGameRequest;
import server.AuthTokenSet;

import java.util.ArrayList;

public class GameService {

    private final AuthDataAccess authDataAccess;
    private final GameDataAccess gameDataAccess;

    public GameService(AuthDataAccess authDataAccess, GameDataAccess gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public ListGamesResult listGames(AuthTokenSet.AuthToken authToken) {
//        AuthData authData = dataAccess.getAuth(listGamesRequest.getAuthToken());
        AuthData authData = authDataAccess.getAuth(authToken);

        if (authData == null) {
            throw new UnauthorizedException("Not authorized to see games.");
        }

        ArrayList<GameData> gameList = gameDataAccess.listGames();

        return new ListGamesResult(gameList);
    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest) {
        AuthData authData = authDataAccess.getAuth(createGameRequest.getAuthToken());
        if (authData == null) {
            throw new UnauthorizedException("Not authorized to create game.");
        }

        GameData gameData = gameDataAccess.createGame(createGameRequest.getGameName());

        return new CreateGameResult(gameData.getGameID());
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest) throws Exception {
        AuthData authData = authDataAccess.getAuth(joinGameRequest.getAuthToken());
        if (authData == null) {
            throw new UnauthorizedException("Not authorized to join game.");
        }

        GameData gameData = gameDataAccess.getGame(joinGameRequest.getGameID());

        // check if color available
        ChessGame.TeamColor playerColor = joinGameRequest.getPlayerColor();

        if (gameData.isTaken(playerColor)) {
            throw new AlreadyTakenException(playerColor.toString() + " position already full.");
        }
        String username = authData.getUsername();
        gameData.fillTeamColor(playerColor, username);

        gameDataAccess.updateGame(gameData);

        return new JoinGameResult(playerColor, joinGameRequest.getGameID());
    }

    public void clear() {
        gameDataAccess.clearData();
    }
}
