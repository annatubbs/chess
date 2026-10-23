package dataaccess;

import handlers.AuthData;

public interface AuthDataAccess {

    AuthData getAuth(String authToken) throws DataAccessException;

    void createAuth(AuthData authData) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;
}
