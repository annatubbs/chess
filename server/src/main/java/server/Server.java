package server;

import com.google.gson.Gson;
import io.javalin.*;
import io.javalin.http.Context;
//import org.jetbrains.annotations.NotNull;
import java.util.Map;

import server.AuthTokens.AuthToken;
import chess.ChessGame.TeamColor;


public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // each request takes url path and handler
        javalin.post("/user/{username}/{password}/{email}", Server::register);
        javalin.post("/session/{username}/{password}", Server::login);
        javalin.delete("/session/{authToken}", Server::logout);
        javalin.get("/game/{authToken}", Server::listGames);
        javalin.post("/game/{authToken}/{gameName}", Server::createGame);
        javalin.get("/game/{authToken}/{playerColor}/{gameID}", Server::joinGame);
        javalin.delete("/db", Server::clearDb);


        // Register your endpoints and exception handlers here.
//        javalin.addEndpoint("DELETE", "/db");

    }


    private static void register(Context ctx) { // username, password, email
        RegisterRequest registerRequest = new RegisterRequest(ctx.formParam("username"),
                ctx.formParam("password"), ctx.formParam("email"));
    }

    private static void login(Context ctx) { // username, password
        LoginRequest loginRequest = new LoginRequest(ctx.formParam("username"),
                ctx.formParam("password"));
    }

    private static void logout(Context ctx) { // authToken
        AuthToken authToken = new AuthToken(ctx.formParam("authToken"));
    }

    private static void listGames(Context ctx) { // authToken
        AuthToken authToken = new AuthToken(ctx.formParam("authToken"));
    }

    private static void createGame(Context ctx) { // authToken, gameName
        CreateGameRequest createGameRequest = new CreateGameRequest(new AuthToken(ctx.formParam("authToken")),
                ctx.formParam("gameName"));
    }

    private static void joinGame(Context ctx) { // authToken, playerColor, gameID // make classes here
        JoinGameRequest joinGameRequest = new JoinGameRequest(new AuthToken(ctx.formParam("authToken")),
                TeamColor.fromString(ctx.formParam("playerColor")),
                Integer.parseInt(ctx.formParam("gameID")));
    }

    private static void clearDb(Context ctx) {
        var r = Map.of();
        var json = new Gson();
        ctx.json(json.toJson(r));
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
