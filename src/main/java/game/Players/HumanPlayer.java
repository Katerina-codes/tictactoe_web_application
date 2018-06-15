package game.Players;

import game.Board;
import game.Mark;
import game.UI;

public class HumanPlayer implements Player {

    private final UI ui;
    private final Mark mark;

    public HumanPlayer(UI ui, Mark mark) {
        this.ui = ui;
        this.mark = mark;
    }

    public Board playMove(Board board) {
        int playerMove = getMove(board);
        return board.updateMove(playerMove, this.mark);
    }

    public Mark getMark() {
        return mark;
    }

    private Integer getMove(Board board) {
        String userMove = ui.getMove(board);
        return Integer.parseInt(String.valueOf(userMove)) - 1;
    }
}
