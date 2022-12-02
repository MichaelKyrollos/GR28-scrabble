import java.util.List;

public class ScrabbleGameStatus {
    private BoardModel board;
    private List<PlayerModel> players;
    private int currentTurn;
    private TileBag tileBag;

    public ScrabbleGameStatus(BoardModel board, List<PlayerModel> players, int currentTurn, TileBag tileBag) {
        this.board = board;
        this.players = players;
        this.currentTurn = currentTurn;
        this.tileBag = tileBag;
    }

    public BoardModel getBoard() {
        return board;
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public TileBag getTileBag() {
        return tileBag;
    }
}
