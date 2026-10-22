package passoff.server;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import passoff.model.*;
import server.Server;

public class ServerTests {
    private static Server server;
    private static TestServerFacade testServerFacade;

    @BeforeAll
    static void startServer() {
        server = new Server();
        int port = 8080;

        testServerFacade = new TestServerFacade("localhost", String.valueOf(port));
        server.run(8080);
    }

    @AfterAll
    static void endServer() {
        server.stop();
    }

    @BeforeEach
    void clearServerData() {
        testServerFacade.clear();
    }

    @Test
    public void registerUserTest() {
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
    public void registerUserTestBadRequest() {
        TestUser testUser = new TestUser("chessLord", "jeffrey@fakeemail.com");

        TestAuthResult registerResult = testServerFacade.register(testUser);

        System.out.println("Expected Status: 400");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());

        Assertions.assertEquals(400, testServerFacade.getStatusCode());
    }

    @Test
    public void registerUserTestAlreadyTaken() {
        TestUser testUser = new TestUser("Jeffrey", "chessLord", "jeffrey@fakeemail.com");
        TestUser testUser2 = new TestUser("Jeffrey", "password", "jeffrey2@fakeemail.com");

        TestAuthResult registerResult = testServerFacade.register(testUser);
        TestAuthResult registerResult2 = testServerFacade.register(testUser2);

        System.out.println("Expected Status: 403");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());

        Assertions.assertEquals(403, testServerFacade.getStatusCode());
    }
}
