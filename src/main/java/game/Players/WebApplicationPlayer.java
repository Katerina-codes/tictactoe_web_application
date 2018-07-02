package game.Players;

import game.Board;

public class WebApplicationPlayer implements Player {

    private boolean moveSet;
    private String currentMove;

    public void receiveMove(String moveNumber) {
        moveSet = true;
        currentMove = moveNumber;
    }

    public Board playMove(Board board) {
        moveSet = false;
        int move = Integer.parseInt(currentMove);
        return board.updateMove(move, board.getCurrentMark());
    }

    @Override
    public boolean hasMove() {
        return moveSet;
    }
}
