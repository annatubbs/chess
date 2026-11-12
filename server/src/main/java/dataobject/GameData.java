package dataobject;

import chess.ChessGame;

import java.io.*;


// **** usernames must be "" when empty! not null *****

public class GameData {
    private final int gameID;
    private String blackUsername;
    private String whiteUsername;
    private final String gameName;
    private ChessGame game;

    public GameData(int gameID, String blackUsername, String whiteUsername, String gameName) {
        System.out.println("Create gameData from default");
        this.gameID = gameID;
        this.blackUsername = blackUsername;
        this.whiteUsername = whiteUsername;
        this.gameName = gameName;
        this.game = new ChessGame();
    }

//    public GameData(int gameID, String blackUsername, String whiteUsername, String gameName, byte[] gameBytes) throws Exception {
//        System.out.println("Create gameData from Bytes");
//        this.gameID = gameID;
//        this.blackUsername = blackUsername;
//        this.whiteUsername = whiteUsername;
//        this.gameName = gameName;
//        this.game = restoreSerializedGame(gameBytes);
//    }

    public GameData(int gameID, String blackUsername, String whiteUsername, String gameName, ChessGame game) {
        System.out.println("Create gameData from gameObj");
        this.gameID = gameID;
        this.blackUsername = blackUsername;
        this.whiteUsername = whiteUsername;
        this.gameName = gameName;
//        this.game = game;
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

//    public ChessGame getGame() {
//        return game;
//    }

//    public void setGame(ChessGame game) {
//        this.game = game;
//    }

    
//    public ChessGame restoreSerializedGame(byte[] byteArray) throws Exception {
//        try (ObjectInputStream objInStrm = new ObjectInputStream(new ByteArrayInputStream(byteArray))) {
//            ChessGame restoredGame = (ChessGame) objInStrm.readObject();
//
//            return restoredGame;
//        }
//    }

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
        return new GameData(gameID, blackUsername, whiteUsername, gameName, game);
    }
}
