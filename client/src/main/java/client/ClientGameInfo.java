package client;

import chess.ChessGame;

public class ClientGameInfo {
    private int gameID;
    private String gameName;
    private Role role;
    private ChessGame.TeamColor playerColor;

    public ClientGameInfo() {
        gameID = 0;
        gameName = null;
        role = null;
        playerColor = null;
    }

    public ClientGameInfo(int gameID, String gameName, Role role, ChessGame.TeamColor playerColor) {
        this.gameID = gameID;
        this.gameName = gameName;
        this.role = role;
        this.playerColor = playerColor;
    }

    public ClientGameInfo(int gameID, String gameName, Role role) {
        this.gameID = gameID;
        this.gameName = gameName;
        this.role = role;
        this.playerColor = null;
    }

    public enum Role {
        PLAYER,
        OBSERVER,
    }

    public int getGameID() {
        return gameID;
    }

    public String getGameName() {
        return gameName;
    }

    public Role getRole() {
        return role;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }


    public void setPlayerColor(ChessGame.TeamColor playerColor) {
        this.playerColor = playerColor;
    }
}
