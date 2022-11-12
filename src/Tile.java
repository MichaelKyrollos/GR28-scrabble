import javax.swing.*;
import java.awt.*;

/**
 * The Tile class models a tile in the game of Scrabble.
 * A tile contains a letter and a point value.
 *
 * @author Pathum Danthanarayana, 101181411
 * @version 1.0
 * @date October 22, 2022
 */
public class Tile extends JButton {

    /** Fields **/
    private char letter;
    private int pointValue;

    /** Constructor **/
    public Tile(char letter, int pointValue)
    {
        super(String.valueOf(letter));
        // Initialize the tile's letter and point value to the specified letter and point value
        this.letter = letter;
        this.pointValue = pointValue;

        this.setBackground(ScrabbleFrameView.ACCENT_COLOR);
        this.setFocusPainted(false);
        this.setForeground(Color.WHITE);
        this.setFont(ScrabbleFrameView.PLAYER_BODY_FONT);
    }

    /** Methods **/

    /**
     * The getLetter method returns a String containing the tile's corresponding letter.
     * @author Pathum Danthanarayana, 101181411
     *
     * @return a Character containing the letter of the current tile
     */
    public char getLetter()
    {
        return this.letter;
    }

    /**
     * The getValue method returns an integer containing the tile's corresponding point value.
     * @author Pathum Danthanarayana, 101181411
     *
     * @return an Integer containing the point value of the current tile
     */
    public int getValue()
    {
        return this.pointValue;
    }

    /**
     * The toString method overrides the default toString method to
     * describe a tile by its letter.
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 1.1
     * @date October 25, 2022
     *
     * @return a String that describes a tile
     */
    @Override
    public String toString()
    {
        return String.valueOf(this.getLetter());
    }
}
