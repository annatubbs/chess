package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] board = new ChessPiece[8][8];

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

        return board[row-1][col-1]; // should return piece or null...?
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        // Set black pieces
        board[7] = resetBackRow(ChessGame.TeamColor.BLACK);
        board[6] = resetFrontRow(ChessGame.TeamColor.BLACK);

        // Set white pieces
        board[0] = resetBackRow(ChessGame.TeamColor.WHITE);
        board[1] = resetFrontRow(ChessGame.TeamColor.WHITE);

        // Set middle blank
        for (int row=2; row<=5; row++) { // loop just middle rows
            board[row] = new ChessPiece[]{null,null,null,null,null,null,null,null};
        }
    }

    // return back row of pieces at starting positions
    private ChessPiece[] resetBackRow(ChessGame.TeamColor color) {
        return new ChessPiece[]{new Rook(color), new Knight(color), new Bishop(color), new Queen(color),
                                new King(color), new Bishop(color), new Knight(color), new Rook(color)};

    }

    // return front row of pawns for starting board
    private ChessPiece[] resetFrontRow(ChessGame.TeamColor color) {
        return new ChessPiece[]{new Pawn(color), new Pawn(color), new Pawn(color), new Pawn(color),
                                new Pawn(color), new Pawn(color), new Pawn(color), new Pawn(color)};
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public String toString() {
        String boardStr = "";

        // reverse concatenation of rows to print in correct order
        for (ChessPiece[] row : board) {
            String rowStr = "";
            for (ChessPiece colVal : row) {
                if (colVal == null) {
                    rowStr += " ";
                } else {
                    rowStr += colVal.toString(); // fill pieces in row
                }
                rowStr += ", ";
            }
            rowStr = rowStr.substring(0,rowStr.length()-2); // trim last ", "
            boardStr = rowStr + "\n" + boardStr; // add row above existing rows in board
        }

        return "ChessBoard: \n" + boardStr;
    }
}
