package services;

import dataaccess.DataAccess;
import handlers.*;
import server.AuthTokenSet;

public class UserService {
    private final DataAccess dataAccess;
    private AuthTokenSet authTokensSet;

    public UserService(DataAccess dataAccess, AuthTokenSet authTokensSet) {
        this.dataAccess = dataAccess;
        authTokensSet = new AuthTokenSet();
    }

    private AuthTokenSet.AuthToken authorize(String username) {
        AuthTokenSet.AuthToken authToken = authTokensSet.makeToken();
        AuthData authData = new AuthData(username, authToken);

        dataAccess.createAuth(authData);
        return authToken;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws Exception {
        String username = registerRequest.getUsername();

        if (dataAccess.getUser(username) != null) {
            throw new AlreadyTakenException("Username " + username + " already taken.");
        }

        UserData userData = new UserData(registerRequest);
        dataAccess.createUser(userData);

        AuthTokenSet.AuthToken authToken = authorize(username); // throws FailureResponseException
        return new RegisterResult(authToken);
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

        AuthTokenSet.AuthToken authToken = this.authorize(username); // throws FailureResponseException
        return new LoginResult(authToken);
    }

    public LogoutResult logout(AuthTokenSet.AuthToken authToken) throws Exception {
//        AuthTokenSet.AuthToken authToken = logoutRequest.getAuthToken();

        AuthData authData = dataAccess.getAuth(authToken);
        if (authData == null) {
            throw new UnauthorizedException("Not authorized to logout.");
        }

        String username = authData.getUsername();
        dataAccess.deleteAuth(authToken);

        return new LogoutResult(username);
    }
}
