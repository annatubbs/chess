package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Rook extends ChessPiece {
    public Rook(ChessGame.TeamColor pieceColor) {
        super(pieceColor,PieceType.ROOK);
    }

    // Gather all available moves helper funcs
    // Collect all vertical (row) moves
    private Collection<ChessMove> verticalMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = myPosition.getRow();
        int currCol = myPosition.getColumn();

        // Move up
        for (int row=currRow; row<=8; row++) { // ***think 8 works w/o index error. getPiece calculates index offset..
            ChessPosition currPos = new ChessPosition(row,currCol);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } else {
                break; // same team's piece here. can't go further. stop iteration
            }
        }
        // Move down
        for (int row=currRow; row>=1; row--) { // ***think 1 works w/o miss spot. getPiece calculates index offset..
            ChessPosition currPos = new ChessPosition(row,currCol);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } else {
                break; // same team's piece here. can't go further.stop iteration
            }
        }

        return moves;
    }

    // Collect all horizontal (col) moves
    private Collection<ChessMove> horizontalMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = myPosition.getRow();
        int currCol = myPosition.getColumn();

        // Move right
        for (int col=currCol; col<=8; col++) { // ***think 8 works w/o index error. getPiece calculates index offset..
            ChessPosition currPos = new ChessPosition(currRow,col);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } else {
                break; // same team's piece here. can't go further. stop iteration
            }
        }
        // Move left
        for (int col=currCol; col>=1; col--) { // ***think 1 works w/o miss spot. getPiece calculates index offset..
            ChessPosition currPos = new ChessPosition(currRow,col);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } else {
                break; // same team's piece here. can't go further.stop iteration
            }
        }

        return moves;
    }

    // return array of all possible moves for piece
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // collect vertical moves
        Collection<ChessMove> moves = verticalMoves(board,myPosition);
        // collect horizontal moves
        moves.addAll(horizontalMoves(board,myPosition));

        return moves;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
