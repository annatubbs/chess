package dataobject;

import chess.ChessGame;


// **** usernames must be "" when empty! not null *****

public class GameData {
    private final int gameID;
    private String blackUsername;
    private String whiteUsername;
    private final String gameName;

    public GameData(int gameID, String blackUsername, String whiteUsername, String gameName) {
        this.gameID = gameID;
        this.blackUsername = blackUsername;
        this.whiteUsername = whiteUsername;
        this.gameName = gameName;
    }

    public int getGameID() {
        return gameID;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public boolean isTaken(ChessGame.TeamColor color) {
        if (color == ChessGame.TeamColor.BLACK) {
            return blackUsername != null;
        } else { // wants white team
            return whiteUsername != null;
        }
    }

    public void fillTeamColor(ChessGame.TeamColor color, String username) {
        if (color == ChessGame.TeamColor.BLACK) {
            blackUsername = username;
        } else { // wants white team
            whiteUsername = username;
        }
    }

    public GameData deepCopy() {
        return new GameData(gameID, blackUsername, whiteUsername, gameName);
    }

}
