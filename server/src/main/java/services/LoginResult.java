package services;

import server.AuthTokens.AuthToken;

// ****same as RegisterResult

public class LoginResult {
    private final String username;
    private final AuthToken authToken;

    public LoginResult(String username, AuthToken authToken) {
        this.username = username;
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
}
