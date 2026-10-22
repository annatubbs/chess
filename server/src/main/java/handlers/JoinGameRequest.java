package handlers;

import chess.ChessGame;
import server.AuthTokenSet;
import chess.ChessGame.TeamColor;
import services.BadRequestException;


public class JoinGameRequest {

    private final String authToken;
    private final TeamColor playerColor;
    private final int gameID;

    public JoinGameRequest(String authToken, JoinGameHelperInfo joinGameHelperInfo) {
        try {
            this.authToken = authToken;
            this.playerColor = joinGameHelperInfo.playerColor;
            this.gameID = joinGameHelperInfo.gameID;
        } catch (Exception e) {
            throw new BadRequestException(e);
        }
    }

    public String getAuthToken() {
        return authToken;
    }

    public TeamColor getPlayerColor() {
        return playerColor;
    }

    public int getGameID() {
        return gameID;
    }

    public static class JoinGameHelperInfo {
        private final TeamColor playerColor;
        private final int gameID;

        public JoinGameHelperInfo(String playerColorStr, int gameID) {
            this.playerColor = ChessGame.TeamColor.fromString(playerColorStr);
            this.gameID = gameID;
        }
    }
}
