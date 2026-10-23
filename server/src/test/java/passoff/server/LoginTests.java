package passoff.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoff.model.TestAuthResult;
import passoff.model.TestUser;

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

    @Test
    public void badRequestExceptionTest() {
        setUp();

        TestUser testUser2 = new TestUser("Nonexixtent", "chessLord", "jeffrey@fakeemail.com");
        TestAuthResult loginResult = testServerFacade.login(testUser2);

        System.out.println("Expected Status: 400");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());

        Assertions.assertEquals(400, testServerFacade.getStatusCode());
    }

    @Test
    public void unauthorizedExceptionTest() {
        setUp();

        TestUser testUser2 = new TestUser("Jeffrey", "wrongPassword", "jeffrey@fakeemail.com");
        TestAuthResult loginResult = testServerFacade.login(testUser2);

        System.out.println("Expected Status: 401");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());

        Assertions.assertEquals(401, testServerFacade.getStatusCode());
    }
}
