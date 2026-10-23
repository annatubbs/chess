package passoff.server;

import handlers.JoinGameRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoff.model.*;

import java.util.Arrays;


public class ClearTests extends ServerTests {
    private TestAuthResult registerResult;
    private TestCreateResult createGameResult;

    private void setUp() {
        TestUser testUser = new TestUser("Jeffrey", "chessLord", "jeffrey@fakeemail.com");
        registerResult = testServerFacade.register(testUser);
        createGameResult = testServerFacade.createGame(new TestCreateRequest("MyGame"), registerResult.getAuthToken());
    }

    @Test
    public void successTest() {
        setUp();

        testServerFacade.clear();

        System.out.println("Expected Status: 200");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());

        Assertions.assertEquals(200, testServerFacade.getStatusCode());

        TestListResult listGamesResult = testServerFacade.listGames(registerResult.getAuthToken());
        Assertions.assertEquals("[]", Arrays.toString(listGamesResult.getGames()));
    }
}
