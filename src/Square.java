public class Square {
    /**
     * The Square class models a Square on a Scrabble Board.
     * Each square has the possibility to have a tile placed
     * TODO Each square can have points
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.0
     * @date October 22, 2022
     */

    private Tile tile;
    public Square() {

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



}