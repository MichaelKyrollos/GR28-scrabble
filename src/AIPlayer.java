import java.util.ArrayList;

/**
 * The AIPlayer class models an AI player in the Scrabble game.
 *
 * @author Pathum Danthanarayana, 101181411
 * @version 1.0
 * @date November 19th, 2022
 */
public class AIPlayer implements ScrabblePlayer
{
    /** Fields **/


    /** Constructor **/
    public AIPlayer()
    {
        //TODO: Implement AIPlayer class
    }

    /**
     * Plays the given word entered by the user at the given coordinates. Returns true if the word was successfully
     * placed (i.e. the user had the necessary tiles and the placement of the word on the board was valid)
     * <p>
     * Words are entered as strings with preplaced letters enclosed in (). For example, placing "h(e)ll(o)" states that
     * the "e" and "o" are already on the board in the correct location, thus the player doesn't need to have an e or o
     * tile to play this word.
     *
     * @param playEvent the PlayWordEvent that was generated to play this word
     * @return true if the word was successfully placed, false otherwise
     * @author Amin Zeina, 101186297
     * @author Yehan De Silva
     * @version 2.1
     * @date November 12, 2022
     */
    @Override
    public boolean playWord(PlayWordEvent playEvent)
    {
        return false;
    }

    /**
     * Redraws the given tiles from the player's rack.
     *
     * @param redrawTiles Tiles to be redrawn.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 13, 2022
     */
    @Override
    public void redraw(ArrayList<Tile> redrawTiles)
    {

    }

    /**
     * Returns the name of this player
     *
     * @return The name of the player
     * @author Amin Zeina
     */
    @Override
    public String getName()
    {
        return null;
    }

    /**
     * Places the given tile on the given square on the board
     *
     * @param square the square to place the tile in
     * @param tile   the tile to place
     */
    @Override
    public void playTile(Square square, Tile tile)
    {

    }

    /**
     * Adds the given score to this player's score. To decrease score, param score should be negative. If score becomes
     * negative after adjustment (score < 0), the player's score is set to 0.
     *
     * @param score the score to add to this players score (or to subtract if negative)
     * @author Amin Zeina, 101186297
     * @version 1.0
     */
    @Override
    public void adjustScore(int score)
    {

    }
}
