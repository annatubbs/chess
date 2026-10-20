package handlers;

import chess.ChessGame;
import io.javalin.http.Context;
import server.*;
import services.*;

public class Handler {

    public Handler() {
    }


    public RegisterResult register(Context ctx) {
        RegisterRequest registerRequest = new RegisterRequest(ctx.formParam("username"),
                ctx.formParam("password"), ctx.formParam("email"));

        return
    }


    public LoginResult login(Context ctx) {
        LoginRequest loginRequest = new LoginRequest(ctx.formParam("username"),
                ctx.formParam("password"));

        return
    }


    public LogoutResult logout(Context ctx) {
        AuthTokens.AuthToken authToken = new AuthToken(ctx.formParam("authToken"));

        return
    }


    public ListGamesResult listGames(Context ctx) {
        AuthTokens.AuthToken authToken = new AuthTokens.AuthToken(ctx.formParam("authToken"));

        return
    }


    public CreateGameResult createGame(Context ctx) {
        CreateGameRequest createGameRequest = new CreateGameRequest(new AuthTokens.AuthToken(ctx.formParam("authToken")),
                ctx.formParam("gameName"));

        return
    }


    public JoinGameResult joinGame(Context ctx) {
        JoinGameRequest joinGameRequest = new JoinGameRequest(new AuthTokens.AuthToken(ctx.formParam("authToken")),
                ChessGame.TeamColor.fromString(ctx.formParam("playerColor")),
                Integer.parseInt(ctx.formParam("gameID")));

        return
    }

    public ClearResult clear() {
        // or return bool?
        return
    }
}
