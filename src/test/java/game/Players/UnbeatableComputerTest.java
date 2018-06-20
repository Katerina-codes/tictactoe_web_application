package game.Players;

import game.Board;
import org.junit.Test;

import java.util.List;

import static game.Mark.*;
import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UnbeatableComputerTest {

    @Test
    public void computerPlaysNextBestMove() {
        UnbeatableComputer unbeatableComputer = new UnbeatableComputer(X);
        Board board = new Board(3, asList(
                EMPTY, X, O,
                O, X, X,
                EMPTY, EMPTY, O));

        assertThat(unbeatableComputer.playMove(board).grid.get(7), is(X));
    }

    @Test
    public void computerBlocksFork() {
        UnbeatableComputer unbeatableComputer = new UnbeatableComputer(O);
        List<Integer> possibleMoves = asList(1, 3, 5, 7);
        Board board = new Board(3, asList(
                X, EMPTY, EMPTY,
                EMPTY, O, EMPTY,
                EMPTY, EMPTY, X));
        int move = unbeatableComputer.findBestMove(board, 7, -10, +10, true).get(0);

        assertTrue(possibleMoves.contains(move));
    }

    @Test
    public void computerBlocksMove() {
        UnbeatableComputer unbeatableComputer = new UnbeatableComputer(O);
        Board board = new Board(3, asList(
                X, X, EMPTY,
                O, O, X,
                EMPTY, X, O));
        assertThat(unbeatableComputer.playMove(board).grid.get(2), is(O));
    }

    @Test
    public void blocksADifferentWin() {
        UnbeatableComputer unbeatableComputer = new UnbeatableComputer(O);
        Board board = new Board(3, asList(
                EMPTY, EMPTY, O,
                EMPTY, X, X,
                EMPTY, EMPTY, EMPTY));
        unbeatableComputer.findBestMove(board, 7, -10, +10, true);

        assertThat(unbeatableComputer.playMove(board).grid.get(3), is(O));
    }

    @Test
    public void computerTakesAWinOn4By4() {
        UnbeatableComputer unbeatableComputer = new UnbeatableComputer(X);
        Board board = new Board(4, asList(
                X, X, X, EMPTY,
                O, EMPTY, EMPTY, EMPTY,
                EMPTY, O, O, EMPTY,
                EMPTY, EMPTY, EMPTY, EMPTY));

        assertThat(unbeatableComputer.playMove(board).grid.get(3), is(X));
    }

    @Test
    public void blocksAWinOn4By4() {
        UnbeatableComputer unbeatableComputer = new UnbeatableComputer(O);
        Board board = new Board(4, asList(
                X, X, X, EMPTY,
                O, EMPTY, EMPTY, EMPTY,
                EMPTY, O, O, EMPTY,
                EMPTY, EMPTY, EMPTY, EMPTY));

        assertThat(unbeatableComputer.playMove(board).grid.get(3), is(O));
    }
}