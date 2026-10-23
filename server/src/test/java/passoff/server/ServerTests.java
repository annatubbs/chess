package passoff.server;

import org.junit.jupiter.api.*;
import passoff.model.*;
import server.Server;

public abstract class ServerTests {
    protected static Server server;
    protected static TestServerFacade testServerFacade;

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

}
