package chess;

import java.util.ArrayList;
import java.util.Collection;

public abstract class SlidePiece extends ChessPiece {

    public SlidePiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        super(pieceColor, type);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return super.pieceMoves(board, myPosition);
    }

    // Get available moves helper
    protected Collection<ChessMove> slideMoves(ChessBoard board, ChessPosition myPosition, int[] rcDirection) {

        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = myPosition.getRow();
        int currCol = myPosition.getColumn();

        while (true) {
            currRow += rcDirection[0]; // increment piece position
            currCol += rcDirection[1];
            ChessPosition currPos = new ChessPosition(currRow, currCol);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } else break; // same team's piece here or off board. stop iteration
        }

        return moves;
    }
}
