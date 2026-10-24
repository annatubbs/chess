package service;

import dataaccess.AuthMemDataAccess;
import dataaccess.GameMemDataAccess;
import dataaccess.UserMemDataAccess;
import handlers.GameHandler;
import handlers.RegisterRequest;
import handlers.UserHandler;
import org.junit.jupiter.api.BeforeEach;
import services.GameService;
import services.RegisterResult;
import services.UserService;

public abstract class IsolatedServerTests {
    static UserService userService;

    static AuthMemDataAccess authMemDataAccess;
    static UserMemDataAccess userMemDataAccess;
    static GameMemDataAccess gameMemDataAccess;

    RegisterResult registerResult;

    @BeforeEach
    protected void setUp() {
        userService = new UserService(authMemDataAccess, userMemDataAccess);

        authMemDataAccess = new AuthMemDataAccess();
        userMemDataAccess = new UserMemDataAccess();
        gameMemDataAccess = new GameMemDataAccess(authMemDataAccess, userMemDataAccess);
    }

    protected void register() {
        try {
            registerResult = userService.register(new RegisterRequest(
                    "Jeffrey", "chessLord", "jeffrey@fakeemail.com"));
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }
}
