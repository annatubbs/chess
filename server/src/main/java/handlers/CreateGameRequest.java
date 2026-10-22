package handlers;

import server.AuthTokenSet.AuthToken;
import services.BadRequestException;


public class CreateGameRequest {

    private final AuthToken authToken;
    private final String gameName;

    public CreateGameRequest(AuthToken authToken, String gameName) {
        try {
            this.authToken = authToken;
            this.gameName = gameName;
        } catch (Exception e) {
            throw new BadRequestException(e);
        }
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public String getGameName() {
        return gameName;
    }
}
