package service.fullPathTests.gitignore;

import org.junit.jupiter.api.*;
import passoff.server.TestServerFacade;
import server.Server;

public abstract class ServerTests {
    protected static Server server;
    protected static TestServerFacade testServerFacade;

    @BeforeAll
    static void startServer() {
        server = new Server();

        testServerFacade = new TestServerFacade("localhost", "8080");
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
