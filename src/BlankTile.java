/**
 * The BlankTile class models a blank tile in the game of Scrabble.
 *
 * @author Amin Zeina, 101186297
 * @author Yehan De Silva, 101185388
 * @version 3.1
 * @date November 17, 2022
 */
public class BlankTile extends Tile {

    /** Constant to represent attributes of blank tile */
    public static final char DEFAULT_BLANK_TILE_TEXT = '-';
    public static final int DEFAULT_SCORE_BLANK_TILE = 0;
    public static final int DEFAULT_FREQUENCY_BLANK_TILE = 2;

    /**
     * Creates a blank tile object with the default letter and score.
     *
     * @author Yehan De Silva, 101185388
     * @version 3.0
     * @date November 17, 2022
     */
    public BlankTile() {this(DEFAULT_BLANK_TILE_TEXT, DEFAULT_SCORE_BLANK_TILE);}

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
     * @param letter the letter to set this tile to.
     *
     * @author Amin Zeina, 101186297
     * @version 3.0
     * @date November 14, 2022
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
        this.letter = BlankTile.DEFAULT_BLANK_TILE_TEXT;
    }
}
