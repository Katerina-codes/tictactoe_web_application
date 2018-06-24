import game.Board;
import org.junit.Test;

import static game.Mark.EMPTY;
import static game.Mark.O;
import static game.Mark.X;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class GridForWebConverterTest {

    @Test
    public void itFormatsQueryStringFromEmptyGridState() {
        GridForWebConverter converter = new GridForWebConverter();
        Board board = new Board();
        board.grid = asList(EMPTY, EMPTY, EMPTY);

        assertEquals("123", converter.createQueryValueForGridState(board.grid));
    }

    @Test
    public void itFormatsQueryStringFromGridStateWithMarks() {
        GridForWebConverter converter = new GridForWebConverter();
        Board board = new Board();
        board.grid = asList(EMPTY, X, O);

        assertEquals("1XO", converter.createQueryValueForGridState(board.grid));
    }

    @Test
    public void createsNewBoardFromAString() {
        GridForWebConverter converter = new GridForWebConverter();

        assertEquals(asList(EMPTY, EMPTY, X), converter.convertToGridOfMarks("12X"));
    }
}
