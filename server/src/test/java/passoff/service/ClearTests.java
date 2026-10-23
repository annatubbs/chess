package passoff.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoff.model.*;

import java.util.Arrays;


public class ClearTests extends ServerTests {
    private TestAuthResult registerResult;

    private void setUp() {
        TestUser testUser = new TestUser("Jeffrey", "chessLord", "jeffrey@fakeemail.com");
        registerResult = testServerFacade.register(testUser);
        TestCreateResult createGameResult = testServerFacade.createGame(new TestCreateRequest("MyGame"), registerResult.getAuthToken());
    }

    @Test
    public void successTest() {
        setUp();

        testServerFacade.clear();

        System.out.println("Expected Status: 200");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());

        Assertions.assertEquals(200, testServerFacade.getStatusCode());

        TestListResult listGamesResult = testServerFacade.listGames(registerResult.getAuthToken());
        System.out.println("Games: " + Arrays.toString(listGamesResult.getGames()));
    }
}
