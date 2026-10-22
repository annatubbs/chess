package passoff.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoff.model.TestAuthResult;
import passoff.model.TestUser;
import server.Server;

public class LoginTests extends ServerTests {
    private TestUser testUser;

    private void setUp() {
        testUser = new TestUser("Jeffrey", "chessLord", "jeffrey@fakeemail.com");

        TestAuthResult registerResult = testServerFacade.register(testUser);
        testServerFacade.logout(registerResult.getAuthToken());
    }


    @Test
    public void successTest() {
        setUp();

        TestAuthResult loginResult = testServerFacade.login(testUser);

        System.out.println("Expected Status: 200");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());
        System.out.println("\nUsername: " + loginResult.getUsername());
        System.out.println("Auth token: " + loginResult.getAuthToken());

        Assertions.assertEquals(200, testServerFacade.getStatusCode());
        Assertions.assertNotNull(loginResult.getUsername());
        Assertions.assertNotNull(loginResult.getAuthToken());
    }
}
