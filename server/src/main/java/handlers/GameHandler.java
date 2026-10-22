package handlers;

import chess.ChessGame;
import com.google.gson.Gson;
import io.javalin.http.Context;
import server.AuthTokenSet;
import services.CreateGameResult;
import services.GameService;
import services.JoinGameResult;
import services.ListGamesResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GameHandler {

    private final GameService gameService;

    public GameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public HashMap<String, Object> listGames(String json) throws Exception {
//        AuthTokenSet.AuthToken authToken = new AuthTokenSet.AuthToken(ctx.formParam("authToken"));
        AuthTokenSet.AuthToken authToken = new Gson().fromJson(json, AuthTokenSet.AuthToken.class);

        ListGamesResult listGamesResult = gameService.listGames(authToken);

        return new HashMap<>(Map.of("games", listGamesResult.getGameList())); // might throw error b/c GameData objs
    }


    public HashMap<String, Object> createGame(String json) throws Exception {
//        CreateGameRequest createGameRequest = new CreateGameRequest(new AuthTokenSet.AuthToken(ctx.formParam("authToken")),
//                ctx.formParam("gameName"));
        CreateGameRequest createGameRequest = new Gson().fromJson(json, CreateGameRequest.class);

        CreateGameResult createGameResult = gameService.createGame(createGameRequest);

        return new HashMap<>(Map.of("gameID", createGameResult.getGameID()));
    }


    public HashMap<String, Object> joinGame(String json) throws Exception {
//        JoinGameRequest joinGameRequest = new JoinGameRequest(new AuthTokenSet.AuthToken(ctx.formParam("authToken")),
//                ChessGame.TeamColor.fromString(ctx.formParam("playerColor")),
//                Integer.parseInt(Objects.requireNonNull(ctx.formParam("gameID"))));
        JoinGameRequest joinGameRequest = new Gson().fromJson(json, JoinGameRequest.class);
        
        JoinGameResult joinGameResult = gameService.joinGame(joinGameRequest);

        return new HashMap<>(Map.of("playerColor", joinGameResult.getPlayerColor(),
                "gameID", joinGameResult.getGameID()));
    }

    public void clear() throws Exception {
        gameService.clear();
    }
}
