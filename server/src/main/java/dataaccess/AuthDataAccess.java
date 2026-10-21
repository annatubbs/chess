package dataaccess;

import handlers.AuthData;
import server.AuthTokenSet;

public interface AuthDataAccess {

    AuthData getAuth(AuthTokenSet.AuthToken authToken) throws DataAccessException;

    void createAuth(AuthData authData) throws DataAccessException;

    void deleteAuth(AuthTokenSet.AuthToken authToken) throws DataAccessException;
}
