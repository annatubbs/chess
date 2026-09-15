package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Queen extends SlidePiece {
    public Queen(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.QUEEN);
    }

    // return array of all possible moves for piece
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        // Add vertical moves
        Collection<ChessMove> moves = super.slideMoves(board, myPosition, new int[]{1,0});
        moves.addAll(super.slideMoves(board, myPosition, new int[]{1,0}));

        // Add horizontal moves
        moves.addAll(super.slideMoves(board, myPosition, new int[]{0,1}));
        moves.addAll(super.slideMoves(board, myPosition, new int[]{0,-1}));

        // Diagonal moves
        moves.addAll(super.slideMoves(board, myPosition, new int[]{1,1})); // up-right
        moves.addAll(super.slideMoves(board, myPosition, new int[]{1,-1})); // up-left
        moves.addAll(super.slideMoves(board, myPosition, new int[]{-1,1})); // down-right
        moves.addAll(super.slideMoves(board, myPosition, new int[]{-1,-1})); // down-left

        return moves;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
