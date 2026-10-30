package dataaccess.fullPathTests.gitignore;

import chess.ChessGame;
import dataObject.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoff.model.*;
import server.Server;


public class JoinGameTests extends ServerTests {
    private TestAuthResult registerResult;
    private TestCreateResult createGameResult;

    private void setUp() {
        TestUser testUser = new TestUser("Jeffrey", "chessLord", "jeffrey@fakeemail.com");
        registerResult = testServerFacade.register(testUser);
        createGameResult = testServerFacade.createGame(new TestCreateRequest(
                "MyGame"), registerResult.getAuthToken());
    }

    @Test
    public void successTest() {
        setUp();

        TestResult joinGameResult = testServerFacade.joinPlayer(new TestJoinRequest(
                "black", createGameResult.getGameID()), registerResult.getAuthToken());

        System.out.println("Expected Status: 200");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());

        Assertions.assertEquals(200, testServerFacade.getStatusCode());
    }

    @Test
    public void badRequestExceptionTest() {
        setUp();

        Server server = new Server();

        TestResult joinGameResult = testServerFacade.joinPlayer(new TestJoinRequest(
                "green", createGameResult.getGameID()), registerResult.getAuthToken());

        System.out.println("Expected Status: 400");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());

        Assertions.assertEquals(400, testServerFacade.getStatusCode());
    }

    @Test
    public void alreadyTakenExceptionTest() {
        setUp();

        Server server = new Server();

        TestResult joinGameResult = testServerFacade.joinPlayer(new TestJoinRequest(
                "black", createGameResult.getGameID()), registerResult.getAuthToken());

        // add another user who tries to take the same spot
        TestUser testUser2 = new TestUser(
                "Charlotte", "butterflyGirl", "charlotte@fakeemail.com");
        TestAuthResult registerResult2 = testServerFacade.register(testUser2);
        TestResult joinGameResult2 = testServerFacade.joinPlayer(new TestJoinRequest(
                "black", createGameResult.getGameID()), registerResult2.getAuthToken());

        System.out.println("Expected Status: 403");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());

        Assertions.assertEquals(403, testServerFacade.getStatusCode());
    }

    @Test
    public void colorTaken() {
        GameData data = new GameData(
                1234, "blackUser", null, "gameName");

        Assertions.assertFalse(data.isTaken(ChessGame.TeamColor.WHITE));
        Assertions.assertTrue(data.isTaken(ChessGame.TeamColor.BLACK));
    }
}
