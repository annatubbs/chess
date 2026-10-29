package dataaccess;

import handlers.AuthData;

import java.util.HashMap;


public class AuthDataAccess {

    private DataTransfer dataTransfer;
    HashMap<String, AuthData> authDataMap; // authToken: username

    public AuthDataAccess(DataTransfer dataTransfer) {
        this.dataTransfer = dataTransfer;
        authDataMap = new HashMap<>();
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        try {
            if (dataTransfer.containsAuth(authToken)) {
                return authDataMap.get(authToken);
            }
            return null;
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }

    public void createAuth(AuthData authData) throws DataAccessException {
        try {
            String authTokenStr = authData.getAuthToken();

            if (authDataMap.containsKey(authTokenStr)) {
                throw new DataAccessException("AuthToken already in use.");
            } else {
                authDataMap.put(authTokenStr, authData);
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        try {
            authDataMap.remove(authToken);
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }

    public void clearData() throws DataAccessException {
        try {
            authDataMap = new HashMap<>();
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }
}
