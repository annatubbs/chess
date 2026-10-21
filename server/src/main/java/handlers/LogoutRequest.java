package handlers;

import server.AuthTokenSet.AuthToken;

/// ///XXXXXXXXX

public class LogoutRequest {

    private final AuthToken authToken;

    public LogoutRequest(AuthToken authToken) {
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

}

