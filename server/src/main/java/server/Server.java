package server;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import io.javalin.*;
import io.javalin.http.Context;
//import org.jetbrains.annotations.NotNull;
import services.*;

import server.AuthTokenSet.AuthToken;
import chess.ChessGame.TeamColor;
import handlers.Handler;


public class Server {

    private AuthTokenSet authTokenSet;
    private final DataAccess dataAccess;
    private final services.UserService userService;
    private final services.GameService gameService;
    private final Javalin javalin;
    private Handler handler;

    public Server() {
        authTokenSet = new AuthTokenSet();
        dataAccess = new DataAccess();

        userService = new UserService(dataAccess, authTokenSet);
        gameService = new GameService(dataAccess, authTokenSet);
        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        handler = new Handler();

        // each request takes url path and handler
        javalin.post("/user/{username}/{password}/{email}", Server::register);
        javalin.post("/session/{username}/{password}", Server::login);
        javalin.delete("/session/{authToken}", Server::logout);
        javalin.get("/game/{authToken}", Server::listGames);
        javalin.post("/game/{authToken}/{gameName}", Server::createGame);
        javalin.put("/game/{authToken}/{playerColor}/{gameID}", Server::joinGame);
        javalin.delete("/db", Server::clearDb);


        // Register your endpoints and exception handlers here.
//        javalin.addEndpoint("DELETE", "/db");

    }


    private void register(Context ctx) { // username, password, email
        handler.register(ctx);
    }

    private void login(Context ctx) { // username, password
        handler.login(ctx);
    }

    private void logout(Context ctx) { // authToken
        handler.logout(ctx);
    }

    private void listGames(Context ctx) { // authToken
        handler.listGames(ctx);
    }

    private void createGame(Context ctx) { // authToken, gameName
        handler.createGame(ctx);
    }

    private void joinGame(Context ctx) { // authToken, playerColor, gameID // make classes here
        handler.joinGame(ctx);
    }

    private void clearDb(Context ctx) {
        handler.clear();
//        var r = Map.of();
//        var json = new Gson();
//        ctx.json(json.toJson(r));
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
