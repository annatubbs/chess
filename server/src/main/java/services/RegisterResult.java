package services;

import handlers.AuthData;
import server.AuthTokenSet.AuthToken;

public class RegisterResult {
    private final String username;
    private final AuthToken authToken;
//
//    public RegisterResult(String username, AuthToken authToken) {
//        this.username = username;
//        this.authToken = authToken;
//    }t

    public RegisterResult(AuthData authData) {
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
