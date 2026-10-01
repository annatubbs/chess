package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    private ChessBoard board;
    private ArrayList<ChessBoard> boardHistory;

    public ChessGame() {
        teamTurn = TeamColor.WHITE;

        board = new ChessBoard();
        board.resetBoard();

        boardHistory = new ArrayList<>();
        boardHistory.add(board.deepCopy());
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Sets which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) { teamTurn = team;}

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE("White"),
        BLACK("Black");

        private final String label;

        TeamColor (String label) {
            this.label = label;
        }

        // return opposite team color
        public TeamColor opposite() { return this == WHITE ? BLACK : WHITE;}

        @Override
        public String toString() {
            return label;
        }
    }

    // Functions:
    // get team positions (get board positions of all players of one color )


    /**
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {

        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null) return null; // if no piece there, no valid moves

        // get potential pieceMoves()
        Collection<ChessMove> possibleMoves = piece.pieceMoves(board, startPosition);
        TeamColor color = piece.getTeamColor();

        // see if moves put king in check
        // because board isInCheck can only use board of curr chessGame, have to change board
        ChessBoard ogBoard = board.deepCopy();
        Collection<ChessMove> validMoves = new ArrayList<>();

        for (ChessMove move: possibleMoves) {
            try {
                board = board.movePiece(move); // set board to that move
            } catch (InvalidMoveException e ) {
                System.out.println(e);
            }

            if (!isInCheck(color)) { // check if move puts king in check
                validMoves.add(move);
            }

            board = (ogBoard.deepCopy()); // reset board to original board
        }

        return validMoves;
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        board = board.movePiece(move); // throws InvalidMoveException
        boardHistory.add(board.deepCopy());
        teamTurn = teamTurn.opposite();
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // get all enemy team positions
        LinkedHashSet<ChessPosition> enemyPositions = board.getTeamPositions(teamColor.opposite());

        for (ChessPosition pos : enemyPositions) {
            ChessPiece currPiece = board.getPiece(pos);

            Collection<ChessMove> potentialMoves = currPiece.pieceMoves(board, pos); // get potential moves of all enemy pieces
            for (ChessMove move : potentialMoves) {
                if (move.getIsCheck()) return true; // if move captures king, is in check
            }
        }
        // king safe
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {

        if (!isInCheck(teamColor)) return false; // if king not in check, not checkmate

        // check all team piece's moves to see if one can rescue king
        LinkedHashSet<ChessPosition> teamPositions = board.getTeamPositions(teamColor);

        for (ChessPosition piecePosition : teamPositions) {
            Collection<ChessMove> validPieceMoves = validMoves(piecePosition);

            if (!validPieceMoves.isEmpty()) return false; // if any valid moves, not checkmate
        }

        return true; // if no valid moves, is checkmate
    }


    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {

        if (isInCheck(teamColor)) return false; // if king in check, not stalemate

        // check all team piece's moves to see if any valid moves
        LinkedHashSet<ChessPosition> teamPositions = board.getTeamPositions(teamColor);

        for (ChessPosition piecePosition : teamPositions) {
            Collection<ChessMove> validPieceMoves = validMoves(piecePosition);

            if (!validPieceMoves.isEmpty()) return false; // if valid moves, not stalemate
        }

        return true; // if no valid moves, is stalemate
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard updateBoard) { board = updateBoard;}

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() { return board.deepCopy();}
}
