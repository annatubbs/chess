package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Pawn extends ChessPiece {
    private boolean firstMove;

    public Pawn(ChessGame.TeamColor pieceColor) {
        super(pieceColor,PieceType.PAWN);
        firstMove = true;
    }

    public Pawn(ChessGame.TeamColor pieceColor, boolean firstMove) {
        super(pieceColor,PieceType.PAWN);
        this.firstMove = firstMove;
    }

    // when first move done, sets firstMove to false
    public void didFirstMove() {
        firstMove = false;
    }

    // Helpers for potential piece moves

    // Tells if piece moves up/down
    private int upDownFactor() {

        if (this.getTeamColor() == ChessGame.TeamColor.WHITE) return 1; // WHITE; move up
        return -1; // BLACK; move down
    }

   // Checks if the piece can move to this spot
    private boolean canMoveHere(ChessPosition currPos, ChessBoard board, boolean captureMove) {
        // Check if in board's range
        if (!currPos.inRange()) {
            return false;
        }

        // Check piece at position
        ChessPiece pieceAtCurrPos = board.getPiece(currPos);

        if (captureMove) { // diagonal move. capture
            if (pieceAtCurrPos == null) return false;
            return this.getTeamColor() != pieceAtCurrPos.getTeamColor();
        }
        return pieceAtCurrPos == null; // forward move. not capture
    }

    // Calculate if new move possible
    private Collection<ChessMove> pawnMove(ChessBoard board, ChessPosition myPosition, int[] xyDirection, 
                                                 boolean captureMove) {

        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = myPosition.getRow() + xyDirection[0];
        int currCol = myPosition.getColumn() + xyDirection[1];

        ChessPosition currPos = new ChessPosition(currRow, currCol);

        if (canMoveHere(currPos, board, captureMove)) {
            ChessPiece currPiece = board.getPiece(currPos);
            boolean isKingHere = currPiece != null && (currPiece).getPieceType() == PieceType.KING;

            if (currRow == 1 || currRow == 8) { // promote pawn when arrives at last row
                moves.add(new ChessMove(myPosition, currPos, ChessPiece.PieceType.QUEEN, isKingHere));
                moves.add(new ChessMove(myPosition, currPos, ChessPiece.PieceType.BISHOP, isKingHere));
                moves.add(new ChessMove(myPosition, currPos, ChessPiece.PieceType.KNIGHT, isKingHere));
                moves.add(new ChessMove(myPosition, currPos, ChessPiece.PieceType.ROOK, isKingHere));
            } else { // no promotion
                moves.add(new ChessMove(myPosition, currPos, null, isKingHere));
            }
        } // else: same team's piece here. can't go further.

        return moves;
    }

    private Collection<ChessMove> firstMove(ChessBoard board, ChessPosition myPosition, int upDownFactor) {
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        // checks for correct start row. check if piece randomly spawned on board by autograder
        if (!((getTeamColor() == ChessGame.TeamColor.WHITE && row == 2) ||
                (getTeamColor() == ChessGame.TeamColor.BLACK && row == 7))) {
            return moves;
        }

        ChessPosition currPos = new ChessPosition(row + upDownFactor, col);
        if (board.getPiece(currPos) != null) return moves;
        // cont to 2nd square if not blocked
        moves.addAll(pawnMove(board, myPosition, new int[]{upDownFactor*2,0}, false));

        return moves;
    }

    // return array of all possible moves for piece
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        int upDownFactor = upDownFactor(); // based on team color
        Collection<ChessMove> moves = new ArrayList<>();
        
        // first move, can go 2 steps
        if (firstMove) {// check that not piece randomly spawned on board by autograder
            moves.addAll(firstMove(board, myPosition, upDownFactor));
        }

        // forward move
        moves.addAll(pawnMove(board, myPosition, new int[]{upDownFactor,0}, false));
        // diagonal moves
        moves.addAll(pawnMove(board, myPosition, new int[]{upDownFactor,1}, true)); // right
        moves.addAll(pawnMove(board, myPosition, new int[]{upDownFactor,-1}, true)); // left
        
        return moves;
    }

    @Override
    public Pawn deepCopy() {
        return new Pawn(getTeamColor(), firstMove);
    }
}
