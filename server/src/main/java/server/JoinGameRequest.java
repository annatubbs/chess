package server;

import server.AuthTokens.AuthToken;
import chess.ChessGame.TeamColor;


public class JoinGameRequest {

    private final AuthTokens.AuthToken authToken;
    private final TeamColor playerColor;
    private final int gameID;

    public JoinGameRequest(AuthTokens.AuthToken authToken, TeamColor playerColor, int gameID) {
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
