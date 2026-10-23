package dataaccess;

import handlers.AuthData;

import java.util.HashMap;
import java.util.Map;


public class AuthMemDataAccess implements AuthDataAccess {

    HashMap<String, HashMap<String, String>> authDataMap; // authToken: username

    public AuthMemDataAccess() {
        authDataMap = new HashMap<>();
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        try {
            System.out.println(authDataMap);

            if (authDataMap.containsKey(authToken)) {
                HashMap<String, String> usernameMap = authDataMap.get(authToken);
                return new AuthData(usernameMap.get("username"), authToken);
            }
            return null;
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }

    public void createAuth(AuthData authData) throws DataAccessException {
        try {
            String username = authData.getUsername();
            String authTokenStr = authData.getAuthToken();

            if (authDataMap.containsKey(authTokenStr)) {
                throw new DataAccessException("AuthToken already in use.");
            } else {
                authDataMap.put(authTokenStr, new HashMap<>(Map.of("username", username)));
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
