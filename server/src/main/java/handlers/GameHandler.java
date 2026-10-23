package handlers;

import com.google.gson.Gson;
import services.*;
import handlers.CreateGameRequest.GameName;
import handlers.JoinGameRequest.JoinGameHelperInfo;

import java.util.HashMap;
import java.util.Map;

public class GameHandler {

    private final GameService gameService;

    public GameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public HashMap<String, Object> listGames(String authToken) throws Exception {
        ListGamesResult listGamesResult = gameService.listGames(authToken);

        return new HashMap<>(Map.of("games", listGamesResult.getGameList())); // might throw error b/c GameData objs
    }


    public HashMap<String, Object> createGame(String authToken, String json) throws Exception {
        GameName gameName = new Gson().fromJson(json, GameName.class);
        CreateGameRequest createGameRequest = new CreateGameRequest(authToken, gameName);

        if (createGameRequest.getGameName() == null) {
            throw new BadRequestException();
        }

        CreateGameResult createGameResult = gameService.createGame(createGameRequest);

        return new HashMap<>(Map.of("gameID", createGameResult.getGameID()));
    }


    public HashMap<String, Object> joinGame(String authToken, String json) throws Exception {
        JoinGameHelperInfo colorAndID = new Gson().fromJson(json, JoinGameHelperInfo.class);
        JoinGameRequest joinGameRequest = new JoinGameRequest(authToken, colorAndID);

        JoinGameResult joinGameResult = gameService.joinGame(joinGameRequest);

        return new HashMap<>(Map.of("playerColor", joinGameResult.getPlayerColor(),
                "gameID", joinGameResult.getGameID()));
    }

    public void clear() throws Exception {
        gameService.clear();
    }
}
