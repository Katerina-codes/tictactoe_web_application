import game.Board;
import game.Mark;
import game.Players.HumanPlayer;
import game.Players.Player;
import game.Result;
import game.UI;

import java.util.List;

import static spark.Spark.get;

public class WebApplication implements UI {

    private Integer gridSize = 3;
    private Board board = new Board();
    Player playerOne = new HumanPlayer(this, Mark.X);
    Player playerTwo = new HumanPlayer(this, Mark.O);
    private Player currentPlayer = playerOne;


    public void run() {
        get("/", (request, response) -> createGrid());
        getMoveAndUpdateBoard();
    }

    private String createGrid() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i <= board.grid.size() - 1; i++) {

            if (board.grid.get(i).equals(Mark.EMPTY)) {
                stringBuilder.append(String.format("<a href='/hello/%s'> %s </a>", i, (i + 1)));
            } else {
                stringBuilder.append(String.format(" %s ", board.grid.get(i).toString()));
            }

            if ((i + 1) % gridSize == 0) {
                stringBuilder.append("<br>");
            }
        }
        return stringBuilder.toString();
    }

    private void getMoveAndUpdateBoard() {
        get("/hello/:move", (request, response) -> {
            String move = request.params("move");
            board.updateMove(Integer.parseInt(move), currentPlayer.getMark());
            switchPlayer(playerOne, playerTwo);
            return createGrid();
        });
    }


    private void switchPlayer(Player playerOne, Player playerTwo) {
        System.out.println("switch player have been called");
        if (currentPlayer == playerOne) {
            currentPlayer = playerTwo;
        } else {
            currentPlayer = playerOne;
        }
    }

    @Override
    public void askForGameMode() {

    }

    @Override
    public String getUserChoice() {
        return null;
    }

    @Override
    public void askForMove(Mark playerMark, List<Mark> boardSize) {

    }

    @Override
    public String getMove(Board board) {
        return null;
    }

    @Override
    public void displayBoard(List<Mark> rows, int size) {

    }

    @Override
    public void announceWinner(Result winner) {

    }

    @Override
    public boolean replay() {
        return false;
    }

    @Override
    public int getBoardSize() {
        return 0;
    }

    @Override
    public void askForBoardSize() {

    }
}
