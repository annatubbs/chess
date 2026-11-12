package chess;

import static ui.EscapeSequences.*;

public class TestMain {
    public static void main(String[] args) {

        try {
            ChessGame game = new ChessGame();
            ChessBoard og = game.getBoard();

            byte[] seriBoard = game.serializeBoard();
            ChessBoard restored = game.restoreSerializedBoard(seriBoard);

            System.out.println(og);
            System.out.println(restored);
        } catch (Exception e ) {
            System.out.println(e.getMessage());
        }
    }
}
