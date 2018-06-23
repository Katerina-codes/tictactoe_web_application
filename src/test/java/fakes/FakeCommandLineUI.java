package fakes;

import game.Board;
import game.Mark;
import game.Result;
import game.UI;

import java.util.ArrayList;
import java.util.List;

public class FakeCommandLineUI implements UI {

    private List<Boolean> userChoices;
    private List<Integer> moves;
    private boolean askForMoveWasCalled = false;
    private boolean getPlayerMoveWasCalled = false;
    private boolean displayBoardWasCalled = false;
    private boolean announceWinnerWasCalled = false;
    private boolean getGameModeWasCalled = false;
    private boolean askForGameModeWasCalled = false;
    private boolean replayCalled = false;

    public FakeCommandLineUI() {
    }

    public FakeCommandLineUI(List moves) {
        this.moves = new ArrayList(moves);
    }

    public FakeCommandLineUI(List moves, List userChoices) {
        this.moves = new ArrayList(moves);
        this.userChoices = new ArrayList(userChoices);
    }

    @Override
    public void askForGameMode() {
        this.askForGameModeWasCalled = true;
    }

    @Override
    public String getUserChoice() {
        this.getGameModeWasCalled = true;
        return "1";
    }

    @Override
    public void askForMove(Mark playerMark, List<Mark> boardSize) {
        this.askForMoveWasCalled = true;
    }

    @Override
    public String getMove(Board board) {
        this.getPlayerMoveWasCalled = true;
        return this.moves.remove(0).toString();
    }

    @Override
    public int getBoardSize() {
        return THREE_BY_THREE;
    }

    @Override
    public void askForBoardSize() {
    }

    @Override
    public void displayBoard(List<Mark> rows, int size) {
        this.displayBoardWasCalled = true;
    }

    @Override
    public void announceWinner(Result winner) {
        this.announceWinnerWasCalled = true;
    }

    public boolean getGameModeWasCalled() {
        return getGameModeWasCalled;
    }

    @Override
    public boolean replay() {
        this.replayCalled = true;
        return userChoices.remove(0);
    }

    public boolean askForGameModeWasCalled() {
        return askForGameModeWasCalled;
    }

    public boolean askForMoveWasCalled() {
        return askForMoveWasCalled;
    }

    public boolean getPlayerMoveWasCalled() {
        return getPlayerMoveWasCalled;
    }

    public boolean displayBoardWasCalled() {
        return displayBoardWasCalled;
    }

    public boolean announceWinnerWasCalled() {
        return announceWinnerWasCalled;
    }

    public boolean replayWasCalled() {
        return replayCalled;
    }
}
