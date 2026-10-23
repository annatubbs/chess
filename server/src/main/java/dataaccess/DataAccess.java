package dataaccess;

public interface DataAccess {

    public AuthDataAccess getAuthDataAccess();

    public UserDataAccess getUserDataAccess();

    public GameDataAccess getGameDataAccess();

}
