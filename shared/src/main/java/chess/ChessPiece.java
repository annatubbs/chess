package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor pieceColor;
    private final ChessPiece.PieceType type;


    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING("King") {
            @Override
            public ChessPiece create(ChessGame.TeamColor pieceColor) {
                return new King(pieceColor);
            }
        },
        QUEEN("Queen") {
            @Override
            public ChessPiece create(ChessGame.TeamColor pieceColor) {
                return new Queen(pieceColor);
            }
        },
        BISHOP("Bishop") {
            @Override
            public ChessPiece create(ChessGame.TeamColor pieceColor) {
                return new Bishop(pieceColor);
            }
        },
        KNIGHT("Knight") {
            @Override
            public ChessPiece create(ChessGame.TeamColor pieceColor) {
                return new Knight(pieceColor);
            }
        },
        ROOK("Rook") {
            @Override
            public ChessPiece create(ChessGame.TeamColor pieceColor) {
                return new Rook(pieceColor);
            }
        },
        PAWN("Pawn") {
            @Override
            public ChessPiece create(ChessGame.TeamColor pieceColor) {
                return new Pawn(pieceColor);
            }
        };
        // String label for each enum val
        private final String label;
        // Abstract constructor
        public abstract ChessPiece create(ChessGame.TeamColor pieceColor);
        // initialize String label
        PieceType (String label) {
            this.label = label;
        }
        // toString()
        @Override
        public String toString() {
            return label;
        }
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
        for (int col=currCol; col<=8; col++) { // ***think 8 works w/o index error. getPiece calculates index offset..
            ChessPosition currPos = new ChessPosition(currRow,col);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } else {
                break; // same team's piece here. can't go further. stop iteration
            }
        }
        // Move down
        for (int col=currCol; col>=1; col--) { // ***think 1 works w/o miss spot. getPiece calculates index offset..
            ChessPosition currPos = new ChessPosition(currRow,col);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } else {
                break; // same team's piece here. can't go further.stop iteration
            }
        }

        // Check row moves
        // Move right
        for (int row=currRow; row<=8; row++) { // ***think 8 works w/o index error. getPiece calculates index offset..
            ChessPosition currPos = new ChessPosition(row,currCol);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } else {
                break; // same team's piece here. can't go further. stop iteration
            }
        }
        // Move left
        for (int row=currRow; row>=1; row--) { // ***think 1 works w/o miss spot. getPiece calculates index offset..
            ChessPosition currPos = new ChessPosition(row,currCol);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } else {
                break; // same team's piece here. can't go further.stop iteration
            }
        }

        return moves;
    }


    public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int currRow = myPosition.getRow();
        int currCol = myPosition.getColumn();

        // Move up-left
        //int tempRow = currRow;
        for (int col=currCol; col<=8; col++) { // ***think 8 works w/o index error. getPiece calculates index offset..
            //row += 1;

            ChessPosition currPos = new ChessPosition(currRow,col);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } else {
                break; // same team's piece here. can't go further. stop iteration
            }
        }
        // Move down
        for (int col=currCol; col>=1; col--) { // ***think 1 works w/o miss spot. getPiece calculates index offset..
            ChessPosition currPos = new ChessPosition(currRow,col);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } else {
                break; // same team's piece here. can't go further.stop iteration
            }
        }

        // Check row moves
        // Move right
        for (int row=currRow; row<=8; row++) { // ***think 8 works w/o index error. getPiece calculates index offset..
            ChessPosition currPos = new ChessPosition(row,currCol);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } else {
                break; // same team's piece here. can't go further. stop iteration
            }
        }
        // Move left
        for (int row=currRow; row>=1; row--) { // ***think 1 works w/o miss spot. getPiece calculates index offset..
            ChessPosition currPos = new ChessPosition(row,currCol);

            if (canMoveHere(currPos, board)) {
                moves.add(new ChessMove(myPosition, currPos, null));
            } else {
                break; // same team's piece here. can't go further.stop iteration
            }
        }

        return moves;
    }


    // Checks if the piece can move to this spot
    public boolean canMoveHere(ChessPosition currPos, ChessBoard board) {
        ChessPiece pieceAtCurrPos = board.getPiece(currPos);

        if (pieceAtCurrPos == null) { // position empty
            return true;
        } else { // return true if piece of other team; false if own team
            return this.pieceColor != pieceAtCurrPos.getTeamColor();
        }
    }

    // interface?? ^


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return pieceColor.toString() + " " + type.toString();
    }
}

