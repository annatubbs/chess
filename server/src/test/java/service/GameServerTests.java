package service;

import chess.ChessGame;
import dataobject.*;
import handlers.GameHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import services.AlreadyTakenException;
import services.BadRequestException;
import services.GameService;
import services.UnauthorizedException;

import java.util.ArrayList;

public class GameServerTests extends IsolatedServerTests {
    static GameService gameService;
    static GameHandler gameHandler;
    CreateGameResult createGameResult;
    ListGamesResult listGamesResult;


    public void setUpGameTools(Path path) {
        setUp(path);
        gameService = new GameService(authDataAccess, gameDataAccess);
        gameHandler = new GameHandler(gameService);
    }

    public void createGame() {
        try {
            CreateGameRequest createGameRequest = new CreateGameRequest(registerResult.getAuthToken(),
                    new CreateGameRequest.GameName("myGame"));
            createGameResult = gameService.createGame(createGameRequest);
        } catch (Exception e) {
            System.out.println("Exception in gameServerTests: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void listGamesSuccessTest(Path path) {
        setUpGameTools(path);
        register();
        createGame();

        try {
            listGamesResult = gameService.listGames(registerResult.getAuthToken());
            Assertions.assertNotEquals(new ArrayList<>(), listGamesResult.getGameList());
        } catch (Exception e) {
            System.out.println("Exception in gameServerTests: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void listGameUnauthorizedTest(Path path) {
        setUpGameTools(path);
        register();
        createGame();

        Assertions.assertThrows(UnauthorizedException.class, () -> gameService.listGames("FAKE_TOKEN"));
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void createGameSuccessTest(Path path) {
        setUpGameTools(path);
        register();
        createGame();

        Assertions.assertNotNull(createGameResult.getGameID());
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void createGameBadRequestTest(Path path) {
        setUpGameTools(path);
        register();

        Assertions.assertThrows(BadRequestException.class, () -> gameService.createGame(
                new CreateGameRequest(registerResult.getAuthToken(), null)));
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void joinGameSuccessTest(Path path) {
        setUpGameTools(path);
        register();
        createGame();

        try {
            JoinGameRequest joinGameRequest = new JoinGameRequest(registerResult.getAuthToken(),
                    new JoinGameRequest.JoinGameHelperInfo("black", createGameResult.getGameID()));

            JoinGameResult joinGameResult = gameService.joinGame(joinGameRequest);

            Assertions.assertEquals(ChessGame.TeamColor.BLACK, joinGameResult.getPlayerColor());
            Assertions.assertEquals(createGameResult.getGameID(), joinGameResult.getGameID());
        } catch (Exception e) {
            System.out.println("Exception in gameServerTests: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void joinGameAlreadyTakenTest(Path path) {
        setUpGameTools(path);
        register();
        createGame();

        JoinGameRequest joinGameRequest = new JoinGameRequest(registerResult.getAuthToken(),
                new JoinGameRequest.JoinGameHelperInfo("black", createGameResult.getGameID()));

        try {
            JoinGameResult joinGameResult = gameService.joinGame(joinGameRequest);
        } catch (Exception e) {
            System.out.println("Exception in gameServerTests: " + e.getMessage());
        }

        Assertions.assertThrows(AlreadyTakenException.class, () -> gameService.joinGame(joinGameRequest));
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void clearSuccessTest(Path path) {
        setUpGameTools(path);
        register();
        createGame();

        try {
            gameService.clear();
            Assertions.assertEquals(new ArrayList<>(), listGamesResult.getGameList());
        } catch (Exception e) {
            System.out.println("Exception in gameServerTests: " + e.getMessage());
        }
    }

}
