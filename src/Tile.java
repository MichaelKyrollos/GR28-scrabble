import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * The Tile class models a tile in the game of Scrabble.
 * A tile contains a letter and a point value.
 *
 * @author Pathum Danthanarayana, 101181411
 * @author Amin Zeina, 101186297
 * @version 3.0
 * @date November 14, 2022
 */
public class Tile extends JButton implements Serializable {

    /** Fields **/
    protected char letter;
    private final int pointValue;

    /** Constructor **/
    public Tile(char letter, int pointValue)
    {
        // Format text as 'Letter_pointValue' (point value as a subscript)
        super(String.format("<html>%s<sub>%d</sub></html>", letter, pointValue));
        // Initialize the tile's letter and point value to the specified letter and point value
        this.letter = letter;
        this.pointValue = pointValue;

        this.setBackground(Color.WHITE);
        this.setFocusPainted(false);
        this.setForeground(Color.BLACK);
        this.setFont(new Font("Manrope", Font.BOLD, 18));
    }

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
        // Format Tile string as 'Letter_pointValue' (point value as a subscript)
        return String.format("<html>%s<sub>%d</sub></html>", this.letter, this.pointValue);
    }
}
