package dataaccess;

public class MemoryDataAccess implements DataAccess {

    private final AuthMemDataAccess authDataAccess;
    private final UserMemDataAccess userDataAccess;
    private final GameMemDataAccess gameDataAccess;

    public MemoryDataAccess() {
        authDataAccess = new AuthMemDataAccess();
        userDataAccess = new UserMemDataAccess();
        gameDataAccess = new GameMemDataAccess();
    }

    public AuthMemDataAccess getAuthDataAccess() {
        return authDataAccess;
    }

    public UserMemDataAccess getUserDataAccess() {
        return userDataAccess;
    }

    public GameMemDataAccess getGameDataAccess() {
        return gameDataAccess;
    }

}
