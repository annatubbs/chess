package service;

import handlers.LoginRequest;
import handlers.RegisterRequest;
import handlers.UserHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.*;

public class UserServerTests extends IsolatedServerTests {
    static UserHandler userHandler;

    @BeforeEach
    public void setUpUserTools() {
        userHandler = new UserHandler(userService);
    }

    @Test
    public void registerSuccessTest() {
        setUp();
        register();

        Assertions.assertEquals("Jeffrey", registerResult.getUsername());
        Assertions.assertNotNull(registerResult.getAuthToken());
    }

    @Test
    public void registerBadRequestTest() {
        setUp();

        Assertions.assertThrows(BadRequestException.class, () -> userService.register(new RegisterRequest(
                "Jeffrey", "chessLord", null)));
    }

    @Test
    public void loginSuccessTest() {
        try {
            setUp();
            register();

            LoginResult loginResult = userService.login(new LoginRequest(
                    "Jeffrey", "chessLord"));

            Assertions.assertEquals("Jeffrey", loginResult.getUsername());
            Assertions.assertNotNull(loginResult.getAuthToken());
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    @Test
    public void loginUnauthorizedTest() {
        setUp();
        register();

        Assertions.assertThrows(UnauthorizedException.class, () -> userService.login(new LoginRequest(
                "Jeffrey", "BAD_PASSWORD")));
    }

    @Test
    public void logoutSuccessTest() {
        setUp();
        register();

        try {
            Assertions.assertDoesNotThrow(() ->
                    userService.logout(registerResult.getAuthToken()));
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    @Test
    public void logoutUnauthorizedTest() {
        setUp();
        register();

        Assertions.assertThrows(UnauthorizedException.class, () -> userService.logout("12345"));
    }

}
