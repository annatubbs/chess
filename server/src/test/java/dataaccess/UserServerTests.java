package dataaccess;

import dataObject.LoginResult;
import dataObject.LoginRequest;
import dataObject.RegisterRequest;
import handlers.UserHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import services.*;

public class UserServerTests extends IsolatedServerTests {
    static UserHandler userHandler;

    @BeforeEach
    public void setUpUserTools() {
        userHandler = new UserHandler(userService);
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void registerSuccessTest(Path path) {
        setUp(path);
        register();

        Assertions.assertEquals("Jeffrey", registerResult.getUsername());
        Assertions.assertNotNull(registerResult.getAuthToken());
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void registerBadRequestTest(Path path) {
        setUp(path);

        Assertions.assertThrows(BadRequestException.class, () -> userService.register(new RegisterRequest(
                "Jeffrey", "chessLord", null)));
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void loginSuccessTest(Path path) {
        try {
            setUp(path);
            register();

            LoginResult loginResult = userService.login(new LoginRequest(
                    "Jeffrey", "chessLord"));

            Assertions.assertEquals("Jeffrey", loginResult.getUsername());
            Assertions.assertNotNull(loginResult.getAuthToken());
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void loginUnauthorizedTest(Path path) {
        setUp(path);
        register();

        Assertions.assertThrows(UnauthorizedException.class, () -> userService.login(new LoginRequest(
                "Jeffrey", "BAD_PASSWORD")));
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void logoutSuccessTest(Path path) {
        setUp(path);
        register();

        try {
            Assertions.assertDoesNotThrow(() ->
                    userService.logout(registerResult.getAuthToken()));
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    @ParameterizedTest
    @EnumSource(Path.class)
    public void logoutUnauthorizedTest(Path path) {
        setUp(path);
        register();

        Assertions.assertThrows(UnauthorizedException.class, () -> userService.logout("12345"));
    }
}
