package dataaccess;

import handlers.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserMemDataAccess implements UserDataAccess {

    private HashMap<String, ArrayList<String>> userDataMap; // Username: password, email

    public UserMemDataAccess() {
        userDataMap = new HashMap<>();
    }

    public UserData getUser(String username) throws DataAccessException {
        try {
            if (userDataMap.containsKey(username)) {
                ArrayList<String> passwordEmailLst = userDataMap.get(username);
                return new UserData(username, passwordEmailLst.get(0), passwordEmailLst.get(1));
            }
            return null;
        } catch (Exception e) {
            throw new DataAccessException("Error in user data access: " + e.getMessage());
        }
    }

    public void createUser(UserData userData) throws DataAccessException {
        try {
            String username = userData.getUsername();

            if (userDataMap.containsKey(username)) {
                throw new DataAccessException("Username already taken.");
            } else {
                userDataMap.put(username, new ArrayList<>(List.of(userData.getPassword(), userData.getEmail())));
            }
        } catch (Exception e) {
            throw new DataAccessException("Error in user data access: " + e.getMessage());
        }
    }
}
