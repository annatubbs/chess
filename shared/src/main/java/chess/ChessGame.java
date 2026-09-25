package chess;

import java.util.Collection;

/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    ChessBoard board;

    public ChessGame() {
        teamTurn = TeamColor.WHITE;

        board = new ChessBoard();
        board.resetBoard();
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
        // check if there is a piece at position
        if (piece == null) return null;

        // get potential pieceMoves()
        Collection<ChessMove> possibleMoves = piece.pieceMoves(board, startPosition);

        // see if team's king in check
        Collection<ChessMove> validMoves = possibleMoves;

        // loop through possibleMove positions
        // create board for each position
        // check if king in check (function separate)

        return validMoves;
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");

        // loop through all spaces on board
        // if piece. if other team color. save pos
        // if piece. if king this color. save pos diff location
        // After. if pieceMoves has king's position, return true
        // return false.
        // * what about pawns far away? no chance. faster check row+col val?


    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
        // if king in check

        // need get all other team pieces that could kill king (w/o) curr team in way (target paths/xray)
        // get all curr team pieces in way + king
        // get their pieceMoves
        // if

        // BRUTE STRAT:
        // for all this team pieces. get piece moves
        // for pieceMove. generate board with that move
        // if not isCheck() return false
        // end of loop, return true.
    }


    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");

        // if not isCheck (if in check, return false?? Or exception)

        // get all positions of pieces of team.
        // for teamPiece. if (validMoves() != null) return false
        // after. return true.
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
