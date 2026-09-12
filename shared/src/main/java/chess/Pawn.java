package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Pawn extends ChessPiece {
    private boolean firstMove;

    public Pawn(ChessGame.TeamColor pieceColor) {
        super(pieceColor,PieceType.PAWN);
        firstMove = false; //*********change to true when real thing
    }

    // Potential piece moves helpers

    private boolean isEnemyPiece(ChessPosition currPos, ChessBoard board) {
        ChessPiece pieceAtCurrPos = board.getPiece(currPos);

        if (pieceAtCurrPos == null) { // position empty
            return false;
        } else { // return true if piece of other team; false if own team
            return this.getTeamColor() != pieceAtCurrPos.getTeamColor();
        }
    }

    // WHITE pieces
    // can move 2 spaces on first move
    private Collection<ChessMove> firstMoveWhite(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = myPosition.getRow();
        int currCol = myPosition.getColumn();

        // moves up
        ChessPosition currPos = new ChessPosition(currRow+2,currCol);

        if (canMoveHere(currPos, board)) {
           moves.add(new ChessMove(myPosition, currPos, null));
        } // else: same team's piece here. can't go further. stop iteration

        return moves;
    }

    private Collection<ChessMove> forwardMoveWhite(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = myPosition.getRow();
        int currCol = myPosition.getColumn();

        // White piece: moves up
        if (currRow < 7) { // in middle of board
            ChessPosition currPos = new ChessPosition(currRow+1,currCol);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here

        } else if (currRow == 7) { // promotion
            ChessPosition currPos = new ChessPosition(currRow+1,currCol);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, ChessPiece.PieceType.QUEEN));
            } // else: same team's piece here
        } // else : currRow == 8, at end of board. Should never get here

        return moves;
    }

    // Capture moves
    private Collection<ChessMove> diagonalMoveWhite(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = myPosition.getRow();
        int currCol = myPosition.getColumn();

        // White piece: moves up
        if (currRow < 7) { // in middle of board

            // right diagonal
            if (currCol < 8) {
                ChessPosition currPos = new ChessPosition(currRow+1, currCol+1);

                if (isEnemyPiece(currPos, board)) {
                    moves.add(new ChessMove(myPosition, currPos, null));
                } // else: same team's piece here
            }
            // left diagonal
            if (currCol > 1) {
                ChessPosition currPos = new ChessPosition(currRow+1, currCol-1);

                if (isEnemyPiece(currPos, board)) {
                    moves.add(new ChessMove(myPosition, currPos, null));
                } // else: same team's piece here
            }

        } else if (currRow == 7) { // promotion

            // right diagonal
            if (currCol < 8) {
                ChessPosition currPos = new ChessPosition(currRow+1, currCol+1);

                if (isEnemyPiece(currPos, board)) {
                    moves.add(new ChessMove(myPosition, currPos, ChessPiece.PieceType.QUEEN));
                } // else: same team's piece here
            }
            // left diagonal
            if (currCol > 1) {
                ChessPosition currPos = new ChessPosition(currRow+1, currCol-1);

                if (isEnemyPiece(currPos, board)) {
                    moves.add(new ChessMove(myPosition, currPos, ChessPiece.PieceType.QUEEN));
                } // else: same team's piece here
            }

        } // else: currRow == 1, at end of board. Should never get here without being promoted

        return moves;
    }

    // BLACK pieces
    // can move 2 spaces on first move
    private Collection<ChessMove> firstMoveBlack(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = myPosition.getRow();
        int currCol = myPosition.getColumn();

        // BLACK piece. Moves down
        ChessPosition currPos = new ChessPosition(currRow-2,currCol);

        if (canMoveHere(currPos, board)) {
           moves.add(new ChessMove(myPosition, currPos, null));
        } // else: same team's piece here. can't go further. stop iteration

        return moves;
    }

    private Collection<ChessMove> forwardMoveBlack(ChessBoard board, ChessPosition myPosition){

        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = myPosition.getRow();
        int currCol = myPosition.getColumn();

        // BLACK piece. Moves down
        if (currRow > 2) { // in middle of board
            ChessPosition currPos = new ChessPosition(currRow-1,currCol);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here

        } else if (currRow == 2) { // promotion
            ChessPosition currPos = new ChessPosition(currRow-1,currCol);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, ChessPiece.PieceType.QUEEN));
            } // else: same team's piece here
        } // else: currRow == 1, at end of board. Should never get here

        return moves;
    }

    // Capture moves
    private Collection<ChessMove> diagonalMoveBlack(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = myPosition.getRow();
        int currCol = myPosition.getColumn();

        // BLACK piece. Moves down
        if (currRow > 2) { // in middle of board
            // right diagonal
            if (currCol < 8) {
                ChessPosition currPos = new ChessPosition(currRow-1, currCol+1);

                if (isEnemyPiece(currPos, board)) {
                    moves.add(new ChessMove(myPosition, currPos, null));
                } // else: same team's piece here
            }
            // left diagonal
            if (currCol > 1) {
                ChessPosition currPos = new ChessPosition(currRow-1, currCol-1);

                if (isEnemyPiece(currPos, board)) {
                    moves.add(new ChessMove(myPosition, currPos, null));
                } // else: same team's piece here
            }

        } else if (currRow == 2) { // promotion
            // right diagonal
            if (currCol < 8) {
                ChessPosition currPos = new ChessPosition(currRow-1, currCol+1);

                if (isEnemyPiece(currPos, board)) {
                    moves.add(new ChessMove(myPosition, currPos, ChessPiece.PieceType.QUEEN));
                } // else: same team's piece here
            }
            // left diagonal
            if (currCol > 1) {
                ChessPosition currPos = new ChessPosition(currRow-1, currCol-1);

                if (isEnemyPiece(currPos, board)) {
                    moves.add(new ChessMove(myPosition, currPos, ChessPiece.PieceType.QUEEN));
                } // else: same team's piece here
            }

        } // else: currRow == 1, at end of board. Should never get here without being promoted

        return moves;
    }

    // return array of all possible moves for piece
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        if (this.getTeamColor() == ChessGame.TeamColor.WHITE) {
            if (firstMove) {
                moves.addAll(firstMoveWhite(board,myPosition));
            }
            // Add forward move
            moves.addAll(forwardMoveWhite(board,myPosition));

            // Add diagonal move
            moves.addAll(diagonalMoveWhite(board,myPosition));

        } else { // piece color BLACK
            if (firstMove) {
                moves.addAll(firstMoveBlack(board,myPosition));
            }
            // Add forward move
            moves.addAll(forwardMoveBlack(board,myPosition));

            // Add diagonal move
            moves.addAll(diagonalMoveBlack(board,myPosition));
        }

        return moves;
    }
}
