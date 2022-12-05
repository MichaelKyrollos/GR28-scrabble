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
 * @author Yehan De Silva
 * @version 3.0
 * @date November 22, 2022
 */
public class Square extends JButton implements Serializable {

    private Tile tile;

    private final int xCoord;
    private final int yCoord;

    public Square(int x, int y) {
        super(" ");
        this.xCoord = x;
        this.yCoord = y;
        this.setBackground(ScrabbleFrameView.SQUARE_BACKGROUND_COLOR);
        this.setFont(new Font("Manrope", Font.BOLD, 18));
        this.setFocusPainted(false);
        // Create and add a border to the button
        Border border = BorderFactory.createLineBorder(ScrabbleFrameView.SQUARE_BORDER_COLOR);
        this.setBorder(border);
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
        this.setBackground(ScrabbleFrameView.SQUARE_BACKGROUND_COLOR);
        this.setFont(new Font("Manrope", Font.BOLD, 18));
        this.setFocusPainted(false);
        // Create and add a border to the button
        Border border = BorderFactory.createLineBorder(ScrabbleFrameView.SQUARE_BORDER_COLOR);
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
     * Places a tile on a square, regardless of the square's state
     *
     * @param tile The tile to be placed on the Square
     */
    public void placeSquare(Tile tile) {
        this.tile = tile;
    }

    /**
     * Returns a string representing the current state of the tile.
     * @return String representing the scrabble game state
     *
     * @author Michael Kyrollos, ID: 101183521
     * @author Yehan De Silva
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

}
