import game.*;
import game.Players.WebApplicationPlayer;
import spark.Request;

import java.util.List;

import static spark.Spark.get;

public class Router implements UI {

    private game.GridForWebConverter converter = new game.GridForWebConverter();

    public void run() {
        get("/", (request, response) -> displayCurrentStateOfGame(new Board()));
        get("/makeMove/:move", (request, response) -> makeMoveAndUpdateBoard(request));
    }

    private String makeMoveAndUpdateBoard(Request request) {
        String move = request.queryParams("move");
        String currentBoard = request.queryParams("currentBoard");
        Board board = new Board(3, converter.convertToGridOfMarks(currentBoard));
        Game newGame = new Game(this, board);
        newGame.playerSetUp();
        WebApplicationPlayer player = (WebApplicationPlayer) newGame.currentPlayer;
        player.receiveMove(move);
        newGame.run();
        return displayCurrentStateOfGame(board);
    }

    private String displayCurrentStateOfGame(Board board) {
        StringBuilder stringBuilder = new StringBuilder();
        buildGrid(stringBuilder, board);
        scoreGame(stringBuilder, board);
        return stringBuilder.toString();
    }

    private void buildGrid(StringBuilder stringBuilder, Board board) {
        String convertedBoard = converter.createQueryValueForGridState(board.grid);
        for (int markPosition = 0; markPosition <= board.grid.size() - 1; markPosition++) {
            if (board.grid.get(markPosition).equals(Mark.EMPTY)) {
                stringBuilder.append(String.format("<a href='/makeMove/%s?move=%s&currentBoard=%s'> %s </a>", markPosition, markPosition, convertedBoard, (markPosition + 1)));
            } else {
                stringBuilder.append(String.format(" %s ", board.grid.get(markPosition).toString()));
            }
            formatGrid(stringBuilder, markPosition);
        }
    }

    private void scoreGame(StringBuilder stringBuilder, Board board) {
        if (board.gameIsOver()) {
            String finalResult = board.findResult().getResult();
            announceResult(stringBuilder, finalResult);
        }
    }

    private void announceResult(StringBuilder stringBuilder, String finalResult) {
        if (finalResult.equals("Tie")) {
            stringBuilder.append("<br><br> It's a tie!<br>");
        } else {
            stringBuilder.append(String.format("<br><br> %s wins! <br>", finalResult));
        }
    }

    private void formatGrid(StringBuilder stringBuilder, int markPosition) {
        int gridSize = 3;
        if ((markPosition + 1) % gridSize == 0) {
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
