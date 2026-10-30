package dataaccess;

import dataobject.UserData;

public class UserDataAccess {

    private DataTransfer dataTransfer;

    public UserDataAccess(DataTransfer dataTransfer) {
        this.dataTransfer = dataTransfer;
    }

    public UserData getUser(String username) throws DataAccessException {
        try {
            if (dataTransfer.containsUsername(username)) {
                return dataTransfer.getUser(username);
            }
            return null;
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }

    public void createUser(UserData userData) throws DataAccessException {
        try {
            if (dataTransfer.containsUsername(userData.getUsername())) {
                throw new DataAccessException("Error: Username already taken.");
            } else {
                dataTransfer.addUser(userData);
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }
}
