// Import libraries
import java.util.ArrayList;

/**
 * The ScrabblePlayer interface models a player in the Scrabble game.
 * A player in the Scrabble game can either be a regular (human) player,
 * or an AI player.
 * A player can place a word on the board, redraw from the tile bag, play
 * a tile on a square, and adjust their score.
 *
 * @author Pathum Danthanarayana, 101181411
 * @version 1.0
 * @date November 19th, 2022
 */
public interface ScrabblePlayer
{
    /**
     * Plays the given word entered by the user at the given coordinates. Returns true if the word was successfully
     * placed (i.e. the user had the necessary tiles and the placement of the word on the board was valid)
     * Words are entered as strings with pre-placed letters enclosed in (). For example, placing "h(e)ll(o)" states that
     * the "e" and "o" are already on the board in the correct location, thus the player doesn't need to have an e or o
     * tile to play this word.
     *
     * @author Amin Zeina, 101186297
     * @author Yehan De Silva, 101185388
     * @version 2.1
     * @date November 12, 2022
     *
     * @param playEvent the PlayWordEvent that was generated to play this word
     * @return true if the word was successfully placed, false otherwise
     */
    boolean playWord(PlayWordEvent playEvent);

    /**
     * Redraws the given tiles from the player's rack.
     * @param redrawTiles Tiles to be redrawn.
     *
     * @author Yehan De Silva, 101185388
     * @version 1.0
     * @date November 13, 2022
     */
    void redraw(ArrayList<Tile> redrawTiles);

    /**
     * Returns the name of this player
     * @author Amin Zeina
     *
     * @return The name of the player
     */
    String getName();

    /**
     * Places the given tile on the given square on the board
     *
     * @param square the square to place the tile in
     * @param tile the tile to place
     */
    void playTile(Square square, Tile tile);

    /**
     * Adds the given score to this player's score. To decrease score, param score should be negative. If score becomes
     * negative after adjustment (score < 0), the player's score is set to 0.
     *
     * @author Amin Zeina, 101186297
     * @version 1.0
     *
     * @param score the score to add to this players score (or to subtract if negative)
     */
    void adjustScore(int score);
}
