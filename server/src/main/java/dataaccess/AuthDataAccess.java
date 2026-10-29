package dataaccess;

import dataObject.AuthData;


public class AuthDataAccess {

    private DataTransfer dataTransfer;

    public AuthDataAccess(DataTransfer dataTransfer) {
        this.dataTransfer = dataTransfer;
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        try {
            if (dataTransfer.containsAuth(authToken)) {
                return dataTransfer.getAuth(authToken);
            }
            return null; // null if auth doesn't exist, bad request
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void createAuth(AuthData authData) throws DataAccessException {
        try {
            if (dataTransfer.containsAuth(authData.getAuthToken())) {
                throw new DataAccessException("AuthToken already in use.");
            } else {
                dataTransfer.addAuth(authData);
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        try {
            if (!dataTransfer.containsAuth(authToken)) {
                throw new DataAccessException("Error: authToken not in database");
            }
            dataTransfer.removeAuth(authToken);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
