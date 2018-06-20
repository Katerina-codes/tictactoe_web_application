package game;

import fakes.FakeCommandLineUI;
import org.junit.Test;

import java.util.List;

import static game.Mark.EMPTY;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {

    @Test
    public void runsTheGame() {
        List moves = asList(1, 4, 2, 5, 3);
        List userChoices = asList(false);
        FakeCommandLineUI inputOutput = new FakeCommandLineUI(moves, userChoices);
        Board board = new Board(3);
        Game game = new Game(inputOutput, board);

        game.playerSetUp();
        game.run();

        assertTrue(inputOutput.askForGameModeWasCalled());
        assertTrue(inputOutput.getGameModeWasCalled());
        assertTrue(inputOutput.displayBoardWasCalled());
        assertTrue(inputOutput.askForMoveWasCalled());
        assertTrue(inputOutput.getPlayerMoveWasCalled());
    }

    @Test
    public void playsTheGame() {
        List moves = asList(1, 4, 2, 5, 3);
        List userChoices = asList(false);
        UI inputOutput = new FakeCommandLineUI(moves, userChoices);
        Board board = new Board(3);
        Game game = new Game(inputOutput, board);

        game.playerSetUp();
        game.run();

        assertEquals(asList(Mark.X, Mark.X, Mark.X, Mark.O, Mark.O, EMPTY, EMPTY, EMPTY, EMPTY), board.grid);
    }
}