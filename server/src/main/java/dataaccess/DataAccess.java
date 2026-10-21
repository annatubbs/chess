package dataaccess;

public class DataAccess {
    private final AuthDataAccess authDataAccess;
    private final UserDataAccess userDataAccess;
    private final GameDataAccess gameDataAccess;

    public dataAccess() {
        this.authDataAccess = new AuthDataAccess();
        this.userDataAccess = new UserDataAccess();
        this.gameDataAccess = new GameDataAccess();
    }
}
