package handlers;

import server.AuthTokens.AuthToken; //*string AuthToken?

public class AuthData {
    private final String username;
    private final AuthToken authToken;

    public AuthData(String username, AuthToken authToken) {
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
