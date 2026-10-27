package service;

import dataaccess.*;
import handlers.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import services.RegisterResult;
import services.UserService;

public abstract class IsolatedServerTests {
    static UserService userService;

    static AuthDataAccess authDataAccess;
    static UserDataAccess userDataAccess;
    static GameDataAccess gameDataAccess;

    RegisterResult registerResult;

    @BeforeEach
    protected void setUp() {
        userService = new UserService(authDataAccess, userDataAccess);

        authDataAccess = new AuthDataAccess();
        userDataAccess = new UserDataAccess();
        gameDataAccess = new GameDataAccess(authDataAccess, userDataAccess);
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
