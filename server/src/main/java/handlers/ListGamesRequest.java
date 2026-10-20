package handlers;

import server.AuthTokens;

public class ListGamesRequest {

    private final AuthTokens.AuthToken authToken;

    public ListGamesRequest(AuthTokens.AuthToken authToken) {
        this.authToken = authToken;
    }

    public AuthTokens.AuthToken getAuthToken() {
        return authToken;
    }

}
