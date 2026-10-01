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

            if (canMoveHere(currPos, board)) { // checks index and if same team's piece here

                ChessPiece pieceAtPos = board.getPiece(currPos);

                if (pieceAtPos != null) { // position has piece
                    if (pieceAtPos.getPieceType() == ChessPiece.PieceType.KING) { // attacks other team's king
                        moves.add(new ChessMove(myPosition, currPos, null, true));
                    } else { // not king here
                        moves.add(new ChessMove(myPosition, currPos, null, false));
                        break; // stop iteration. hit a piece
                    }
                } else { // position empty
                    moves.add(new ChessMove(myPosition, currPos, null, false));
                }
                
            } else break; // invalid move. stop iteration
        }

        return moves;
    }

    @Override
    public abstract ChessPiece deepCopy();
}
