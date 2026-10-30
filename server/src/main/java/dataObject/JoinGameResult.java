package dataObject;

import chess.ChessGame.TeamColor;

public class JoinGameResult {
    private final TeamColor playerColor;
    private final int gameID;

    public JoinGameResult(TeamColor playerColor, int gameID) {
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    public TeamColor getPlayerColor() {
        return playerColor;
    }

    public int getGameID() {
        return gameID;
    }
}
