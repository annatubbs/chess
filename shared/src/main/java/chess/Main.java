package chess;

public class Main {
    public static void main(String[] args) {
//        ChessBoard board = new ChessBoard();
//        board.resetBoard();
        ChessGame game = new ChessGame();
        ChessBoard board = game.getBoard();

        System.out.println(board);
    }
}

