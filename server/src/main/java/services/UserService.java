package services;

import dataaccess.DataAccess;
import dataaccess.GameDataAccess;
import handlers.*;
import server.AuthTokenSet;
import server.AuthTokenSet.AuthToken;

public class UserService {
    private AuthTokenSet authTokensSet;
    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess) {
        authTokensSet = new AuthTokenSet();
        this.dataAccess = dataAccess;
    }

    private AuthData generateAuth(String username) {
        AuthToken authToken = authTokensSet.makeToken();
        return new AuthData(username, authToken);
    }

    public RegisterResult register(RegisterRequest registerRequest) throws Exception {
        String username = registerRequest.getUsername();
        if (dataAccess.getUser(username) != null) {
            throw new AlreadyTakenException("Username " + username + " already taken.");
        }

        UserData userData = new UserData(registerRequest);
        dataAccess.createUser(userData);

        AuthData authData = generateAuth(username);
        dataAccess.createAuth(authData);

        return new RegisterResult(authData);
    }

    public LoginResult login(LoginRequest loginRequest) throws Exception {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        UserData userData = dataAccess.getUser(username);
        if (userData == null) {
            throw new BadRequestException("User not found, username: " + username + ", password: " + password + ".");
        }

        if (userData.getPassword().equals(password)) {
            throw new UnauthorizedException("Password: " + password + " incorrect for " + username + ".");
        }

        AuthData authData = this.generateAuth(username); // throws FailureResponseException
        return new LoginResult(authData);
    }

    public LogoutResult logout(AuthToken authToken) throws Exception {
//        AuthToken authToken = logoutRequest.getAuthToken();

        AuthData authData = dataAccess.getAuth(authToken);
        if (authData == null) {
            throw new UnauthorizedException("Not authorized to logout.");
        }

        String username = authData.getUsername();
        dataAccess.deleteAuth(authToken);

        return new LogoutResult(username);
    }
}
