package client;

import chess.ChessGame;

public class ClientGameInfo {
    private int gameID;
    private String gameName;
    private ChessGame.TeamColor playerColor;
    private Role role;

    public ClientGameInfo() {
        gameID = 0;
        gameName = null;
        playerColor = null;
        role = null;
    }

    public enum Role {
        PLAYER,
        OBSERVER,
    }
}
