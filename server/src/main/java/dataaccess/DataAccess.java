package dataaccess;

/*
    This phase you will create an implementation of your data access interface that stores your server’s data
in main memory (RAM) using standard data structures (maps, sets, lists). In the next phase you will
create an implementation of the data access interface that uses an external SQL database.
*/

public interface DataAccess {

    public AuthDataAccess getAuthDataAccess();

    public UserDataAccess getUserDataAccess();

    public GameDataAccess getGameDataAccess();

}
