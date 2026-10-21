package dataaccess;

import handlers.UserData;

public interface UserDataAccess {

    UserData getUser(String username) throws DataAccessException;

    void createUser(UserData userData) throws DataAccessException;
}
