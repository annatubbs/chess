package services;

import handlers.AuthData;
import server.AuthTokenSet.AuthToken;

// ****same as RegisterResult

public class LoginResult {
    private final String username;
    private final AuthToken authToken;

    public LoginResult(AuthData authData) {
        this.username = authData.getUsername();
        this.authToken = authData.getAuthToken();
    }

    public String getUsername() {
        return username;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
}
