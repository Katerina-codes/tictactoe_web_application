import game.*;
import game.Players.WebApplicationPlayer;
import spark.Request;

import java.util.List;

import static spark.Spark.get;

public class Router implements UI {

    private Board board = new Board();
    private UI ui = this;
    private Game game = new Game(ui, board);


    public void run() {
        game.playerSetUp();
        get("/", (request, response) -> displayCurrentStateOfGame());
        get("/makeMove/:move", (request, response) -> makeMoveAndUpdateBoard(request));
    }

    private String makeMoveAndUpdateBoard(Request request) {
        String move = request.params("move");
        WebApplicationPlayer player = (WebApplicationPlayer) game.currentPlayer;
        player.receiveMove(move);
        game.run();
        return displayCurrentStateOfGame();
    }

    private String displayCurrentStateOfGame() {
        StringBuilder stringBuilder = new StringBuilder();
        buildGrid(stringBuilder);
        scoreGame(stringBuilder);
        return stringBuilder.toString();
    }

    private void buildGrid(StringBuilder stringBuilder) {
        for (int markPosition = 0; markPosition <= board.grid.size() - 1; markPosition++) {
            if (board.grid.get(markPosition).equals(Mark.EMPTY)) {
                stringBuilder.append(String.format("<a href='/makeMove/%s?currentBoard=123456789'> %s </a>", markPosition, (markPosition + 1)));
            } else {
                stringBuilder.append(String.format(" %s ", board.grid.get(markPosition).toString()));
            }
            formatGrid(stringBuilder, markPosition);
        }
    }

    private void scoreGame(StringBuilder stringBuilder) {
        if (board.gameIsOver()) {
            String finalResult = board.findResult().getResult();
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

    @Override
    public void askForGameMode() {

    }

    @Override
    public String getUserChoice() {
        return "8";
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
        displayCurrentStateOfGame();
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
        return 1;
    }

    @Override
    public void askForBoardSize() {

    }
}
