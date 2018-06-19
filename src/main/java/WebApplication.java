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
        get("/", (request, response) -> displayCurrentStateOfGame());
        getMoveAndUpdateBoard();
    }

    private String displayCurrentStateOfGame() {
        StringBuilder stringBuilder = new StringBuilder();
        buildGrid(stringBuilder);
        endAndScoreGame(stringBuilder);
        return stringBuilder.toString();
    }

    private void buildGrid(StringBuilder stringBuilder) {
        for (int i = 0; i <= board.grid.size() - 1; i++) {
            if (board.grid.get(i).equals(Mark.EMPTY)) {
                stringBuilder.append(String.format("<a href='/hello/%s'> %s </a>", i, (i + 1)));
            } else {
                stringBuilder.append(String.format(" %s ", board.grid.get(i).toString()));
            }
            formatGrid(stringBuilder, i);
        }
    }

    private void endAndScoreGame(StringBuilder stringBuilder) {
        if (board.gameIsOver()) {
            String finalResult = board.findWinner().getResult();
            if (finalResult.equals("Tie")) {
                stringBuilder.append("<br><br> It's a tie!<br>");
            } else {
                stringBuilder.append(String.format("<br><br> %s wins! <br>", finalResult));
            }
        }
    }

    private void formatGrid(StringBuilder stringBuilder, int i) {
        Integer gridSize = 3;
        if ((i + 1) % gridSize == 0) {
            stringBuilder.append("<br>");
        }
    }

    private void getMoveAndUpdateBoard() {
            get("/hello/:move", (request, response) -> {
                String move = request.params("move");
                board.updateMove(Integer.parseInt(move), currentPlayer.getMark());
                switchPlayer(playerOne, playerTwo);
                return displayCurrentStateOfGame();
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
