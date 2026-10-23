package passoff.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoff.model.TestAuthResult;
import passoff.model.TestListResult;
import passoff.model.TestUser;

import java.util.Arrays;

public class ListGamesTests extends ServerTests {
    private TestAuthResult registerResult;

    private void setUp() {
        TestUser testUser = new TestUser("Jeffrey", "chessLord", "jeffrey@fakeemail.com");

        registerResult = testServerFacade.register(testUser);
    }

    @Test
    public void successTest() {
        setUp();

        TestListResult listGamesResult = testServerFacade.listGames(registerResult.getAuthToken());

        System.out.println("Expected Status: 200");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());
        System.out.println(Arrays.toString(listGamesResult.getGames()));

        Assertions.assertEquals(200, testServerFacade.getStatusCode());
        Assertions.assertNotNull(listGamesResult.getGames());
    }

    @Test
    public void UnauthorizedExceptionTest() {
        setUp();

        TestListResult listGamesResult = testServerFacade.listGames("1234");

        System.out.println("Expected Status: 401");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());

        Assertions.assertEquals(401, testServerFacade.getStatusCode());
    }
}
