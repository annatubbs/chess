package chess;

import java.util.ArrayList;
import java.util.Collection;

public class King extends ChessPiece {
    public King(ChessGame.TeamColor pieceColor) {
        super(pieceColor,PieceType.KING);
    }

    // Gather all available moves helper funcs
    // Collect all vertical (row) moves
        private Collection<ChessMove> verticalMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = myPosition.getRow();
        int currCol = myPosition.getColumn();

        // Move up
        if (currRow < 8) {
            ChessPosition currPos = new ChessPosition(currRow+1,currCol);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here. can't go further. stop iteration
        }

        // Move down
        if (currRow > 1) {
            ChessPosition currPos = new ChessPosition(currRow-1,currCol);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here. can't go further. stop iteration
        }

        return moves;
    }

    // Collect all horizontal (col) moves
    private Collection<ChessMove> horizontalMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = myPosition.getRow();
        int currCol = myPosition.getColumn();

        // Move right
        if (currCol < 8) {
            ChessPosition currPos = new ChessPosition(currRow,currCol+1);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here. can't go further. stop iteration
        }

        // Move left
        if (currCol > 1) {
            ChessPosition currPos = new ChessPosition(currRow,currCol-1);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here. can't go further. stop iteration
        }

        return moves;
    }

    // Collect all up-right diagonal moves
    private Collection<ChessMove> upRightDiagonalMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (row < 8 && col < 8) {
            ChessPosition currPos = new ChessPosition(row+1,col+1);

            if (canMoveHere(currPos, board)) { // space empty or other team's piece
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here. can't go further. stop iteration
        }

        return moves;
    }

    // Collect all up-left diagonal moves
    private Collection<ChessMove> upLeftDiagonalMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (row < 8 && col > 1) {
            ChessPosition currPos = new ChessPosition(row+1,col-1);

            if (canMoveHere(currPos, board)) { // space empty or other team's piece
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here. can't go further. stop iteration
        }

        return moves;
    }

    // Collect all down-right diagonal moves
    private Collection<ChessMove> downRightDiagonalMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (row > 1 && col < 8) {
            ChessPosition currPos = new ChessPosition(row-1,col+1);

            if (canMoveHere(currPos, board)) { // space empty or other team's piece
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here. can't go further. stop iteration
        }

        return moves;
    }

    // Collect all down-left diagonal moves
    private Collection<ChessMove> downLeftDiagonalMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (row > 1 && col > 1) {
            ChessPosition currPos = new ChessPosition(row-1,col-1);

            if (canMoveHere(currPos, board)) { // space empty or other team's piece
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here. can't go further. stop iteration
        }

        return moves;
    }

    // return array of all possible moves for piece
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        // Add horizontal/vertical moves
        Collection<ChessMove> moves = verticalMoves(board,myPosition);
        moves.addAll(horizontalMoves(board,myPosition));

        // Add diagonal moves
        moves.addAll(upRightDiagonalMoves(board,myPosition));
        moves.addAll(upLeftDiagonalMoves(board,myPosition));
        moves.addAll(downRightDiagonalMoves(board,myPosition));
        moves.addAll(downLeftDiagonalMoves(board,myPosition));

        return moves;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
