package game.Players;

import fakes.FakeCommandLineUI;
import game.Board;
import org.junit.Test;

import java.util.List;

import static game.Mark.X;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class HumanPlayerTest {

    @Test
    public void canPlayAMoveOnTheBoard() {
        List moves = asList(1);
        FakeCommandLineUI ui = new FakeCommandLineUI(moves);
        Board board = new Board(3);
        HumanPlayer player = new HumanPlayer(ui, X);

        Board updatedBoard = player.playMove(board);

        assertEquals(X, updatedBoard.valueAt(0));
    }
}
