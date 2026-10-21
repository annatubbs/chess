package server;

import dataaccess.*;
import handlers.GameHandler;
import handlers.UserHandler;
import io.javalin.*;
import io.javalin.http.Context;
import services.*;

import handlers.Handler;

import java.util.HashMap;
import java.util.Map;


public class Server {

    private final UserHandler userHandler;
    private final GameHandler gameHandler;
    private final Javalin javalin;


    public Server() {
//        dataAccessMap = new HashMap<>(Map.of("AuthDataAccess", new AuthDataAccess(),
//                "UserDataAccess", new UserDataAccess(),
//                "GameDataAccess", new GameDataAccess()));

        Service service = new Service(new MemoryDataAccess());
        Handler handler = new Handler(service);

        userHandler = handler.getUserHandler();
        gameHandler = handler.getGameHandler();
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // each request takes url path and handler
        javalin.post("/user/{username}/{password}/{email}", this::register);
        javalin.post("/session/{username}/{password}", this::login);
        javalin.delete("/session/{authToken}", this::logout);
        javalin.get("/game/{authToken}", this::listGames);
        javalin.post("/game/{authToken}/{gameName}", this::createGame);
        javalin.put("/game/{authToken}/{playerColor}/{gameID}", this::joinGame);
        javalin.delete("/db", this::clearDb);

    }


    private void register(Context ctx) throws Exception { // username, password, email
        try {
            HashMap<String, Object> resultMap = userHandler.register(ctx);

//            ctx.result(resultMap.toString());
            ctx.status(200).json(resultMap);
        } catch (DataAccessException e) {
            ctx.status(500).json(Map.of("message", e.getMessage()));
        }
    }

    private void login(Context ctx) throws Exception { // username, password
        try {
            HashMap<String, Object> resultMap = userHandler.login(ctx);

//            ctx.result(resultMap.toString());
            ctx.status(200).json(resultMap);
        } catch (DataAccessException e) {
            ctx.status(500).json(Map.of("message", e.getMessage()));
        }
    }

    private void logout(Context ctx) throws Exception { // authToken
        try {
            HashMap<String, Object> resultMap = userHandler.logout(ctx);

//            ctx.result(resultMap.toString());
            ctx.status(200).json(resultMap);
        } catch (DataAccessException e) {
            ctx.status(500).json(Map.of("message", e.getMessage()));
        }
    }

    private void listGames(Context ctx) throws Exception { // authToken
        try {
            HashMap<String, Object> resultMap = gameHandler.listGames(ctx);

//            ctx.result(resultMap.toString());
            ctx.status(200).json(resultMap);
        } catch (DataAccessException e) {
            ctx.status(500).json(Map.of("message", e.getMessage()));
        }
    }

    private void createGame(Context ctx) throws Exception { // authToken, gameName
        try {
            HashMap<String, Object> resultMap = gameHandler.createGame(ctx);

//            ctx.result(resultMap.toString());
            ctx.status(200).json(resultMap);
        } catch (DataAccessException e) {
            ctx.status(500).json(Map.of("message", e.getMessage()));
        }
    }

    private void joinGame(Context ctx) throws Exception { // authToken, playerColor, gameID // make classes here
        try {
            HashMap<String, Object> resultMap = gameHandler.joinGame(ctx);

//            ctx.result(resultMap.toString());
            ctx.status(200).json(resultMap);
        } catch (DataAccessException e) {
            ctx.status(500).json(Map.of("message", e.getMessage()));
        }
    }

    private void clearDb(Context ctx) throws Exception {
        try {
            gameHandler.clear();
            ctx.status(200);
        } catch (DataAccessException e) {
            ctx.status(500).json(Map.of("message", e.getMessage()));
        }
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
