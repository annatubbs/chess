package handlers;

import chess.ChessGame;
import dataaccess.DataAccess;
import io.javalin.http.Context;
import server.*;
import services.*;
import server.AuthTokenSet.*;

import java.util.HashMap;
import java.util.Map;

public class Handler {

    private AuthTokenSet authTokenSet;
    private final DataAccess dataAccess;
    private final services.UserService userService;
    private final services.GameService gameService;

    public Handler(AuthTokenSet authTokenSet, DataAccess dataAccess, UserService userService, GameService gameService) {
        this.dataAccess = dataAccess;
        this.authTokenSet = authTokenSet;
        this.userService = userService;
        this.gameService = gameService;
    }


    // User Handles

    public HashMap<String,Object> register(Context ctx) throws Exception {
        RegisterRequest registerRequest = new RegisterRequest(ctx.formParam("username"),
                ctx.formParam("password"), ctx.formParam("email"));

        RegisterResult registerResult = userService.register(registerRequest);

        return new HashMap<>(Map.of("username", registerResult.getUsername(),
                                    "authToken", registerResult.getAuthToken()));
    }


    public HashMap<String,Object> login(Context ctx) throws Exception {
        LoginRequest loginRequest = new LoginRequest(ctx.formParam("username"),
                ctx.formParam("password"));

        LoginResult loginResult = userService.login(loginRequest);

        return new HashMap<>(Map.of("username", loginResult.getUsername(),
                                    "authToken", loginResult.getAuthToken()));
    }


    public HashMap<String,Object> logout(Context ctx) throws Exception {
        AuthToken authToken = new AuthToken(ctx.formParam("authToken"));

        LogoutResult logoutResult = userService.logout(authToken);
        return new HashMap<>(Map.of("username", logoutResult.getUsername()));
    }


    // Game Handles

    public ListGamesResult listGames(Context ctx) {
        AuthToken authToken = new AuthToken(ctx.formParam("authToken"));

        return
    }


    public CreateGameResult createGame(Context ctx) {
        CreateGameRequest createGameRequest = new CreateGameRequest(new AuthToken(ctx.formParam("authToken")),
                ctx.formParam("gameName"));

        return
    }


    public JoinGameResult joinGame(Context ctx) {
        JoinGameRequest joinGameRequest = new JoinGameRequest(new AuthToken(ctx.formParam("authToken")),
                ChessGame.TeamColor.fromString(ctx.formParam("playerColor")),
                Integer.parseInt(ctx.formParam("gameID")));

        return
    }

    public ClearResult clear() {
        // or return bool?
        return
    }
}
