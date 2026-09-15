package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Pawn extends ChessPiece {
    private boolean firstMove;

    public Pawn(ChessGame.TeamColor pieceColor) {
        super(pieceColor,PieceType.PAWN);
        firstMove = false; //*********change to true when real thing
    }

    // Potential piece moves helpers

    // Tells if piece moves up/down
    private int upDownFactor() {

        if (this.getTeamColor() == ChessGame.TeamColor.WHITE) { // piece color WHITE; move up
            return 1;
        }
        // piece color BLACK; move down
        return -1;
    }

   // Checks if the piece can move to this spot
    private boolean canMoveHere(ChessPosition currPos, ChessBoard board, boolean captureMove) {
        // Check if in board's range
        int currRow = currPos.getRow();
        int currCol = currPos.getColumn();

        if (!(currRow >= 1 && currRow <= 8 && currCol >= 1 && currCol <= 8)) {
            return false;
        }
        
        // Check if piece is at spot
        ChessPiece pieceAtCurrPos = board.getPiece(currPos);
        // moving fwd
        if (!captureMove) {
            return pieceAtCurrPos == null; // pawns can only move fwd if space empty
        }
        // move to capture piece; diagonal
        if (pieceAtCurrPos == null) { // position empty
            return false;
        }
        return this.getTeamColor() != pieceAtCurrPos.getTeamColor(); // return true if piece of other team
    }
    
    private Collection<ChessMove> singleStepMove(ChessBoard board, ChessPosition myPosition, int[] xyDirection, 
                                                 boolean captureMove) {

        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = myPosition.getRow() + xyDirection[0];
        int currCol = myPosition.getColumn() + xyDirection[1];

        // moves up
        ChessPosition currPos = new ChessPosition(currRow, currCol);

        if (canMoveHere(currPos, board, captureMove)) {
            if (currRow == 1 || currRow == 8) { // promote pawn to queen at end row
                moves.add(new ChessMove(myPosition, currPos, ChessPiece.PieceType.QUEEN));
            } else { // no promotion
                moves.add(new ChessMove(myPosition, currPos, null));
            }
        } // else: same team's piece here. can't go further. stop iteration

        return moves;
    }

    // return array of all possible moves for piece
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        int upDownFactor = upDownFactor(); // based on team color
        Collection<ChessMove> moves = new ArrayList<>();
        
        // first move, can go 2 steps
        if (firstMove) {
            moves.addAll(singleStepMove(board, myPosition, new int[]{upDownFactor*2,0}, false));
        }
        // forward move
        moves.addAll(singleStepMove(board, myPosition, new int[]{upDownFactor,0}, false));

        // diagonal moves
        moves.addAll(singleStepMove(board, myPosition, new int[]{upDownFactor,1}, true)); // right
        moves.addAll(singleStepMove(board, myPosition, new int[]{upDownFactor,-1}, true)); // left
        
        return moves;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
