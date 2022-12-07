import java.io.Serializable;
import java.util.List;

/**
 * Record to store that status of a Scrabble Game.
 * The status includes the board, list of players, current turn and tileBag.
 *
 * @author Yehan De Silva, 101185388
 * @version 4.0
 * @date December 05, 2022
 */
public record ScrabbleGameStatus(BoardModel board, List<PlayerModel> players, int currentTurn,
                                 TileBag tileBag) implements Serializable {
}
