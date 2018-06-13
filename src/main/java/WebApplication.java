import static spark.Spark.get;

public class WebApplication {

    private Integer gridSize = 9;

    public void run() {
        get("/", (req, res) -> createGrid());
    }

    private String createGrid() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 1; i <= gridSize; i++) {
            stringBuilder.append(String.format("<button> %s </button>", i));
        }
        return stringBuilder.toString();
    }
}
