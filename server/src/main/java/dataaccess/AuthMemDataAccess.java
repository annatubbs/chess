package dataaccess;

import handlers.AuthData;
import server.AuthTokenSet;

import java.util.HashMap;
import java.util.Map;

// *Cast authToken to string?? I think so for tests..

public class AuthMemDataAccess implements AuthDataAccess {

    HashMap<String, HashMap<String, String>> authDataMap; // authToken: username

    public AuthMemDataAccess() {
        authDataMap = new HashMap<>();
    }

    public AuthData getAuth(AuthTokenSet.AuthToken authToken) throws DataAccessException {
        try {
            String authTokenStr = authToken.toString();

            if (authDataMap.containsKey(authTokenStr)) {
                HashMap<String, String> usernameMap = authDataMap.get(authTokenStr);
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
            String authTokenStr = authData.getAuthToken().toString();

            if (authDataMap.containsKey(authTokenStr)) {
                throw new DataAccessException("AuthToken already in use.");
            } else {
                authDataMap.put(authTokenStr, new HashMap<>(Map.of("username", username)));
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }

    public void deleteAuth(AuthTokenSet.AuthToken authToken) throws DataAccessException {
        try {
            authDataMap.remove(authToken.toString());
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }
}
