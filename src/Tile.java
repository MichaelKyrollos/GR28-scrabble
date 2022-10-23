/**
 * The Tile class models a tile in the game of Scrabble.
 * A tile contains a letter and a point value.
 *
 * @author Pathum Danthanarayana, 101181411
 * @version 1.0
 * @date October 22, 2022
 */
public class Tile {

    /** Fields **/
    private Character letter;
    private int pointValue;

    /** Constructor **/
    public Tile(Character letter, int pointValue)
    {
        // Initialize the tile's letter and point value to the specified letter and point value
        this.letter = letter;
        this.pointValue = pointValue;
    }

    /** Methods **/

    /**
     * The getLetter method returns a String containing the tile's corresponding letter.
     * @author Pathum Danthanarayana, 101181411
     *
     * @return a String containing the letter of the current tile
     */
    public Character getLetter()
    {
        return this.letter;
    }

    /**
     * The getValue method returns an integer containing the tile's corresponding point value.
     * @author Pathum Danthanarayana, 101181411
     *
     * @return an integer containing the point value of the current tile
     */
    public int getValue()
    {
        return this.pointValue;
    }
}
