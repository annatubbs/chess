package chess;

import java.io.*;
import java.util.*;


/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard implements Serializable {
    private ChessPiece[][] board;

    public ChessBoard() {
        board = new ChessPiece[8][8];
    }

    // create copy of board
    public ChessBoard deepCopy() {
        ChessBoard copyBoard = new ChessBoard();

        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {

                ChessPiece currPiece = board[row - 1][col - 1];
                // Check piece type at position
                if (currPiece == null) {
                    continue;
                }
                copyBoard.addPiece(new ChessPosition(row, col), currPiece.deepCopy());
            }
        }

        return copyBoard;
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow() - 1][position.getColumn() - 1] = piece; // calc for 0-based index
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow() - 1][position.getColumn() - 1]; // should return piece or null...?
    }

    private void removePiece(ChessPosition position) {
        board[position.getRow() - 1][position.getColumn() - 1] = null;
    }

    public ChessBoard movePiece(ChessMove move) throws InvalidMoveException {
        ChessBoard updateBoard = this.deepCopy();

        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece.PieceType promotionPiece = move.getPromotionPiece();
        ChessPiece piece = updateBoard.getPiece(startPosition);

        updateBoard.removePiece(startPosition);

        if (promotionPiece == null) { // not promoted
            updateBoard.addPiece(endPosition, piece);
        } else { // piece promoted
            updateBoard.addPiece(endPosition, new ChessPiece(piece.getTeamColor(), promotionPiece));
        }

        return updateBoard;
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    // return back row of pieces at starting positions
    private ChessPiece[] resetBackRowWhite() {
        ChessGame.TeamColor color = ChessGame.TeamColor.WHITE;
        return new ChessPiece[]{new Rook(color), new Knight(color), new Bishop(color), new Queen(color),
                new King(color), new Bishop(color), new Knight(color), new Rook(color)};

    }

    private ChessPiece[] resetBackRowBlack() {
        ChessGame.TeamColor color = ChessGame.TeamColor.BLACK;
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
        startBoard[7] = resetBackRowBlack();
        startBoard[6] = resetFrontRow(ChessGame.TeamColor.BLACK);

        // Set white pieces
        startBoard[0] = resetBackRowWhite();
        startBoard[1] = resetFrontRow(ChessGame.TeamColor.WHITE);

        board = startBoard;
    }

    // get positions of all a team's pieces
    public LinkedHashSet<ChessPosition> getTeamPositions(ChessGame.TeamColor color) {
        LinkedHashSet<ChessPosition> positions = new LinkedHashSet<>();

        for (int rowI = 0; rowI < 8; rowI++) {
            for (int colI = 0; colI < 8; colI++) {
                ChessPiece currPiece = board[rowI][colI];

                if (currPiece != null && currPiece.getTeamColor() == color) { // if not null and right color, add position
                    positions.add(new ChessPosition(rowI + 1, colI + 1));
                }
            }
        }
        return positions;
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

    public ChessPiece[][] getBoardArray() {
        return board;
    }
}
