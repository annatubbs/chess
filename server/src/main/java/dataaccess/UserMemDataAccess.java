package dataaccess;

import handlers.UserData;

import java.util.HashMap;
import java.util.Map;

public class UserMemDataAccess implements UserDataAccess {

    private HashMap<String, HashMap<String, String>> userDataMap; // Username: password, email

    public UserMemDataAccess() {
        userDataMap = new HashMap<>();
    }

    public UserData getUser(String username) throws DataAccessException {
        try {
            if (userDataMap.containsKey(username)) {
                HashMap<String, String> passwordEmailLst = userDataMap.get(username);
                return new UserData(username, passwordEmailLst.get("password"), passwordEmailLst.get("email"));
            }
            return null;
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }

    public void createUser(UserData userData) throws DataAccessException {
        try {
            String username = userData.getUsername();

            if (userDataMap.containsKey(username)) {
                throw new DataAccessException("Error: Username already taken.");
            } else {
                userDataMap.put(username,
                        new HashMap<>(Map.of("password", userData.getPassword(), "email", userData.getEmail())));
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }
}
