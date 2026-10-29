package dataaccess;

import handlers.UserData;

import java.util.HashMap;
import java.util.Map;

public class UserDataAccess {

    private DataTransfer dataTransfer;

    public UserDataAccess(DataTransfer dataTransfer) {
        this.dataTransfer = dataTransfer;
        userDataMap = new HashMap<>();
    }

    public UserData getUser(String username) throws DataAccessException {
        try {
            if (dataTransfer.containsUsername(username)) {
                return dataTransfer.get
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

    public void clearData() throws DataAccessException {
        try {
            userDataMap = new HashMap<>();
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }
}
