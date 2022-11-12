import javax.swing.*;

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

    public Square() {
        super(" ");
    }

    /**
     * Deep copy constructor for Square
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     * @date October 24, 2022
     * @param otherSquare - Another Square object where its field(s) (a Tile object)
     *                    will be copied to the current Square's field(s).
     */
    public Square(Square otherSquare)
    {
        this.tile = otherSquare.tile;
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
