package handlers;

import server.AuthTokenSet.AuthToken;
import chess.ChessGame.TeamColor;


public class JoinGameRequest {

    private final AuthToken authToken;
    private final TeamColor playerColor;
    private final int gameID;

    public JoinGameRequest(AuthToken authToken, TeamColor playerColor, int gameID) {
        this.authToken = authToken;
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public TeamColor getPlayerColor() {
        return playerColor;
    }

    public int getGameID() {
        return gameID;
    }
}
