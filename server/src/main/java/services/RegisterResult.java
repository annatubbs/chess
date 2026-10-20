package services;

import server.AuthTokens.AuthToken;

public class RegisterResult {
    private final String username;
    private final AuthToken authToken;

    public RegisterResult(String username, AuthToken authToken) {
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
