package chess;

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
        // loop through board to get king and enemy positions
        ChessPosition kingPosition = board.getKingPosition(teamColor);
        LinkedHashSet<ChessPosition> enemyPositions = board.getTeamPositions(teamColor.opposite());

        // get potential moves of all enemy pieces
        for (ChessPosition pos : enemyPositions) {
            ChessPiece currPiece = board.getPiece(pos);
            Collection<ChessMove> potentialMoves = currPiece.pieceMoves(board, pos);

            // if potential move is king's position, king is in check
            for (ChessMove move : potentialMoves) {
                if (move.getEndPosition() == kingPosition) {
                    return true;
                }
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
