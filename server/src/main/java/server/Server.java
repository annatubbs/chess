package server;

import com.google.gson.Gson;
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
    private DataTransfer dataTransfer;


    public Server() {
        dataTransfer = new MemoryDataTransfer();
        Service service = new Service(new DataAccess(dataTransfer));
        Handler handler = new Handler(service);

        userHandler = handler.getUserHandler();
        gameHandler = handler.getGameHandler();
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // each request takes url path and handler
        javalin.post("/user", this::register);
        javalin.post("/session", this::login);
        javalin.delete("/session", this::logout);
        javalin.get("/game", this::listGames);
        javalin.post("/game", this::createGame);
        javalin.put("/game", this::joinGame);
        javalin.delete("/db", this::clearDb);
    }


    private void register(Context ctx) throws Exception { // username, password, email
        try {
            HashMap<String, Object> resultMap = userHandler.register(ctx.body());

            ctx.status(200);
            ctx.contentType("application/json");
            ctx.result(new Gson().toJson(resultMap));
        } catch (BadRequestException e) {
            ctx.status(400);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        } catch (AlreadyTakenException e) {
            ctx.status(403);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        } catch (DataAccessException e) {
            ctx.status(500);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        }
    }

    private void login(Context ctx) throws Exception { // username, password
        try {
            HashMap<String, Object> resultMap = userHandler.login(ctx.body());

            ctx.status(200);
            ctx.contentType("application/json");
            ctx.result(new Gson().toJson(resultMap));
        } catch (BadRequestException e) {
            ctx.status(400);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        } catch (UnauthorizedException e) {
            ctx.status(401);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        } catch (DataAccessException e) {
            ctx.status(500);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        }
    }

    private void logout(Context ctx) throws Exception { // authToken
        try {
            userHandler.logout(ctx.header("Authorization"));

            ctx.status(200);
            ctx.contentType("application/json");
        } catch (UnauthorizedException e) {
            ctx.status(401);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        } catch (DataAccessException e) {
            ctx.status(500);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        }
    }

    private void listGames(Context ctx) throws Exception { // authToken
        try {
            HashMap<String, Object> resultMap = gameHandler.listGames(ctx.header("Authorization"));

            ctx.status(200);
            ctx.contentType("application/json");
            ctx.result(new Gson().toJson(resultMap));
        } catch (UnauthorizedException e) {
            ctx.status(401);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        } catch (DataAccessException e) {
            ctx.status(500);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        }
    }

    private void createGame(Context ctx) throws Exception { // authToken, gameName
        try {
            HashMap<String, Object> resultMap = gameHandler.createGame(ctx.header("Authorization"), ctx.body());

            ctx.status(200);
            ctx.contentType("application/json");
            ctx.result(new Gson().toJson(resultMap));
        } catch (BadRequestException e) {
            ctx.status(400);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        } catch (UnauthorizedException e) {
            ctx.status(401);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        } catch (DataAccessException e) {
            ctx.status(500);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        }
    }

    private void joinGame(Context ctx) throws Exception { // authToken, playerColor, gameID // make classes here
        try {
            HashMap<String, Object> resultMap = gameHandler.joinGame(ctx.header("Authorization"), ctx.body());

            ctx.status(200);
            ctx.contentType("application/json");
            ctx.result(new Gson().toJson(resultMap));
        } catch (BadRequestException e) {
            ctx.status(400);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        } catch (UnauthorizedException e) {
            ctx.status(401);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        } catch (AlreadyTakenException e) {
            ctx.status(403);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        } catch (DataAccessException e) {
            ctx.status(500);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        }
    }

    private void clearDb(Context ctx) throws Exception {
        try {
            gameHandler.clear();
            ctx.status(200);
        } catch (DataAccessException e) {
            ctx.status(500);
            ctx.json(new Gson().toJson(Map.of("message", e.getMessage())));
        }
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }

    public int getPort() {
        return javalin.port();
    }
}
