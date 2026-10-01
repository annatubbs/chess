package chess;

import java.util.ArrayList;
import java.util.Collection;

public abstract class SingleStepPiece extends ChessPiece {

    public SingleStepPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) { super(pieceColor, type);}

    @Override
    public abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);

    // Get available moves helper
    protected Collection<ChessMove> singleStepMove(ChessBoard board, ChessPosition myPosition, int[] rcDirection) {

        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = myPosition.getRow();
        int currCol = myPosition.getColumn();
        ChessPosition currPos = new ChessPosition(currRow+rcDirection[0], currCol+rcDirection[1]);

        if (canMoveHere(currPos, board)) { // Can move here: space empty or other team's piece
            ChessPiece currPiece = board.getPiece(currPos);

            boolean attacksKing = (currPiece != null) && (currPiece.getPieceType() == PieceType.KING);
            moves.add(new ChessMove(myPosition, currPos, null, attacksKing));

        } // else: same team's piece here. can't go further. stop iteration

        return moves;
    }

    @Override
    public abstract ChessPiece deepCopy();
}
