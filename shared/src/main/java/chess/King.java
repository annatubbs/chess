package chess;

import java.util.Collection;

public class King extends SingleStepPiece {

    public King(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.KING);
    }

    // return array of all possible moves for piece
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        // Add horizontal moves
        Collection<ChessMove> moves = singleStepMove(board, myPosition, new int[]{1,0}); // up
        moves.addAll(singleStepMove(board, myPosition, new int[]{-1,0})); // down
        // Add horizontal moves
        moves.addAll(singleStepMove(board, myPosition, new int[]{0,1})); // right
        moves.addAll(singleStepMove(board, myPosition, new int[]{0,-1})); // left
        // Add diagonal moves
        moves.addAll(singleStepMove(board, myPosition, new int[]{1,1})); // up-right
        moves.addAll(singleStepMove(board, myPosition, new int[]{1,-1})); // up-left
        moves.addAll(singleStepMove(board, myPosition, new int[]{-1,1})); // down-right
        moves.addAll(singleStepMove(board, myPosition, new int[]{-1,-1})); // down-left

        return moves;
    }

    @Override
    public King deepCopy() {
        return new King(getTeamColor());
    }
}
