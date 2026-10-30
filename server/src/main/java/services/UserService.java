package services;

import dataobject.*;
import dataaccess.AuthDataAccess;
import dataaccess.UserDataAccess;
import org.mindrot.jbcrypt.BCrypt;
import server.AuthTokenSet;

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
        String providedClearTextPassword = loginRequest.getPassword();

        UserData userData = userDataAccess.getUser(username);

        if (userData == null) {
            throw new UnauthorizedException();
        }

        String hashedPassword = userData.getPassword();
        if (!BCrypt.checkpw(providedClearTextPassword, hashedPassword)) {
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
