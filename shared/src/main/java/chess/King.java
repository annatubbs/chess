package chess;

import java.util.ArrayList;
import java.util.Collection;

public class King extends SingleStepPiece {
    public King(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.KING);
    }

    // return array of all possible moves for piece
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        // Add vertical moves
        Collection<ChessMove> moves = singleStepMove(board, myPosition, new int[]{1,0});
        moves.addAll(singleStepMove(board, myPosition, new int[]{1,0}));

        // Add horizontal moves
        moves.addAll(singleStepMove(board, myPosition, new int[]{0,1}));
        moves.addAll(singleStepMove(board, myPosition, new int[]{0,-1}));

        // Diagonal moves
        moves.addAll(singleStepMove(board, myPosition, new int[]{1,1})); // up-right
        moves.addAll(singleStepMove(board, myPosition, new int[]{1,-1})); // up-left
        moves.addAll(singleStepMove(board, myPosition, new int[]{-1,1})); // down-right
        moves.addAll(singleStepMove(board, myPosition, new int[]{-1,-1})); // down-left

        return moves;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
