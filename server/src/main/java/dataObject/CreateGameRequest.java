package dataObject;

import services.BadRequestException;


public class CreateGameRequest {

    private final String authToken;
    private final String gameName;

    public CreateGameRequest(String authToken, GameName gameNameObj) {
        try {
            this.authToken = authToken;
            this.gameName = gameNameObj.toString();
        } catch (Exception e) {
            throw new BadRequestException(e);
        }
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getGameName() {
        return gameName;
    }

    public static class GameName {
        private final String gameName;

        public GameName(String gameName) {
            this.gameName = gameName;
        }

        public String toString() {
            return gameName;
        }
    }
}
