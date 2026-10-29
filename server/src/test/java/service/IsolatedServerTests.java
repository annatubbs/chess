package service;

import dataaccess.*;
import handlers.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import services.RegisterResult;
import services.UserService;

public abstract class IsolatedServerTests {
    static UserService userService;
    static DataTransfer dataTransfer;
    static AuthDataAccess authDataAccess;
    static UserDataAccess userDataAccess;
    static GameDataAccess gameDataAccess;

    RegisterResult registerResult;

    @BeforeEach
    protected void setUp() {
        userService = new UserService(authDataAccess, userDataAccess);
        dataTransfer = new MemoryDataTransfer();
        authDataAccess = new AuthDataAccess(dataTransfer);
        userDataAccess = new UserDataAccess(dataTransfer);
        gameDataAccess = new GameDataAccess(dataTransfer);
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
