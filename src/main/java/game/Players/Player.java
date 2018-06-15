package game.Players;

import game.Board;
import game.Mark;

public interface Player {
    Board playMove(Board board);

    Mark getMark();
}
