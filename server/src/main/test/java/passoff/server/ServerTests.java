package passoff.server;

import org.junit.Test;
import org.junit.jupiter.api.*;
import passoff.model.*;
import server.Server;
import server.ServerMain;

public class ServerTests {
    private static Server server;
    private static TestServerFacade testServerFacade;

    @BeforeAll
    static void startServer() {
        server = new Server();
        String port = "8080";
        testServerFacade = new TestServerFacade("localhost", port);
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
    void registerUserTest() {
        TestUser testUser = new TestUser("Jeffrey", "grandRidge", "jeffrey@fakeemail.com");

        TestAuthResult;

        Assertions.assertEquals(testServerFacade.getStatusCode(), 200);
    }
}
