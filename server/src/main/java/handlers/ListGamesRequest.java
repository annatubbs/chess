package handlers;

import server.AuthTokenSet.AuthToken;

public class ListGamesRequest {

    private final AuthToken authToken;

    public ListGamesRequest(AuthToken authToken) {
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

}
