package services;

import handlers.AuthData;

public class LoginResult {
    private final String username;
    private final String authToken;

    public LoginResult(AuthData authData) {
        this.username = authData.getUsername();
        this.authToken = authData.getAuthToken();
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }
}
