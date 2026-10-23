package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoff.model.TestAuthResult;
import passoff.model.TestUser;

public class LogoutTests extends ServerTests {
    TestAuthResult registerResult;

    private void setUp() {
        TestUser testUser = new TestUser("Jeffrey", "chessLord", "jeffrey@fakeemail.com");

        registerResult = testServerFacade.register(testUser);
    }

    @Test
    public void successTest() {
        setUp();

        testServerFacade.logout(registerResult.getAuthToken());

        System.out.println("Expected Status: 200");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());

        Assertions.assertEquals(200, testServerFacade.getStatusCode());
        Assertions.assertNotNull(registerResult.getUsername());
        Assertions.assertNotNull(registerResult.getAuthToken());
    }

    @Test
    public void unauthorizedExceptionTest() {
        setUp();

        testServerFacade.logout("1234"); // fake authtoken

        System.out.println("Expected Status: 401");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());

        Assertions.assertEquals(401, testServerFacade.getStatusCode());
    }
}
