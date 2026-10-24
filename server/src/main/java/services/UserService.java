package services;

import dataaccess.AuthMemDataAccess;
import dataaccess.UserMemDataAccess;
import handlers.*;
import server.AuthTokenSet;

public class UserService {
    private AuthTokenSet authTokensSet;
    private final AuthMemDataAccess authDataAccess;
    private final UserMemDataAccess userDataAccess;

    public UserService(AuthMemDataAccess authDataAccess, UserMemDataAccess userDataAccess) {
        authTokensSet = new AuthTokenSet();
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    private AuthData generateAuth(String username) {
        String authToken = authTokensSet.makeToken();
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
            throw new UnauthorizedException();
        }

        if (!userData.getPassword().equals(password)) {
            throw new UnauthorizedException();
        }

        AuthData authData = this.generateAuth(username);
        authDataAccess.createAuth(authData);

        return new LoginResult(authData);
    }

    public void logout(String authToken) throws Exception {
        AuthData authData = authDataAccess.getAuth(authToken);

        if (authData == null) {
            throw new UnauthorizedException();
        }

        authDataAccess.deleteAuth(authToken);
    }
}
