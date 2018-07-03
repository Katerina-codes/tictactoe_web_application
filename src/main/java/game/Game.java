package game;

import game.Players.Player;
import game.Players.PlayerFactory;

import java.util.List;

public class Game {

    private final UI ui;
    private Board board;
    public Player currentPlayer;
    public Player playerOne;
    public Player playerTwo;

    public Game(UI ui, Board board) {
        this.ui = ui;
        this.board = board;
    }

    public void run() {
        displayBoard();
        while (gameIsNotOver() && currentPlayer.hasMove()) {
            playMove();
            displayBoard();
            switchPlayer(playerOne, playerTwo);
        }
    }

    public void playerSetUp() {
        ui.askForGameMode();
        String gameMode = ui.getUserChoice();
        PlayerFactory playerTypes = new PlayerFactory(ui);
        setPlayers(gameMode, playerTypes);
    }

    public void switchPlayer(Player playerOne, Player playerTwo) {
        if (currentPlayer == playerOne) {
            currentPlayer = playerTwo;
        } else {
            currentPlayer = playerOne;
        }
    }

    public void setPlayers(String gameMode, PlayerFactory playerTypes) {
        List<Player> players = playerTypes.getPlayerTypes(gameMode);
        playerOne = players.get(0);
        playerTwo = players.get(1);
        currentPlayer = playerOne;
    }

    public Player getCurrentPlayer() {
        if (board.getCurrentMark().equals(Mark.X)) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }

    private void playMove() {
        ui.askForMove(this.board.getCurrentMark(), this.board.grid);
        board = currentPlayer.playMove(board);
    }

    private boolean gameIsNotOver() {
        return !this.board.gameIsOver();
    }

    private void displayBoard() {
        ui.displayBoard(this.board.grid, this.board.size);
    }

}
