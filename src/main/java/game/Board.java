package game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static game.Mark.*;
import static game.Result.TIE;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class Board {

    public int size;
    public List<Mark> grid;

    public Board() {
        this(3);
    }

    public Board(int size) {
        this.size = size;
        this.grid = createGrid();
    }

    public Board(int size, List<Mark> grid) {
        this.size = size;
        this.grid = grid;
    }

    public List<Mark> createGrid() {
        return Stream.generate(() -> EMPTY).limit(size * size).collect(toList());
    }

    public Board updateMove(int position, Mark mark) {
        this.grid.set(position, mark);
        return new Board(size, this.grid);
    }

    public boolean isMoveAvailable(int move) {
        return this.grid.get(move - 1) == EMPTY;
    }

    public boolean hasAvailableMoves() {
        return this.grid.stream().anyMatch(space -> space.equals(EMPTY));
    }

    public List<Integer> availableMoves() {
        return range(0, this.grid.size()).filter(index -> this.grid.get(index).equals(EMPTY)).boxed().collect(toList());
    }

    public Result findWinner() {
        if (gameIsTied()) {
            return TIE;
        } else if (playerHasWon(X)) {
            return Result.PLAYER_ONE_WIN;
        } else if (playerHasWon(O)) {
            return Result.PLAYER_TWO_WIN;
        } else {
            throw new RuntimeException("No winner.");
        }

    }

    public boolean playerHasWon(Mark mark) {
        return lines().stream().anyMatch(line -> line.hasWinner(mark));
    }

    public boolean gameIsOver() {
        return playerHasWon(X) || playerHasWon(O) || !hasAvailableMoves();
    }

    public Mark valueAt(int position) {
        return this.grid.get(position);
    }

    private List<Line> lines() {
        List<Line> lines = new ArrayList<>();
        lines.addAll(diagonalLines());
        lines.addAll(columnLines());
        lines.addAll(rowLines());
        return lines;
    }

    private List<Line> rowLines() {
        List<Line> rows = new ArrayList<>();
        for (int i = 0; i < grid.size(); i += size) {

            ArrayList<Mark> rowElements = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                rowElements.add(grid.get(i + j));
            }
            rows.add(new Line(rowElements));
        }
        return rows;
    }

    private Line diagonalOne() {
        List<Integer> diagonalOne = range(0, grid.size()).filter(index -> index % (size + 1) == 0).boxed().collect(toList());
        List<Mark> diagonalElements = diagonalOne.stream().map(element -> this.grid.get(element)).collect(toList());
        return new Line(diagonalElements);
    }

    private Line diagonalTwo() {
        List<Integer> diagonal = range(size - 1, grid.size() - (size - 1)).filter(index -> index % (size - 1) == 0).boxed().collect(toList());
        List<Mark> diagonalElements = diagonal.stream().map(element -> this.grid.get(element)).collect(toList());
        return new Line(diagonalElements);
    }

    private List<Line> diagonalLines() {
        List<Line> diagonals = new ArrayList<>();
        diagonals.add(diagonalOne());
        diagonals.add(diagonalTwo());
        return diagonals;
    }

    private List<Line> columnLines() {
        List<Line> columns = new ArrayList<>();

        IntStream.range(0, size).forEach(i -> columnElements(i, columns));
        return columns;
    }

    private void columnElements(int i, List<Line> columnElements) {
        List<Integer> indicesForElements = IntStream.range(i, grid.size()).filter(index -> (index - i) % size == 0).boxed().collect(toList());
        List<Mark> marks = indicesForElements.stream().map(index -> this.grid.get(index)).collect(toList());
        columnElements.add(new Line(marks));
    }

    private boolean gameIsTied() {
        return !playerHasWon(X) && !playerHasWon(O) && !hasAvailableMoves();
    }
}
