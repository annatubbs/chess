package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType type;


    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if (this.type == PieceType.ROOK) {
            return rookMoves(board,myPosition);
        } else if (this.type == PieceType.KNIGHT) {
            return null;
        } else if (this.type == PieceType.BISHOP) {
            return null;
        } else if (this.type == PieceType.QUEEN) {
            return null;
        } else if (this.type == PieceType.KING) {
            return null;
        } else { // this.type == PieceType.PAWN) {
            return null;
        }
    }

    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = myPosition.getRow();
        int currCol = myPosition.getColumn();

        // Check col moves
        // Move up
        for (int col=currCol; col<=7; col++) {
            ChessPosition currPos = new ChessPosition(currRow,col);
            ChessPiece pieceAtCurrPos = board.getPiece(currPos);

            // See if any piece there
            if (pieceAtCurrPos == null) { // position empty
                moves.add(new ChessMove(myPosition, currPos, null));
            } else { // already has piece there
                if (this.pieceColor != pieceAtCurrPos.getTeamColor()) { // other team's piece here. Take piece.
                    moves.add(new ChessMove(myPosition, currPos, null));
                } else { // same team's peace here. stop iteration
                    break;
                }
            }

        }

        return moves;
    }
}
