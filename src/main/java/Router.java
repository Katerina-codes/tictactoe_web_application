import game.*;
import game.GridForWebConverter;
import game.Players.PlayerFactory;
import game.Players.WebApplicationPlayer;
import spark.Request;

import java.util.List;

import static spark.Spark.get;

public class Router implements UI {

    private GridForWebConverter converter = new GridForWebConverter();

    public void run() {
        get("/", (request, response) -> getResponseBody(new Board(), ""));
        get("/newGame/:gameMode", (request, response) -> gameMode(request));
        get("/makeMove/:move", (request, response) -> makeMoveAndUpdateBoard(request));
    }

    private String gameMode(Request request) {
        String gameMode = request.queryParams("gameMode");
        return getResponseBody(new Board(), gameMode);
    }

    private String makeMoveAndUpdateBoard(Request request) {
        String gameMode = request.queryParams("gameMode");
        String move = request.queryParams("move");
        String currentBoard = request.queryParams("currentBoard");
        Board board = new Board(3, converter.convertToGridOfMarks(currentBoard));
        Game game = setUpNewGame(gameMode, move, board);
        game.run();
        return getResponseBody(board, gameMode);
    }

    private Game setUpNewGame(String gameMode, String move, Board board) {
        Game game = new Game(this, board);
        game.setPlayers(gameMode, new PlayerFactory(this));
        sendMoveToWebPlayer(move, game);
        return game;
    }

    private void sendMoveToWebPlayer(String move, Game game) {
        WebApplicationPlayer player = (WebApplicationPlayer) game.currentPlayer;
        player.receiveMove(move);
    }

    private String getResponseBody(Board board, String gameMode) {
        String responseBody = buildResponse(board, gameMode);
        return updateUIWithResult(responseBody, board);
    }

    private String buildResponse(Board board, String gameMode) {
        String responseBody = "";
        responseBody += "<a href='/newGame/8?gameMode=8'> Human Vs Human </a></br>" +
                "<a href='/newGame/9?gameMode=9'> Human Vs Unbeatable player </a><br>";
        responseBody = buildGrid(board, responseBody, gameMode);
        return responseBody;
    }

    private String buildGrid(Board board, String responseBody, String gameMode) {
        String convertedBoard = converter.convertBoardToString(board.grid);
        responseBody = createAndFormatGrid(board, responseBody, gameMode, convertedBoard);
        return responseBody;
    }

    private String createAndFormatGrid(Board board, String responseBody, String gameMode, String convertedBoard) {
        for (int cell = 0; cell <= board.grid.size() - 1; cell++) {
            if (board.grid.get(cell).equals(Mark.EMPTY)) {
                responseBody += String.format("<a href='/makeMove/%s?move=%s&currentBoard=%s&gameMode=%s'> %s </a>", cell, cell, convertedBoard, gameMode, (cell + 1));
            } else {
                responseBody += String.format(" %s ", board.grid.get(cell).toString());
            }
            responseBody = formatGrid(responseBody, cell);
        }
        return responseBody;
    }

    private String formatGrid(String responseBody, int markPosition) {
        int gridSize = 3;
        if ((markPosition + 1) % gridSize == 0) {
            responseBody += "<br>";
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

    @Override
    public void askForGameMode() {

    }

    @Override
    public String getUserChoice() {
        return "";
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
