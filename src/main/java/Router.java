import game.*;
import game.Players.PlayerFactory;
import game.Players.WebApplicationPlayer;
import spark.Request;

import java.util.List;

import static spark.Spark.get;

public class Router implements UI {

    private game.GridForWebConverter converter = new game.GridForWebConverter();

    public void run() {
        get("/", (request, response) -> displayCurrentStateOfGame(new Board()));
        get("/newGame/:gameMode", (request, response) -> gameMode(request));
        get("/makeMove/:move", (request, response) -> makeMoveAndUpdateBoard(request));
    }

    private String gameMode(Request request) {
        return request.queryParams("gameMode");
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
        String responseBody = buildResponse(board);
        return updateUIWithResult(responseBody, board);
    }

    private String buildResponse(Board board) {
        String responseBody = "";
        responseBody += "<a href='/newGame/8?gameMode=8'> Human Vs Human </a></br>" +
                "<a href='/newGame/9?gameMode=9'> Human Vs Unbeatable player </a><br>";
        String convertedBoard = converter.createQueryValueForGridState(board.grid);

        for (int markPosition = 0; markPosition <= board.grid.size() - 1; markPosition++) {
            if (board.grid.get(markPosition).equals(Mark.EMPTY)) {
                responseBody += String.format("<a href='/makeMove/%s?move=%s&currentBoard=%s'> %s </a>", markPosition, markPosition, convertedBoard, (markPosition + 1));
            } else {
                responseBody += String.format(" %s ", board.grid.get(markPosition).toString());
            }
            responseBody = formatGrid(responseBody, markPosition);
        }
        return responseBody;
    }

    private String updateUIWithResult(String responseBody, Board board) {
        if (board.gameIsOver()) {
            String finalResult = board.findResult().getResult();
            return announceResult(responseBody, finalResult);
        }
        return responseBody;
    }

    private String announceResult(String responseBody, String finalResult) {
        if (finalResult.equals("Tie")) {
            return responseBody + "<br><br> It's a tie!<br>";
        } else {
            return responseBody + String.format("<br><br> %s wins! <br>", finalResult);
        }
    }

    private String formatGrid(String responseBody, int markPosition) {
        int gridSize = 3;
        if ((markPosition + 1) % gridSize == 0) {
            responseBody += "<br>";
        }
        return responseBody;
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
