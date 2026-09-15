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
        if (type == PieceType.KING) {
            King temp = new King(ChessGame.TeamColor.WHITE);
            return temp.pieceMoves(board,myPosition);

        } else if (type == PieceType.QUEEN) {
            Queen temp = new Queen(ChessGame.TeamColor.WHITE);
            return temp.pieceMoves(board,myPosition);

        } else if (type == PieceType.BISHOP) {
            Bishop temp = new Bishop(ChessGame.TeamColor.WHITE);
            return temp.pieceMoves(board,myPosition);

        } else if (type == PieceType.KNIGHT) {
            Knight temp = new Knight(ChessGame.TeamColor.WHITE);
            return temp.pieceMoves(board,myPosition);

        } else if (type == PieceType.ROOK) {
            Rook temp = new Rook(ChessGame.TeamColor.WHITE);
            return temp.pieceMoves(board,myPosition);

        } else if (type == PieceType.PAWN) {
            Pawn temp = new Pawn(ChessGame.TeamColor.WHITE);
            return temp.pieceMoves(board,myPosition);
        } else
            throw new UnsupportedOperationException("Subclass should call this pieceMoves() only :(");
    }

    // Checks if the piece can move to this spot
    public boolean canMoveHere(ChessPosition currPos, ChessBoard board) {
        // check if in board's range
        int currRow = currPos.getRow();
        int currCol = currPos.getColumn();

        if (!(currRow >= 1 && currRow <= 8 && currCol >= 1 && currCol <= 8)) {
            return false;
        }

        // check if space occupied
        ChessPiece pieceAtCurrPos = board.getPiece(currPos);

        if (pieceAtCurrPos == null) { // position empty
            return true;
        } else { // return true if piece of other team; false if own team
            return this.pieceColor != pieceAtCurrPos.getTeamColor();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ChessPiece)) {
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

