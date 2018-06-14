import game.Board;
import game.Mark;
import static spark.Spark.get;

public class WebApplication {

    private Integer gridSize = 3;
    private Board board = new Board();

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
            board.updateMove(Integer.parseInt(move), Mark.X);
            return createGrid(); });
    }
}
