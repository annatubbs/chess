package handlers;

import chess.ChessGame;
import dataaccess.GameDataAccess;
import io.javalin.http.Context;
import server.*;
import services.*;
import server.AuthTokenSet.*;

import java.util.HashMap;
import java.util.Map;

public class Handler {

    private final services.Service service;

    public Handler(Service service) {
        this.service = service;
    }


    // User Handles

    public HashMap<String, Object> register(Context ctx) throws Exception {
        RegisterRequest registerRequest = new RegisterRequest(ctx.formParam("username"),
                ctx.formParam("password"), ctx.formParam("email"));

        RegisterResult registerResult = userService.register(registerRequest);

        return new HashMap<>(Map.of("username", registerResult.getUsername(),
                "authToken", registerResult.getAuthToken()));
    }


    public HashMap<String, Object> login(Context ctx) throws Exception {
        LoginRequest loginRequest = new LoginRequest(ctx.formParam("username"),
                ctx.formParam("password"));

        LoginResult loginResult = userService.login(loginRequest);

        return new HashMap<>(Map.of("username", loginResult.getUsername(),
                "authToken", loginResult.getAuthToken()));
    }


    public HashMap<String, Object> logout(Context ctx) throws Exception {
        AuthToken authToken = new AuthToken(ctx.formParam("authToken"));

        LogoutResult logoutResult = userService.logout(authToken);
        return new HashMap<>(Map.of("username", logoutResult.getUsername()));
    }


    // Game Handles

    public HashMap<String, Object> listGames(Context ctx) throws Exception {
        AuthToken authToken = new AuthToken(ctx.formParam("authToken"));

        ListGamesResult listGamesResult = gameService.listGames(authToken);

        return new HashMap<>(Map.of("games", listGamesResult.getGameList())); // might throw error b/c GameData objs
    }


    public HashMap<String, Object> createGame(Context ctx) throws Exception {
        CreateGameRequest createGameRequest = new CreateGameRequest(new AuthToken(ctx.formParam("authToken")),
                ctx.formParam("gameName"));

        CreateGameResult createGameResult = gameService.createGame(createGameRequest);

        return new HashMap<>(Map.of("gameID", createGameResult.getGameID()));
    }


    public HashMap<String, Object> joinGame(Context ctx) throws Exception {
        JoinGameRequest joinGameRequest = new JoinGameRequest(new AuthToken(ctx.formParam("authToken")),
                ChessGame.TeamColor.fromString(ctx.formParam("playerColor")),
                Integer.parseInt(ctx.formParam("gameID")));

        JoinGameResult joinGameResult = gameService.joinGame(joinGameRequest);

        return new HashMap<>(Map.of("playerColor", joinGameResult.getPlayerColor(),
                "gameID", joinGameResult.getGameID()));
    }

    public boolean clear() throws Exception {
        return gameService.clear();
    }
}
