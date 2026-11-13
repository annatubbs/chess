package requestandresultobjects;

public class GetGameRequest {
    private String authToken;
    private final int gameID;

    public GetGameRequest(int gameID) {
        this.gameID = gameID;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public int getGameID() {
        return gameID;
    }

    public String getAuthToken() {
        return authToken;
    }
}
