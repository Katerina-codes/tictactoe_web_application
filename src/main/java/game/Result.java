package game;

public enum Result {
    PLAYER_ONE_WIN("X"),
    PLAYER_TWO_WIN("O"),
    TIE("Tie");

    private final String result;

    Result(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}