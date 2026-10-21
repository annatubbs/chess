package handlers;

import chess.ChessGame;
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

    public HashMap<String, Object> listGames(Context ctx) throws Exception {
        AuthTokenSet.AuthToken authToken = new AuthTokenSet.AuthToken(ctx.formParam("authToken"));

        ListGamesResult listGamesResult = gameService.listGames(authToken);

        return new HashMap<>(Map.of("games", listGamesResult.getGameList())); // might throw error b/c GameData objs
    }


    public HashMap<String, Object> createGame(Context ctx) throws Exception {
        CreateGameRequest createGameRequest = new CreateGameRequest(new AuthTokenSet.AuthToken(ctx.formParam("authToken")),
                ctx.formParam("gameName"));

        CreateGameResult createGameResult = gameService.createGame(createGameRequest);

        return new HashMap<>(Map.of("gameID", createGameResult.getGameID()));
    }


    public HashMap<String, Object> joinGame(Context ctx) throws Exception {
        JoinGameRequest joinGameRequest = new JoinGameRequest(new AuthTokenSet.AuthToken(ctx.formParam("authToken")),
                ChessGame.TeamColor.fromString(ctx.formParam("playerColor")),
                Integer.parseInt(Objects.requireNonNull(ctx.formParam("gameID"))));

        JoinGameResult joinGameResult = gameService.joinGame(joinGameRequest);

        return new HashMap<>(Map.of("playerColor", joinGameResult.getPlayerColor(),
                "gameID", joinGameResult.getGameID()));
    }

    public boolean clear() throws Exception {
        return gameService.clear();
    }
}
