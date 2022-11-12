import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Square extends JButton {
    /**
     * The Square class models a Square on a Scrabble BoardModel.
     * Each square has the possibility to have a tile placed
     * TODO Each square can have points
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.1
     * @date October 22, 2022
     */

    private Tile tile;

    private int xCoord;
    private int yCoord;

    public Square(int x, int y) {
        super(" ");
        this.xCoord = x;
        this.yCoord = y;
        this.setBackground(ScrabbleFrameView.SQUARE_BACKGROUND_COLOR);
        this.setFont(new Font("Arial", Font.BOLD, 18));
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
        this.setFont(new Font("Arial", Font.BOLD, 18));
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
    public int getxCoord() {
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
    public int getyCoord() {
        return yCoord;
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
     *
     * Returns a string representing the current state of the tile.
     *
     * @author Michael Kyrollos, ID: 101183521
     *
     * @return String representing the scrabble game state
     *
     */
    @Override
    public String toString() {
        String s = "";
        if (this.tile != null) {
            s += this.tile;
        }
        else {
            s += " ";
        }
        return s;
    }
    /**
     * getter method for tile field
     *
     * @return the tile associated with this square
     * @author Amin Zeina
     */
    public Tile getTile() {
        return this.tile;
    }


}
