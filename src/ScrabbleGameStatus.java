import java.util.List;

/**
 * Class to store that status of a Scrabble Game.
 *
 * @author Yehan De Silva
 * @version 4.0
 * @date December 05, 2022
 */
public class ScrabbleGameStatus {

    private BoardModel board;
    private List<PlayerModel> players;
    private int currentTurn;
    private TileBag tileBag;

    /**
     * Constructs a ScrabbleGameStatus object.
     * @param board Status of the board.
     * @param players Status of the players.
     * @param currentTurn Status of teh current turn.
     * @param tileBag Status of the tile bag.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 05, 2022
     */
    public ScrabbleGameStatus(BoardModel board, List<PlayerModel> players, int currentTurn, TileBag tileBag) {
        this.board = board;
        this.players = players;
        this.currentTurn = currentTurn;
        this.tileBag = tileBag;
    }

    /**
     * Gets the status of the board.
     * @return Status of the board.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 05, 2022
     */
    public BoardModel getBoard() {
        return board;
    }

    /**
     * Gets the status of the players.
     * @return Status of the players.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 05, 2022
     */
    public List<PlayerModel> getPlayers() {
        return players;
    }

    /**
     * Gets the status of the current turn.
     * @return Status of the current turn.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 05, 2022
     */
    public int getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Gets the status of the tile bag.
     * @return Status of the tile bag.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 05, 2022
     */
    public TileBag getTileBag() {
        return tileBag;
    }
}
