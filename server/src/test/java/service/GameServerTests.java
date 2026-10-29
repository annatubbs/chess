package service;

import chess.ChessGame;
import handlers.CreateGameRequest;
import handlers.GameHandler;
import handlers.JoinGameRequest;
import handlers.LoginRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.*;

import java.util.ArrayList;

public class GameServerTests extends IsolatedServerTests {
    static GameService gameService;
    static GameHandler gameHandler;
    CreateGameResult createGameResult;
    ListGamesResult listGamesResult;

    @BeforeEach
    public void setUpGameTools() {
        gameService = new GameService(authDataAccess, gameDataAccess);
        gameHandler = new GameHandler(gameService);
    }

    public void createGame() {
        try {
            CreateGameRequest createGameRequest = new CreateGameRequest(registerResult.getAuthToken(),
                    new CreateGameRequest.GameName("myGame"));
            createGameResult = gameService.createGame(createGameRequest);
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    @Test
    public void listGamesSuccessTest() {
        setUp();
        register();
        createGame();

        try {
            listGamesResult = gameService.listGames(registerResult.getAuthToken());
            Assertions.assertNotEquals(new ArrayList<>(), listGamesResult.getGameList());
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    @Test
    public void listGameUnauthorizedTest() {
        setUp();
        register();
        createGame();

        Assertions.assertThrows(UnauthorizedException.class, () -> gameService.listGames("FAKE_TOKEN"));
    }

    @Test
    public void createGameSuccessTest() {
        setUp();
        register();
        createGame();

        Assertions.assertNotNull(createGameResult.getGameID());
    }

    @Test
    public void createGameBadRequestTest() {
        setUp();
        register();

        Assertions.assertThrows(BadRequestException.class, () -> gameService.createGame(
                new CreateGameRequest(registerResult.getAuthToken(), null)));
    }

    @Test
    public void joinGameSuccessTest() {
        setUp();
        register();
        createGame();

        try {
            JoinGameRequest joinGameRequest = new JoinGameRequest(registerResult.getAuthToken(),
                    new JoinGameRequest.JoinGameHelperInfo("black", createGameResult.getGameID()));

            JoinGameResult joinGameResult = gameService.joinGame(joinGameRequest);

            Assertions.assertEquals(ChessGame.TeamColor.BLACK, joinGameResult.getPlayerColor());
            Assertions.assertEquals(createGameResult.getGameID(), joinGameResult.getGameID());
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    @Test
    public void joinGameAlreadyTakenTest() {
        setUp();
        register();
        createGame();

        JoinGameRequest joinGameRequest = new JoinGameRequest(registerResult.getAuthToken(),
                new JoinGameRequest.JoinGameHelperInfo("black", createGameResult.getGameID()));

        try {
            JoinGameResult joinGameResult = gameService.joinGame(joinGameRequest);
        } catch (Exception e) {
            System.out.println("Exception");
        }

        Assertions.assertThrows(AlreadyTakenException.class, () -> gameService.joinGame(joinGameRequest));
    }

    @Test
    public void clearSuccessTest() {
        setUp();
        register();
        createGame();

        try {
            gameService.clear();
            Assertions.assertEquals(new ArrayList<>(), listGamesResult.getGameList());
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

}
