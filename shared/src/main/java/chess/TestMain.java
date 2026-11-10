package chess;

public class TestMain {
    public static void main(String[] args) {
//        ChessPiece king = new King(ChessGame.TeamColor.BLACK);
//        ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
//
//        System.out.println(king);
//        System.out.println(piece);

        ChessBoard board = new ChessBoard();
        board.resetBoard();
        System.out.println(board);
    }
}
