/**
 * This class models a Premium Square on a Scrabble BoardModel, which doubles or triples
 * the letter value of the tile placed on this square.
 *
 * @author Amin Zeina, 101186297
 * @version 3.0
 * @date November 16, 2022
 */
public class LetterPremiumSquare extends Square {

    private int letterValueMultiplier;
    /** Used to track if this square's bonus has been used already (should be only used once, on the first word play)*/
    private boolean isBonusScoreUsed;

    /**
     * Creates a letter premium square with the given multiplier
     *
     * @param x the x coordinate of this square on the board
     * @param y the x coordinate of this square on the board
     * @param multiplier the letter score multiplier of this square
     */
    public LetterPremiumSquare(int x, int y, int multiplier) {
        super(x, y);
        this.letterValueMultiplier = multiplier;
        this.isBonusScoreUsed = false;
        if (letterValueMultiplier == 2) {
            this.setBackground(ScrabbleFrameView.DOUBLE_LETTER_SQUARE_BACKGROUND_COLOR);
        } else {
            // multiplier == 3
            this.setBackground(ScrabbleFrameView.TRIPLE_LETTER_SQUARE_BACKGROUND_COLOR);
        }
    }

    /**
     * Deep copy constructor for LetterPremiumSquare
     * @author Amin Zeina, 101186297
     * @version 3.0
     * @date November 16, 2022
     * @param otherSquare - Another LetterPremiumSquare object where its fields
     *                    will be copied to the current LetterPremiumSquare's fields.
     */
    public LetterPremiumSquare(LetterPremiumSquare otherSquare) {
        super(otherSquare);
        this.letterValueMultiplier = otherSquare.letterValueMultiplier;
        if (this.letterValueMultiplier == 2) {
            this.setBackground(ScrabbleFrameView.DOUBLE_LETTER_SQUARE_BACKGROUND_COLOR);
        } else {
            // multiplier == 3
            this.setBackground(ScrabbleFrameView.TRIPLE_LETTER_SQUARE_BACKGROUND_COLOR);
        }
    }

    /**
     * Returns the point value associated with the tile in this square, or 0 if there is no tile.
     *
     * @author Amin Zeina, 101186297
     * @version 3.0
     *
     * @return the point value associated with the tile in this square, or 0 if there is no tile.
     */
    @Override
    public int getSquareValue() {
        // check if this bonus was already used (i.e., check that this is the original letter placed on this tile)
        if (!isBonusScoreUsed) {
            // original letter placed -> increase score by multiplier
            isBonusScoreUsed = true;
            return super.getSquareValue() * letterValueMultiplier;
        } else {
            // this square is part of a new word, not the original placement -> dont increase score
            return super.getSquareValue();
        }
    }
}
