import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Square class models a Square on a Scrabble BoardModel.
 * Each square has the possibility to have a tile placed
 *
 * @author Michael Kyrollos, 101183521
 * @author Yehan De Silva, 101185388
 * @version 3.0
 * @date November 22, 2022
 */
@SuppressWarnings("rawtypes")
public class Square extends JButton implements Serializable, Comparable {

    private Tile tile;

    public static final Color SQUARE_UNSELECTED_COLOR = new Color(34, 178, 194);
    public static final Color SQUARE_SELECTED_COLOUR = Color.white;

    private final int xCoord;
    private final int yCoord;

    private boolean squareFinalized;

    /**
     * Constructs a square object.
     * @param x x coordinate of the square.
     * @param y y coordinate of the square.
     *
     * @author Yehan De Silva, 101185388
     * @version 4.0
     * @date December 05, 2022
     */
    public Square(int x, int y) {
        super(" ");
        this.xCoord = x;
        this.yCoord = y;
        this.squareFinalized = false;
        this.setBackground(ScrabbleFrameView.SQUARE_BACKGROUND_COLOR);
        this.setFont(new Font("Manrope", Font.BOLD, 18));
        this.setFocusPainted(false);
        // Create and add a border to the button
        this.setBorder(BorderFactory.createLineBorder(SQUARE_UNSELECTED_COLOR));
        this.setForeground(Color.WHITE);
    }

    /**
     * Deep copy constructor for Square
     * @author Pathum Danthanarayana, 101181411
     * @author Amin Zeina, 101186297
     * @version 2.1
     * @date November 12, 2022
     * @param otherSquare - Another Square object where its field(s) (a Tile object)
     *                    will be copied to the current Square's field(s).
     */
    public Square(Square otherSquare)
    {
        super(otherSquare.getText());
        this.tile = otherSquare.tile;
        this.xCoord = otherSquare.xCoord;
        this.yCoord = otherSquare.yCoord;
        this.squareFinalized = otherSquare.squareFinalized;
        this.setBackground(ScrabbleFrameView.SQUARE_BACKGROUND_COLOR);
        this.setFont(new Font("Manrope", Font.BOLD, 18));
        this.setFocusPainted(false);
        // Create and add a border to the button
        Border border = BorderFactory.createLineBorder(SQUARE_UNSELECTED_COLOR);
        this.setBorder(border);
        this.setForeground(Color.WHITE);
    }

    /**
     * Getter method for the xCoord field. Returns the x coordinate of this square on the board.
     *
     * @author Amin Zeina, 101186297
     * @version 1.0
     *
     * @return the x coordinate of this square on the board
     */
    public int getXCoord() {
        return xCoord;
    }

    /**
     * Getter method for the yCoord field. Returns the y coordinate of this square on the board.
     *
     * @author Amin Zeina, 101186297
     * @version 1.0
     *
     * @return the y coordinate of this square on the board
     */
    public int getYCoord() {
        return yCoord;
    }

    /**
     * Returns the point value associated with the tile in this square, or 0 if there is no tile.
     *
     * @author Amin Zeina, 101186297
     * @version 3.0
     *
     * @return the point value associated with the tile in this square, or 0 if there is no tile.
     */
    public int getSquareValue() {
        if (this.tile != null) {
            return tile.getValue();
        }
        return 0;
    }

    /**
     * Returns the square finalized field of the square.
     * @return True if the square is finalized, false otherwise.
     *
     * @author Yehan De Silva, 101185388
     * @version 4.0
     * @date December 05, 2022
     */
    public boolean getSquareFinalized() {
        return this.squareFinalized;
    }

    /**
     * Sets the square finalized field to the given boolean.
     * @param b The value to be set to.
     *
     * @author Yehan De Silva, 101185388
     * @version 4.0
     * @date December 05, 2022
     */
    public void setSquareFinalized(Boolean b) {
        this.squareFinalized = b;
    }

    /**
     * Places a tile on a square, regardless of the square's state
     *
     * @param tile The tile to be placed on the Square
     */
    public void placeSquare(Tile tile) {
        this.tile = tile;
    }

    /**
     * Removes tile from this square.
     *
     * @author Yehan De Silva, 101185388
     * @version 4.0
     * @date December 05, 2022
     */
    public void removeTile() {this.tile = null;}

    /**
     * Returns a string representing the current state of the tile.
     * @return String representing the scrabble game state
     *
     * @author Michael Kyrollos, ID: 101183521
     * @author Yehan De Silva, 101185388
     * @version 3.0
     * @date November 22, 2022
     */
    @Override
    public String toString() {
        String s = "";
        s += Objects.requireNonNullElse(this.tile, " ");
        return s;
    }

    /**
     * Getter method for tile field
     *
     * @return the tile associated with this square
     * @author Amin Zeina
     */
    public Tile getTile() {
        return this.tile;
    }

    /**
     * Compares a square to another.
     * @param o the object to be compared.
     * @return 1 if this square has greater x/y values than other square, 0 if they are equal and -1 otherwise.
     */
    @Override
    public int compareTo(Object o) {
        Square otherSquare = (Square) o;
        if (this.xCoord == otherSquare.xCoord && this.yCoord == otherSquare.yCoord) {return 0;}
        else if (this.xCoord > otherSquare.xCoord || this.yCoord > otherSquare.yCoord) {return 1;}
        else {return -1;}
    }
}
