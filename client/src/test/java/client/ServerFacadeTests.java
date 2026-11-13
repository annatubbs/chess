package client;

import chess.ChessGame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import server.Server;

import java.util.ArrayList;
import java.util.Map;


class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);

        serverFacade = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() throws Exception {
        serverFacade.clear();
        server.stop();
    }

    @AfterEach
    void clear() throws Exception {
        serverFacade.clear();
    }


    // register
    @Test
    void registerTest() throws Exception {
        Map<String, String> result = register();

        Assertions.assertNotNull(result.get("username"));
        Assertions.assertNotNull(result.get("authToken"));
    }

    // login
    @Test
    void loginTest() throws Exception {
        Map<String, String> result = login();

        Assertions.assertNotNull(result.get("username"));
        Assertions.assertNotNull(result.get("authToken"));
    }

    // logout
    @Test
    void logoutTest() throws Exception {
        Map<String, String> loginData = login();
        Map<String, String> result = serverFacade.logout(loginData.get("authToken"));

        Assertions.assertNotNull(result.get("username"));
    }

    // create
    @Test
    void createTest() throws Exception {
        Map<String, String> loginData = login();
        Map<String, String> result = create(loginData);

        Assertions.assertNotNull(result.get("gameID"));
    }

    // list
    @Test
    void listTest() throws Exception {
        Map<String, String> loginData = login();
        create(loginData);
        ArrayList<Map<String, Object>> result = serverFacade.list(loginData.get("authToken"));

        Assertions.assertNotNull(result);
    }

    // play
    @Test
    void playTest() throws Exception {
        Map<String, String> loginData = login();
        create(loginData);
        Map<String, Object> result = play(loginData.get("authToken"));

        Assertions.assertNotNull(result.get("playerColor"));
        Assertions.assertNotNull(result.get("gameID"));
    }

    // getGameBoard
    @Test
    void gameBoardTest() throws Exception {
        Map<String, String> loginData = login();
        create(loginData);
        String authToken = loginData.get("authToken");
        Map<String, Object> result = play(authToken);
        String board = serverFacade.getGameBoard(authToken, String.valueOf(result.get("gameID")),
                ChessGame.TeamColor.fromString(result.get("playerColor").toString()));

        Assertions.assertNotNull(board);
    }

    // Helper functions

    private Map<String, String> register() throws Exception {
        init();
        String[] params = {"Jeffrey", "password", "email@email.com"};
        Map<String, String> result = serverFacade.register(params);

        return result;
    }

    private Map<String, String> login() throws Exception {
        register();
        String[] params = {"Jeffrey", "password"};
        Map<String, String> result = serverFacade.login(params);

        return result;
    }

    private Map<String, String> create(Map<String, String> loginData) throws Exception {
        String[] params = {"myGame"};
        return serverFacade.create(loginData.get("authToken"), params);
    }

    private Map<String, Object> play(String authToken) throws Exception {
        String[] params = {"white", "1"};
        return serverFacade.play(authToken, params);
    }

}
