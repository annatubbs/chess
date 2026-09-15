package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Knight extends SingleStepPiece {
    public Knight(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.KNIGHT);
    }

    // return array of all possible moves for piece
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        
        // up
        Collection<ChessMove> moves = singleStepMove(board, myPosition, new int[]{2,1});
        moves.addAll(singleStepMove(board, myPosition, new int[]{2,-1}));
        //down
        moves.addAll(singleStepMove(board, myPosition, new int[]{-2,1}));
        moves.addAll(singleStepMove(board, myPosition, new int[]{-2,-1}));
        // right
        moves.addAll(singleStepMove(board, myPosition, new int[]{1,2}));
        moves.addAll(singleStepMove(board, myPosition, new int[]{-1,2}));
        // left
        moves.addAll(singleStepMove(board, myPosition, new int[]{1,-2}));
        moves.addAll(singleStepMove(board, myPosition, new int[]{-1,-2}));

        return moves;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
