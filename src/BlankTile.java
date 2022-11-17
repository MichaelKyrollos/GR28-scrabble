/**
 * The BlankTile class models a blank tile in the game of Scrabble.
 *
 * @author Amin Zeina, 101186297
 * @version 3.0
 * @date November 14, 2022
 */
public class BlankTile extends Tile {

    /**
     * Creates a blank tile object.
     * @param letter Letter of the blank tile.
     * @param pointValue Point value of the blank tile.
     */
    public BlankTile(char letter, int pointValue) {
        super(letter, pointValue);
    }

    /**
     * Sets this letter to the given letter.
     *
     * @author Amin Zeina, 101186297
     * @version 3.0
     * @date November 14, 2022
     *
     * @param letter the letter to set this tile to
     */
    public void setLetter(char letter) {
        this.letter = letter;
    }

    /**
     * Resets this tile's letter to the default blank character
     *
     * @author Amin Zeina, 101186297
     * @version 3.0
     * @date November 14, 2022
     */
    public void resetToBlank() {
        this.letter = Tile.BLANK_TILE_TEXT;
    }
}
