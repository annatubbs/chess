package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Bishop extends ChessPiece {
    public Bishop(ChessGame.TeamColor pieceColor) {
        super(pieceColor,PieceType.BISHOP);
    }

    // Gather all available moves helper funcs
    // Collect all up-right diagonal moves
    private Collection<ChessMove> upRightDiagonalMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();

        int row = myPosition.getRow(); // temp vars
        int col = myPosition.getColumn();

        while (row < 8 && col < 8) {
            row++;
            col++;

            ChessPosition currPos = new ChessPosition(row,col);

            if (canMoveHere(currPos, board)) { // space empty or other team's piece
                moves.add(new ChessMove(myPosition, currPos, null));
            } else {
                break; // same team's piece here. can't go further. stop iteration
            }
        }

        return moves;
    }

    // Collect all up-left diagonal moves
    private Collection<ChessMove> upLeftDiagonalMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();

        int row = myPosition.getRow(); // temp vars
        int col = myPosition.getColumn();

        while (row < 8 && col > 1) {
            row++;
            col--;

            ChessPosition currPos = new ChessPosition(row,col);

            if (canMoveHere(currPos, board)) { // space empty or other team's piece
                moves.add(new ChessMove(myPosition, currPos, null));
            } else {
                break; // same team's piece here. can't go further. stop iteration
            }
        }

        return moves;
    }

    // Collect all down-right diagonal moves
    private Collection<ChessMove> downRightDiagonalMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();

        int row = myPosition.getRow(); // temp vars
        int col = myPosition.getColumn();

        while (row > 1 && col < 8) {
            row--;
            col++;

            ChessPosition currPos = new ChessPosition(row,col);

            if (canMoveHere(currPos, board)) { // space empty or other team's piece
                moves.add(new ChessMove(myPosition, currPos, null));
            } else {
                break; // same team's piece here. can't go further. stop iteration
            }
        }

        return moves;
    }

    // Collect all down-left diagonal moves
    private Collection<ChessMove> downLeftDiagonalMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();

        int row = myPosition.getRow(); // temp vars
        int col = myPosition.getColumn();

        while (row > 1 && col > 1) {
            row--;
            col--;

            ChessPosition currPos = new ChessPosition(row,col);

            if (canMoveHere(currPos, board)) { // space empty or other team's piece
                moves.add(new ChessMove(myPosition, currPos, null));
            } else {
                break; // same team's piece here. can't go further. stop iteration
            }
        }

        return moves;
    }

    // return array of all possible moves for piece
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // Collect moves in each diagonal direction
        Collection<ChessMove> moves = upRightDiagonalMoves(board,myPosition);
        moves.addAll(upLeftDiagonalMoves(board,myPosition));
        moves.addAll(downRightDiagonalMoves(board,myPosition));
        moves.addAll(downLeftDiagonalMoves(board,myPosition));

        return moves;
    }
}
