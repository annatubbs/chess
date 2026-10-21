package server;

import dataaccess.AuthDataAccess;
import dataaccess.DataAccess;
import dataaccess.GameDataAccess;
import dataaccess.UserDataAccess;
import handlers.GameHandler;
import handlers.UserHandler;
import io.javalin.*;
import io.javalin.http.Context;
//import org.jetbrains.annotations.NotNull;
import services.*;

import handlers.Handler;

import java.util.HashMap;
import java.util.Map;


public class Server {

    //    private final DataAccess dataAccess;
//    private final services.Service service;
    private final UserHandler userHandler;
    private final GameHandler gameHandler;
    private final Javalin javalin;


    public Server() {
//        dataAccessMap = new HashMap<>(Map.of("AuthDataAccess", new AuthDataAccess(),
//                "UserDataAccess", new UserDataAccess(),
//                "GameDataAccess", new GameDataAccess()));

        Service service = new Service(new DataAccess());
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


        // Register your endpoints and exception handlers here.
//        javalin.addEndpoint("DELETE", "/db");

    }


    private void register(Context ctx) throws Exception { // username, password, email
        HashMap<String, Object> resultMap = userHandler.register(ctx);

        ctx.result(resultMap.toString());
        ctx.status(200);
    }

    private void login(Context ctx) throws Exception { // username, password
        HashMap<String, Object> resultMap = userHandler.login(ctx);

        ctx.result(resultMap.toString());
        ctx.status(200);
    }

    private void logout(Context ctx) throws Exception { // authToken
        HashMap<String, Object> resultMap = userHandler.logout(ctx);
        ;

        ctx.result(resultMap.toString());
        ctx.status(200);
    }

    private void listGames(Context ctx) throws Exception { // authToken
        HashMap<String, Object> resultMap = gameHandler.listGames(ctx);
        ;

        ctx.result(resultMap.toString());
        ctx.status(200);
    }

    private void createGame(Context ctx) throws Exception { // authToken, gameName
        HashMap<String, Object> resultMap = gameHandler.createGame(ctx);
        ;

        ctx.result(resultMap.toString());
        ctx.status(200);
    }

    private void joinGame(Context ctx) throws Exception { // authToken, playerColor, gameID // make classes here
        HashMap<String, Object> resultMap = gameHandler.joinGame(ctx);
        ;

        ctx.result(resultMap.toString());
        ctx.status(200);
    }

    private void clearDb(Context ctx) throws Exception {
        if (gameHandler.clear()) {
            ctx.status(200);
        } else { /// LOOK AT THIS***************
            ctx.status(500);
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
