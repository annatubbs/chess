package chess;

public class Main {
    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        ChessBoard board = game.getBoard();

        System.out.println(board);
    }
}

