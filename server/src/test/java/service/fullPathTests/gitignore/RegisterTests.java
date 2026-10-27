package service.fullPathTests.gitignore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoff.model.TestAuthResult;
import passoff.model.TestUser;

public class RegisterTests extends ServerTests {
    @Test
    public void successTest() {
        TestUser testUser = new TestUser("Jeffrey", "chessLord", "jeffrey@fakeemail.com");

        TestAuthResult registerResult = testServerFacade.register(testUser);

        System.out.println("Expected Status: 200");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());
        System.out.println("\nUsername: " + registerResult.getUsername());
        System.out.println("Auth token: " + registerResult.getAuthToken());

        Assertions.assertEquals(200, testServerFacade.getStatusCode());
        Assertions.assertNotNull(registerResult.getUsername());
        Assertions.assertNotNull(registerResult.getAuthToken());
    }

    @Test
    public void badRequestTest() {
        TestUser testUser = new TestUser("chessLord", "jeffrey@fakeemail.com");

        TestAuthResult registerResult = testServerFacade.register(testUser);

        System.out.println("Expected Status: 400");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());

        Assertions.assertEquals(400, testServerFacade.getStatusCode());
    }

    @Test
    public void alreadyTakenExceptionTest() {
        TestUser testUser = new TestUser("Jeffrey", "chessLord", "jeffrey@fakeemail.com");
        TestUser testUser2 = new TestUser("Jeffrey", "password", "jeffrey2@fakeemail.com");

        TestAuthResult registerResult = testServerFacade.register(testUser);
        TestAuthResult registerResult2 = testServerFacade.register(testUser2);

        System.out.println("Expected Status: 403");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());

        Assertions.assertEquals(403, testServerFacade.getStatusCode());
    }
}
