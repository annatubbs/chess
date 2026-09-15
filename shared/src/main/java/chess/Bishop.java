package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Bishop extends SlidePiece {
    public Bishop(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.BISHOP);
    }

    // return array of all possible moves for piece
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        // Diagonal moves
        Collection<ChessMove> moves = slideMoves(board, myPosition, new int[]{1,1}); // up-right
        moves.addAll(slideMoves(board, myPosition, new int[]{1,-1})); // up-left
        moves.addAll(slideMoves(board, myPosition, new int[]{-1,1})); // down-right
        moves.addAll(slideMoves(board, myPosition, new int[]{-1,-1})); // down-left

        return moves;
    }
}
