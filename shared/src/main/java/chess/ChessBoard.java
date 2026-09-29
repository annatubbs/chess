package chess;

import java.util.Arrays;
import java.util.LinkedHashSet;
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

    // create copy of board
    public ChessBoard deepCopy() {
        ChessBoard copyBoard = new ChessBoard();

        for (int row=1; row<=8; row++) {
            for (int col=1; col<=8; col++) {

                ChessPiece currPiece = board[row-1][col-1];
                // Check piece type at position
                if (currPiece == null) continue;
                copyBoard.addPiece(new ChessPosition(row, col), currPiece.deepCopy());
            }
        }

        return copyBoard;
    }

    // get positions of all a team's pieces
    public LinkedHashSet<ChessPosition> getTeamPositions(ChessGame.TeamColor color) {
        LinkedHashSet<ChessPosition> positions = new LinkedHashSet<>();

        for (int rowI=0; rowI<8; rowI++) {
            for (int colI=0; colI<8; colI++) {
                ChessPiece currPiece = board[rowI][colI];

                if (currPiece.getTeamColor() == color) { // if right color, add position
                    positions.add(new ChessPosition(rowI + 1, colI + 1));
                }
            }
        }
        return positions;
    }

    // get positions of all a team's pieces
    public ChessPosition getKingPosition(ChessGame.TeamColor color) {

        for (int rowI=0; rowI<8; rowI++) {
            for (int colI=0; colI<8; colI++) {
                ChessPiece currPiece = board[rowI][colI];

                if (!(currPiece.getTeamColor() == color)) continue; // if wrong color, continue to next position

                if (currPiece.getPieceType() == ChessPiece.PieceType.KING) { // if King, add and return
                    return new ChessPosition(rowI + 1, colI + 1);
                } // else if not King, continue searching
            }
        }
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
