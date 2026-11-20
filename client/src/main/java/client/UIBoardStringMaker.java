package client;

import chess.*;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.Collection;

import static ui.EscapeSequences.*;

public class UIBoardStringMaker {
    private Collection<ChessPosition> validPositions = new ArrayList<ChessPosition>();

    public UIBoardStringMaker() {
    }

    public UIBoardStringMaker(Collection<ChessMove> validMoves) {
        ArrayList<ChessPosition> validPositions = new ArrayList<ChessPosition>();

        for (ChessMove move : validMoves) {
            validPositions.add(move.getEndPosition());
        }
        this.validPositions = validPositions;
    }

    public String getUIBoardString(ChessBoard board, ChessGame.TeamColor playerColor) {
        if (playerColor == ChessGame.TeamColor.WHITE) {
            return toStringWhitePerspective(board);
        } else { // BLACK
            return toStringBlackPerspective(board);
        }
    }

    private String toStringWhitePerspective(ChessBoard board) {
        ChessPiece[][] boardArr = board.getBoardArray();
        String boardStr = "";
        boolean startDarkSquare = true;

        // reverse concatenation of rows to print in correct order
        for (int rowI = 0; rowI < boardArr.length; rowI++) {
            String rowStr = getRowStr(rowI, startDarkSquare, boardArr[rowI], true);
            boardStr = rowStr + "\n" + boardStr;

            startDarkSquare = !startDarkSquare;
        }
        return COL_INDICES_WHITE + "\n" + boardStr + COL_INDICES_WHITE;
    }

    private String toStringBlackPerspective(ChessBoard board) {
        ChessPiece[][] boardArr = board.getBoardArray();
        String boardStr = "";
        boolean startDarkSquare = true;

        // reverse concatenation of rows to print in correct order
        for (int rowI = 0; rowI < boardArr.length; rowI++) {
            String rowStr = getRowStr(rowI, startDarkSquare, boardArr[rowI], false);
            boardStr = boardStr + rowStr + "\n";

            startDarkSquare = !startDarkSquare;
        }
        return COL_INDICES_BLACK + "\n" + boardStr + COL_INDICES_BLACK;
    }

    private String getRowStr(int rowI, boolean startDarkSquare, ChessPiece[] row, boolean whitePerspective) {
        int rowLabel = rowI + 1;
        String rowStr = "";
        boolean isLightSquare = !startDarkSquare;

        for (int colI = 0; colI < row.length; colI++) {
            ChessPiece colVal = row[colI];
            if (whitePerspective) {
                rowStr += setSquareColor(isLightSquare, new ChessPosition(rowI, colI)) + setSquarePiece(colVal);
            } else {
                rowStr = setSquareColor(isLightSquare, new ChessPosition(rowI, colI)) + setSquarePiece(colVal) + rowStr;
            }
            isLightSquare = !isLightSquare;
        }
        rowStr = String.valueOf(rowLabel) + " " + rowStr + RESET_BG_COLOR + RESET_TEXT_COLOR + " " +
                String.valueOf(rowLabel);

        return rowStr;
    }

    private String setSquareColor(boolean isLightSquare, ChessPosition currPos) {
        if (isLightSquare && validPositions.contains(currPos)) {
            return SET_BG_COLOR_GREEN + SET_TEXT_COLOR_BLACK;
        } else if (isLightSquare) { // no highlight
            return SET_BG_COLOR_LIGHT_BROWN + SET_TEXT_COLOR_BLACK;
        } else if (validPositions.contains(currPos)) { // dark square
            return SET_BG_COLOR_DARK_GREEN + SET_TEXT_COLOR_BLACK;
        } else { // dark and no highlight
            return SET_BG_COLOR_DARK_BROWN + SET_TEXT_COLOR_BLACK;
        }
    }

    private String setSquarePiece(ChessPiece colVal) {
        if (colVal == null) {
            return EMPTY;
        } else {
            return getUIPieceString(colVal); // fill pieces in row
        }
    }

    private String getUIPieceString(ChessPiece piece) {
        return switch (piece.getPieceType()) {
            case KING -> piece.getTeamColor() == ChessGame.TeamColor.WHITE ? WHITE_KING : BLACK_KING;
            case QUEEN -> piece.getTeamColor() == ChessGame.TeamColor.WHITE ? WHITE_QUEEN : BLACK_QUEEN;
            case BISHOP -> piece.getTeamColor() == ChessGame.TeamColor.WHITE ? WHITE_BISHOP : BLACK_BISHOP;
            case KNIGHT -> piece.getTeamColor() == ChessGame.TeamColor.WHITE ? WHITE_KNIGHT : BLACK_KNIGHT;
            case ROOK -> piece.getTeamColor() == ChessGame.TeamColor.WHITE ? WHITE_ROOK : BLACK_ROOK;
            case PAWN -> piece.getTeamColor() == ChessGame.TeamColor.WHITE ? WHITE_PAWN : BLACK_PAWN;
        };
    }

}
