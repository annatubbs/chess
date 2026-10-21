package handlers;

import server.AuthTokenSet.AuthToken;


public class CreateGameRequest {

    private final AuthToken authToken;
    private final String gameName;

    public CreateGameRequest(AuthToken authToken, String gameName) {
        this.authToken = authToken;
        this.gameName = gameName;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public String getGameName() {
        return gameName;
    }
}
