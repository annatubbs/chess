package dataaccess;

public class DataAccess {

    private final AuthDataAccess authDataAccess;
    private final UserDataAccess userDataAccess;
    private final GameDataAccess gameDataAccess;

    public DataAccess(DataTransfer dataTransfer) {
        authDataAccess = new AuthDataAccess(dataTransfer);
        userDataAccess = new UserDataAccess(dataTransfer);
        gameDataAccess = new GameDataAccess(dataTransfer);
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
