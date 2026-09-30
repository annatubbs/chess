package chess;

import java.util.ArrayList;
import java.util.Collection;

public abstract class SlidePiece extends ChessPiece {

    public SlidePiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        super(pieceColor, type);
    }

    @Override
    public abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);

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

                // check for capture. (clunky, checks piece types twice..)
                ChessPiece pieceAtPos = board.getPiece(currPos);
                if (pieceAtPos != null) { //&& getPieceType() != pieceAtPos.getPieceType()) {
                    // see if move can capture king
                    if (pieceAtPos.getPieceType() == ChessPiece.PieceType.KING) {
                        moves.add(new ChessMove(myPosition, currPos, null, true));
                    }
                    // captures other piece
                    moves.add(new ChessMove(myPosition, currPos, null, false));
                    break;
                }
                // space empty
                moves.add(new ChessMove(myPosition, currPos, null, false));



            } else break; // same team's piece here or off board. stop iteration
        }

        return moves;
    }

    @Override
    public abstract ChessPiece deepCopy();
}
