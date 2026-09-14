package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Knight extends ChessPiece {
    public Knight(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.KNIGHT);
    }

    // Gather all available moves helper funcs
    // Collect all up  moves
    private Collection<ChessMove> upMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        // up-right
        if (row < 7 && col < 8) {
            ChessPosition currPos = new ChessPosition(row+2,col+1);

            if (canMoveHere(currPos, board)) { // space empty or other team's piece
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here. can't go further. stop iteration
        }
        // up-left
        if (row < 7 && col > 1) {
            ChessPosition currPos = new ChessPosition(row+2,col-1);

            if (canMoveHere(currPos, board)) { // space empty or other team's piece
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here. can't go further. stop iteration
        }

        return moves;
    }

    // Collect all down moves
    private Collection<ChessMove> downMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        // down-right
        if (row > 2 && col < 8) {
            ChessPosition currPos = new ChessPosition(row-2,col+1);

            if (canMoveHere(currPos, board)) { // space empty or other team's piece
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here. can't go further. stop iteration
        }
        // down-left
        if (row > 2 && col > 1) {
            ChessPosition currPos = new ChessPosition(row-2,col-1);

            if (canMoveHere(currPos, board)) { // space empty or other team's piece
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here. can't go further. stop iteration
        }

        return moves;
    }

    // Collect all right  moves
    private Collection<ChessMove> rightMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        // right-up
        if (row < 8 && col < 7) {
            ChessPosition currPos = new ChessPosition(row+1,col+2);

            if (canMoveHere(currPos, board)) { // space empty or other team's piece
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here. can't go further. stop iteration
        }
        // right-down
        if (row > 1 && col < 7) {
            ChessPosition currPos = new ChessPosition(row-1,col+2);

            if (canMoveHere(currPos, board)) { // space empty or other team's piece
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here. can't go further. stop iteration
        }

        return moves;
    }

    // Collect all left  moves
    private Collection<ChessMove> leftMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        // left-up
        if (row < 8 && col > 2) {
            ChessPosition currPos = new ChessPosition(row+1,col-2);

            if (canMoveHere(currPos, board)) { // space empty or other team's piece
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here. can't go further. stop iteration
        }
        // left-down
        if (row < 1 && col > 2) {
            ChessPosition currPos = new ChessPosition(row-1,col-2);

            if (canMoveHere(currPos, board)) { // space empty or other team's piece
                moves.add(new ChessMove(myPosition, currPos, null));
            } // else: same team's piece here. can't go further. stop iteration
        }

        return moves;
    }

    // return array of all possible moves for piece
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        // Compile list of moves
        Collection<ChessMove> moves = upMoves(board, myPosition);
        moves.addAll(downMoves(board, myPosition));
        moves.addAll(leftMoves(board, myPosition));
        moves.addAll(rightMoves(board, myPosition));

        return moves;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
