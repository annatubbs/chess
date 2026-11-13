package dataaccess;

import requestandresultobjects.CreateGameResult;
import requestandresultobjects.ListGamesResult;
import handlers.GameHandler;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import services.*;

public class GameServerTests extends service.GameServerTests {
    static GameService gameService;
    static GameHandler gameHandler;
    CreateGameResult createGameResult;
    ListGamesResult listGamesResult;


    public void setUpGameTools(Path path) throws Exception {
        super.setUpGameTools(path);
    }

    public void createGame() {
        super.createGame();
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void listGamesSuccessTest(Path path) throws Exception {
        super.listGamesSuccessTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void listGameUnauthorizedTest(Path path) throws Exception {
        super.listGameUnauthorizedTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void createGameSuccessTest(Path path) throws Exception {
        super.createGameSuccessTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void createGameBadRequestTest(Path path) throws Exception {
        super.createGameBadRequestTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void joinGameSuccessTest(Path path) throws Exception {
        super.joinGameSuccessTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void joinGameAlreadyTakenTest(Path path) throws Exception {
        super.joinGameAlreadyTakenTest(path);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void clearSuccessTest(Path path) throws Exception {
        super.clearSuccessTest(path);
    }

}
