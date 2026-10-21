package server;

import dataaccess.DataAccess;
import io.javalin.*;
import io.javalin.http.Context;
//import org.jetbrains.annotations.NotNull;
import services.*;

import handlers.Handler;

import java.util.HashMap;


public class Server {

    private final DataAccess dataAccess;
    private final services.Service service;
    private final Javalin javalin;
    private Handler handler;

    public Server() {
        dataAccess = new DataAccess();
        service = new Service(dataAccess);
        handler = new Handler(service);

        javalin = Javalin.create(config -> config.staticFiles.add("web"));

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


    private void register(Context ctx) throws Exception { // username, password, email
        HashMap<String, Object> resultMap = handler.register(ctx);

        ctx.result(resultMap.toString());
        ctx.status(200);
    }

    private void login(Context ctx) throws Exception { // username, password
        HashMap<String, Object> resultMap = handler.login(ctx);
        ;

        ctx.result(resultMap.toString());
        ctx.status(200);
    }

    private void logout(Context ctx) throws Exception { // authToken
        HashMap<String, Object> resultMap = handler.logout(ctx);
        ;

        ctx.result(resultMap.toString());
        ctx.status(200);
    }

    private void listGames(Context ctx) throws Exception { // authToken
        HashMap<String, Object> resultMap = handler.listGames(ctx);
        ;

        ctx.result(resultMap.toString());
        ctx.status(200);
    }

    private void createGame(Context ctx) throws Exception { // authToken, gameName
        HashMap<String, Object> resultMap = handler.createGame(ctx);
        ;

        ctx.result(resultMap.toString());
        ctx.status(200);
    }

    private void joinGame(Context ctx) throws Exception { // authToken, playerColor, gameID // make classes here
        HashMap<String, Object> resultMap = handler.joinGame(ctx);
        ;

        ctx.result(resultMap.toString());
        ctx.status(200);
    }

    private void clearDb(Context ctx) throws Exception {
        if (handler.clear()) {
            ctx.status(200);
        } else { /// LOOK AT THIS
            ctx.status(500);
        }
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
