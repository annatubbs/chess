package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Rook extends SlidePiece {
    public Rook(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.ROOK);
    }

    // return array of all possible moves for piece
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        // Add vertical moves
        Collection<ChessMove> moves = slideMoves(board, myPosition, new int[]{1,0});
        moves.addAll(slideMoves(board, myPosition, new int[]{1,0}));

        // Add horizontal moves
        moves.addAll(slideMoves(board, myPosition, new int[]{0,1}));
        moves.addAll(slideMoves(board, myPosition, new int[]{0,-1}));

        return moves;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
