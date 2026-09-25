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
    private ChessPiece[][] board;

    public ChessBoard() { board = new ChessPiece[8][8];}

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
        return board[position.getRow()-1][position.getColumn()-1]; // should return piece or null...?
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
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

    public void resetBoard() {
        ChessPiece[][] startBoard = new ChessPiece[8][8];

        // Set black pieces
        startBoard[7] = resetBackRow(ChessGame.TeamColor.BLACK);
        startBoard[6] = resetFrontRow(ChessGame.TeamColor.BLACK);

        // Set white pieces
        startBoard[0] = resetBackRow(ChessGame.TeamColor.WHITE);
        startBoard[1] = resetFrontRow(ChessGame.TeamColor.WHITE);

        board = startBoard;
    }

    public ChessBoard deepCopy() {
        ChessBoard copyBoard = new ChessBoard();

        for (int row=0; row<8; row++) {
            for (int col=0; col<8; col++) {
                ChessPiece currPiece = board[row][col];

                if (currPiece != null) {
                    copyBoard.addPiece(new ChessPosition(row-1,col-1), currPiece);
                }
            }
        }

        return copyBoard;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
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
                    rowStr += "           | ";
                } else {
                    rowStr += colVal.toString() + " | "; // fill pieces in row
                }
            }
            boardStr = rowStr + "\n" + boardStr; // add row above existing rows in board
        }

        return "Board:\n" + boardStr;
    }
}
