package game;

import java.util.List;

public class Line {
    private List<Mark> spaces;

    public Line(List<Mark> spaces) {
        this.spaces = spaces;
    }

    public boolean hasWinner(Mark mark) {
        return spaces.stream().filter(space -> space != Mark.EMPTY && space == mark).count() == spaces.size();
    }

    @Override
    public boolean equals(Object line) {
        Line otherLine = (Line) line;
        return this.spaces.equals(otherLine.spaces);
    }

}
