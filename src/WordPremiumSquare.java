/**
 * This class models a Premium Square on a Scrabble BoardModel, which doubles or triples
 * the word value of the word placed on this square.
 *
 * @author Amin Zeina, 101186297
 * @version 3.0
 * @date November 16, 2022
 */
public class WordPremiumSquare extends Square {

    private final int wordValueMultiplier;

    /** Used to track if this square's bonus has been used already (should be only used once, on the first word play)*/
    private boolean isBonusScoreUsed;

    /**
     * Creates a word premium square with the given multiplier
     *
     * @param x the x coordinate of this square on the board
     * @param y the x coordinate of this square on the board
     * @param multiplier the letter score multiplier of this square
     */
    public WordPremiumSquare(int x, int y, int multiplier) {
        super(x, y);
        this.wordValueMultiplier = multiplier;
        this.isBonusScoreUsed = false;
        if (wordValueMultiplier == 2) {
            this.setBackground(ScrabbleFrameView.DOUBLE_WORD_SQUARE_BACKGROUND_COLOR);
        } else {
            // multiplier == 3
            this.setBackground(ScrabbleFrameView.TRIPLE_WORD_SQUARE_BACKGROUND_COLOR);
        }
    }

    /**
     * Deep copy constructor for WordPremiumSquare
     * @author Amin Zeina, 101186297
     * @version 3.0
     * @date November 16, 2022
     * @param otherSquare - Another WordPremiumSquare object where its fields
     *                    will be copied to the current LetterPremiumSquare's fields.
     */
    public WordPremiumSquare(WordPremiumSquare otherSquare) {
        super(otherSquare);
        this.wordValueMultiplier = otherSquare.wordValueMultiplier;
        this.isBonusScoreUsed = otherSquare.isBonusScoreUsed;
        if (this.wordValueMultiplier == 2) {
            this.setBackground(ScrabbleFrameView.DOUBLE_WORD_SQUARE_BACKGROUND_COLOR);
        } else {
            // multiplier == 3
            this.setBackground(ScrabbleFrameView.TRIPLE_WORD_SQUARE_BACKGROUND_COLOR);
        }
    }

    /**
     * Getter method for the wordValueMultiplier field. Returns the word score multiplier for this square
     *
     * @author Amin Zeina, 101186297
     * @version 3.0
     * @date November 16, 2022
     * @return the word score multiplier for this square, or 1 if the square bonus has already been used
     */
    public int getWordValueMultiplier() {
        if (!isBonusScoreUsed) {
            isBonusScoreUsed = true;
            return this.wordValueMultiplier;
        } else {
            return 1;
        }
    }
}
