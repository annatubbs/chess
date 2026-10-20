package handlers;

import server.AuthTokens.AuthToken;

public class LogoutRequest {

    private final AuthToken authToken;

    public LogoutRequest(AuthToken authToken) {
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

}

