package dataaccess;

public class MemoryDataAccess implements DataAccess {

    private final AuthDataAccess authDataAccess;
    private final UserDataAccess userDataAccess;
    private final GameDataAccess gameDataAccess;

    public MemoryDataAccess() {
        authDataAccess = new AuthDataAccess();
        userDataAccess = new UserDataAccess();
        gameDataAccess = new GameDataAccess(authDataAccess, userDataAccess);
    }

    public AuthDataAccess getAuthDataAccess() {
        return authDataAccess;
    }

    public UserDataAccess getUserDataAccess() {
        return userDataAccess;
    }

    public GameDataAccess getGameDataAccess() {
        return gameDataAccess;
    }

}
