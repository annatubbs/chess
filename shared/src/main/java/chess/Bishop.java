package chess;

import java.util.Collection;

public class Bishop extends SlidePiece {

    public Bishop(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.BISHOP);
    }

    // return array of all possible moves for piece
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        // Add diagonal moves
        Collection<ChessMove> moves = slideMoves(board, myPosition, new int[]{1,1}); // up-right
        moves.addAll(slideMoves(board, myPosition, new int[]{1,-1})); // up-left
        moves.addAll(slideMoves(board, myPosition, new int[]{-1,1})); // down-right
        moves.addAll(slideMoves(board, myPosition, new int[]{-1,-1})); // down-left

        return moves;
    }

    @Override
    public Bishop deepCopy() {
        return new Bishop(getTeamColor());
    }
}
