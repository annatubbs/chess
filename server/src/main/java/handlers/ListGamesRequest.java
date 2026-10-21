package handlers;

import server.AuthTokenSet.AuthToken;

/// //XXXXXXX

public class ListGamesRequest {

    private final AuthToken authToken;

    public ListGamesRequest(AuthToken authToken) {
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

}
