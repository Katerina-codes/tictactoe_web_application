import game.*;
import game.Players.HumanPlayer;
import game.Players.Player;

import java.util.List;

import static spark.Spark.get;

public class WebApplication implements UI {

    private Board board = new Board();
    private UI ui = this;
    private Player playerOne = new HumanPlayer(ui, Mark.X);
    private Player playerTwo = new HumanPlayer(ui, Mark.O);
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

            Integer gridSize = 3;
            if ((i + 1) % gridSize == 0) {
                stringBuilder.append("<br>");
            }
        }

        if (board.gameIsOver()) {
            Result gameResult = board.findWinner();
            String finalResult = gameResult.getResult();
            if (finalResult.equals("Tie")) {
                stringBuilder.append("<br><br> It's a tie!<br>");
            } else {
                stringBuilder.append(String.format("<br><br> %s wins! <br>", finalResult));
            }
        }

        return stringBuilder.toString();
    }

    private void getMoveAndUpdateBoard() {
            get("/hello/:move", (request, response) -> {
                String move = request.params("move");
                board.updateMove(Integer.parseInt(move), currentPlayer.getMark());
                switchPlayer(playerOne, playerTwo);
                Result winner = Result.PLAYER_ONE_WIN;
                announceWinner(winner);
                return createGrid();
            });
    }


    private void switchPlayer(Player playerOne, Player playerTwo) {
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
