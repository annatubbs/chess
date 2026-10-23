package passoff.server;

import com.google.gson.Gson;
import handlers.CreateGameRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoff.model.TestAuthResult;
import passoff.model.TestCreateRequest;
import passoff.model.TestCreateResult;
import passoff.model.TestUser;

import java.util.Arrays;

public class CreateGameTest extends ServerTests {
    private TestAuthResult registerResult;

    private void setUp() {
        TestUser testUser = new TestUser("Jeffrey", "chessLord", "jeffrey@fakeemail.com");

        registerResult = testServerFacade.register(testUser);
    }

    @Test
    public void successTest() {
        setUp();

//        CreateGameRequest cgr = new CreateGameRequest(registerResult.getAuthToken(), new CreateGameRequest.GameName("myGame"));
        TestCreateResult createGameResult = testServerFacade.createGame(new TestCreateRequest("MyGame"), registerResult.getAuthToken());

        System.out.println("Expected Status: 200");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());
        System.out.println(createGameResult.getGameID());

        Assertions.assertEquals(200, testServerFacade.getStatusCode());
        Assertions.assertNotNull(createGameResult.getGameID());
    }

    @Test
    public void BadRequestExceptionTest() {
        setUp();

//        CreateGameRequest cgr = new CreateGameRequest(registerResult.getAuthToken(), new CreateGameRequest.GameName("myGame"));
        TestCreateResult createGameResult = testServerFacade.createGame(null, registerResult.getAuthToken());

        System.out.println("Expected Status: 400");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());

        Assertions.assertEquals(400, testServerFacade.getStatusCode());
    }

    @Test
    public void UnauthorizedExceptionTest() {
        setUp();

//        CreateGameRequest cgr = new CreateGameRequest(registerResult.getAuthToken(), new CreateGameRequest.GameName("myGame"));
        TestCreateResult createGameResult = testServerFacade.createGame(new TestCreateRequest("MyGame"), "1234");

        System.out.println("Expected Status: 401");
        System.out.println("Actual Status: " + testServerFacade.getStatusCode());

        Assertions.assertEquals(401, testServerFacade.getStatusCode());
    }
}
