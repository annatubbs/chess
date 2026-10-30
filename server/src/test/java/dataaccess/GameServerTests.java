package dataaccess;

import chess.ChessGame;
import dataobject.CreateGameResult;
import dataobject.JoinGameResult;
import dataobject.ListGamesResult;
import dataobject.CreateGameRequest;
import handlers.GameHandler;
import dataobject.JoinGameRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import services.*;

import java.util.ArrayList;

public class GameServerTests extends service.GameServerTests {
    static GameService gameService;
    static GameHandler gameHandler;
    CreateGameResult createGameResult;
    ListGamesResult listGamesResult;


    public void setUpGameTools(Path path) {
        super.setUpGameTools(path);
    }

    public void createGame() {
        super.createGame();
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void listGamesSuccessTest(Path path) {
        super.listGamesSuccessTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void listGameUnauthorizedTest(Path path) {
        super.listGameUnauthorizedTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void createGameSuccessTest(Path path) {
        super.createGameSuccessTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void createGameBadRequestTest(Path path) {
        super.createGameBadRequestTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void joinGameSuccessTest(Path path) {
        super.joinGameSuccessTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void joinGameAlreadyTakenTest(Path path) {
        super.joinGameAlreadyTakenTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void clearSuccessTest(Path path) {
        super.clearSuccessTest(path);
    }

}
