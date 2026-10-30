package service;

import dataaccess.sql.SQLDataTransfer;
import dataobject.RegisterRequest;
import dataobject.RegisterResult;
import dataaccess.*;
import services.UserService;

public abstract class IsolatedServerTests {
    public static UserService userService;
    static DataTransfer dataTransfer;
    public static AuthDataAccess authDataAccess;
    static UserDataAccess userDataAccess;
    public static GameDataAccess gameDataAccess;

    public RegisterResult registerResult;

    public enum Path {
        MEMORY,
        SQL
    }

    //    @BeforeEach
    protected void setUp(Path path) {
        clearData();
        if (path == Path.MEMORY) {
            dataTransfer = new MemoryDataTransfer();
        } else { // path = Path.SQL
            dataTransfer = new SQLDataTransfer();
        }
        authDataAccess = new AuthDataAccess(dataTransfer);
        userDataAccess = new UserDataAccess(dataTransfer);
        gameDataAccess = new GameDataAccess(dataTransfer);
        userService = new UserService(authDataAccess, userDataAccess);
    }

    protected void clearData() {
        try {
            gameDataAccess.clearData();
        } catch (Exception e) {
            System.out.println("Exception in tests: " + e.getMessage());
        }
    }

    protected void register() {
        try {
            registerResult = userService.register(new RegisterRequest(
                    "Jeffrey", "chessLord", "jeffrey@fakeemail.com"));
        } catch (Exception e) {
            System.out.println("Exception in tests: " + e.getMessage());
        }
    }
}
