package game.Players;

import game.Board;
import org.junit.Test;

import java.util.List;

import static game.Mark.O;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;

public class ComputerTest {

    @Test
    public void playsARandomMove() {
        Computer computer = new Computer(O);
        Board board = new Board(3);
        List<Integer> possibleMoves = asList(0, 1, 2, 3, 4, 5, 6, 7, 8);

        int move = computer.move(board);

        assertTrue(possibleMoves.contains(move));
    }
}
