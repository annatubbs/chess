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

        // Add vertical/horizontal moves
        Collection<ChessMove> moves = super.slideMoves(board, myPosition, new int[]{1,0}); // up
        moves.addAll(super.slideMoves(board, myPosition, new int[]{-1,0})); // down
        moves.addAll(super.slideMoves(board, myPosition, new int[]{0,1})); // right
        moves.addAll(super.slideMoves(board, myPosition, new int[]{0,-1})); // left

        // Diagonal moves
        moves.addAll(super.slideMoves(board, myPosition, new int[]{1,1})); // up-right
        moves.addAll(super.slideMoves(board, myPosition, new int[]{1,-1})); // up-left
        moves.addAll(super.slideMoves(board, myPosition, new int[]{-1,1})); // down-right
        moves.addAll(super.slideMoves(board, myPosition, new int[]{-1,-1})); // down-left

        return moves;
    }
}
