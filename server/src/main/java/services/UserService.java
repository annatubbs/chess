package services;

import dataaccess.AuthDataAccess;
import dataaccess.UserDataAccess;
import handlers.*;
import server.AuthTokenSet;
import server.AuthTokenSet.AuthToken;

public class UserService {
    private AuthTokenSet authTokensSet;
    private final AuthDataAccess authDataAccess;
    private final UserDataAccess userDataAccess;

    public UserService(AuthDataAccess authDataAccess, UserDataAccess userDataAccess) {
        authTokensSet = new AuthTokenSet();
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    private AuthData generateAuth(String username) {
        AuthToken authToken = authTokensSet.makeToken();
        return new AuthData(username, authToken);
    }

    public RegisterResult register(RegisterRequest registerRequest) throws Exception {
        String username = registerRequest.getUsername();
        if (userDataAccess.getUser(username) != null) {
            throw new AlreadyTakenException();
        }

        try {
            UserData userData = new UserData(registerRequest);
            userDataAccess.createUser(userData);
        } catch (Exception e) {
            throw new BadRequestException(e);
        }

        AuthData authData = generateAuth(username);
        authDataAccess.createAuth(authData);

        return new RegisterResult(authData);
    }

    public LoginResult login(LoginRequest loginRequest) throws Exception {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        UserData userData = userDataAccess.getUser(username);
        if (userData == null) {
            throw new BadRequestException();
        }

        if (userData.getPassword().equals(password)) {
            throw new UnauthorizedException();
        }

        AuthData authData = this.generateAuth(username); // throws FailureResponseException
        return new LoginResult(authData);
    }

    public LogoutResult logout(AuthToken authToken) throws Exception {
//        AuthToken authToken = logoutRequest.getAuthToken();

        AuthData authData = authDataAccess.getAuth(authToken);
        if (authData == null) {
            throw new UnauthorizedException();
        }

        String username = authData.getUsername();
        authDataAccess.deleteAuth(authToken);

        return new LogoutResult(username);
    }
}
