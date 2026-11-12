package services;

import chess.ChessGame;
import dataobject.*;
import dataaccess.*;
import dataobject.CreateGameRequest;
import dataobject.JoinGameRequest;

import java.util.ArrayList;

public class GameService {

    private final AuthDataAccess authDataAccess;
    private final GameDataAccess gameDataAccess;

    public GameService(AuthDataAccess authDataAccess, GameDataAccess gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public ListGamesResult listGames(String authToken) throws Exception {
        AuthData authData = authDataAccess.getAuth(authToken);

        if (authData == null) {
            throw new UnauthorizedException();
        }

        ArrayList<GameData> gameList = gameDataAccess.listGames();

        return new ListGamesResult(gameList);
    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws Exception {
        AuthData authData = authDataAccess.getAuth(createGameRequest.getAuthToken());
        if (authData == null) {
            throw new UnauthorizedException();
        }

        GameData gameData = gameDataAccess.createGame(createGameRequest.getGameName());

        return new CreateGameResult(gameData.getGameID());
    }

    public ChessGame getGame(GetGameRequest getGameRequest) throws Exception {
        AuthData authData = authDataAccess.getAuth(getGameRequest.getAuthToken());
        if (authData == null) {
            throw new UnauthorizedException();
        }

        ChessGame game = gameDataAccess.getGame(getGameRequest.getGameID());
        if (game == null) {
            throw new BadRequestException();
        }

        System.out.println(game);
        System.out.println(game.getClass());

        return game;
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest) throws Exception {
        AuthData authData = authDataAccess.getAuth(joinGameRequest.getAuthToken());
        int gameID = joinGameRequest.getGameID();

        if (authData == null) {
            throw new UnauthorizedException();
        }

        GameData gameData = gameDataAccess.getGameData(gameID);
        if (gameData == null) {
            throw new BadRequestException();
        }

        // check if color available
        ChessGame.TeamColor playerColor = joinGameRequest.getPlayerColor();

        if (gameData.isTaken(playerColor)) {
            throw new AlreadyTakenException();
        }
        String username = authData.getUsername();
        gameData.fillTeamColor(playerColor, username);

        gameDataAccess.updateGame(gameData);

        return new JoinGameResult(playerColor, joinGameRequest.getGameID());
    }

    public void clear() throws Exception {
        gameDataAccess.clearData();
    }
}
