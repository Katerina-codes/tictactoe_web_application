package game.Players;

import game.Board;
import game.Mark;

public class WebApplicationPlayer implements Player {

    private boolean moveSet;
    private String currentMove;
    private Mark mark;

    public WebApplicationPlayer(Mark mark) {
        this.mark = mark;
    }

    public void receiveMove(String moveNumber) {
        moveSet = true;
        currentMove = moveNumber;
    }

    public Board playMove(Board board) {
        moveSet = false;
        int move = Integer.parseInt(currentMove);
        return board.updateMove(move, mark);
    }

    @Override
    public Mark getMark() {
        return mark;
    }

    @Override
    public boolean hasMove() {
        return moveSet;
    }
}
