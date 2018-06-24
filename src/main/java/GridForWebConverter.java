import game.Mark;

import java.util.ArrayList;
import java.util.List;

import static game.Mark.EMPTY;
import static game.Mark.O;
import static game.Mark.X;
import static java.util.Arrays.asList;

public class GridForWebConverter {

    public String createQueryValueForGridState(List<Mark> grid) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int markPosition = 0; markPosition < grid.size(); markPosition++) {
            if (grid.get(markPosition).equals(Mark.EMPTY)) {
                stringBuilder.append(String.format("%s", (markPosition + 1)));
            } else {
                stringBuilder.append(String.format("%s", grid.get(markPosition).toString()));
            }
        }
        return stringBuilder.toString();
    }

    public List<Mark> convertToGridOfMarks(String grid) {
        List<String> splitString = asList(grid.split(""));
        List<Mark> convertedString = new ArrayList<>();
        for (int i = 0; i < splitString.size(); i++) {
            if (splitString.get(i).equals("X")) {
                convertedString.add(i, X);
            } else if (splitString.get(i).equals("O")) {
                convertedString.add(i, O);
            } else {
                convertedString.add(i, EMPTY);
            }
        }
        return convertedString;
    }
}
