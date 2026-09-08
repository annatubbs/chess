package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] board = new ChessPiece[8][8];

    // Piece type order of reset board back row
    public static final ChessPiece.PieceType[] backRowOrder = {ChessPiece.PieceType.ROOK, ChessPiece.PieceType.KNIGHT,
            ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.QUEEN, ChessPiece.PieceType.KING,
            ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.KNIGHT, ChessPiece.PieceType.ROOK};

    public ChessBoard() {
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece)  {
        board[position.getRow()-1][position.getColumn()-1] = piece; // calc for 0-based index
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();

        return board[row][col]; // should return piece or null...?
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        // Set black pieces
        // back row
        for (int col=0; col<=7; col++) {
            board[7][col] = new ChessPiece(ChessGame.TeamColor.BLACK, backRowOrder[col]);
        }
        // front row
        for (int col=0; col<=7; col++) {
            board[6][col] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        }

        // Set white pieces
        // back row
        for (int col=0; col<=7; col++) {
            board[0][col] = new ChessPiece(ChessGame.TeamColor.WHITE, backRowOrder[col]);
        }
        // front row
        for (int col=0; col<=7; col++) {
            board[1][col] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        }

        // Set middle blank
        for (int row=2; row<=5; row++) { // loop just middle rows
            for (int col = 0; col <= 7; col++) { // loop all cols
                board[row][col] = null;
            }
        }
    }
}
