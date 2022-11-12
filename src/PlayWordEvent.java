import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;

/**
 * This class stores information for when a player plays a word.
 *
 * @author Amin Zeina, 101186297
 * @version 1.0
 * @date November 12, 2022
 */
public class PlayWordEvent extends EventObject {

    private ArrayList<Square> squaresInWord;

    private ArrayList<Tile> tilesPlaced;

    private String word;

    /**
     * Constructs a PlayWordEvent object
     *
     * @author Amin Zeina, 101186297
     * @version 1.0
     *
     * @param model the model source of the event
     * @param squaresInWord the squares that are included in the word
     * @param tilesPlaced the new tiles that were placed on the board
     */
    public PlayWordEvent(ScrabbleModel model, ArrayList<Square> squaresInWord, ArrayList<Tile> tilesPlaced) {
        super(model);
        this.squaresInWord = squaresInWord;
        this.tilesPlaced = tilesPlaced;
        this.word = createWordString();
    }

    /**
     * Getter method for the word field. Returns the word placed as a formatted string
     *
     * @author Amin Zeina, 101186297
     * @version 1.0
     *
     * @return a String of the placed word
     */
    public String getWord() {
        return this.word;
    }

    /**
     * Getter method for the tilesPlaced field. Returns a list of placed tiles.
     *
     * @author Amin Zeina, 101186297
     * @version 1.0
     *
     * @return a list of placed tiles
     */
    public ArrayList<Tile> getTilesPlaced() {
        return this.tilesPlaced;
    }

    /**
     * Getter method for the squaresInWord field. Returns a list of squares of the placed word.
     *
     * @author Amin Zeina, 101186297
     * @version 1.0
     *
     * @return a list of squares of the placed word
     */
    public ArrayList<Square> getSquaresInWord() {
        return this.squaresInWord;
    }

    /**
     * Returns a formatted string of the placed word.
     *
     * New letters that were just placed (new tiles) are lowercase, and letters that were already placed (existing tiles)
     * are uppercase. For example, if word == h(e)llo return hEllo
     *
     * @author Amin Zeina, 101186297
     * @version 1.0
     *
     * @return a formatted string of the placed word
     */
    private String createWordString() {
        String word = "";
        int tileIndex = 0;
        for (Square square : squaresInWord) {
            if (tileIndex < tilesPlaced.size() && square.getTile() == tilesPlaced.get(tileIndex)) { // checking object references ==
                // this square contains a new tile -> new letter, lowercase in string
                word += Character.toLowerCase(square.getTile().getLetter());
                tileIndex++;
            } else {
                // no new tiles left, or this sqaure's tile not in new tile list -> existing letter, uppercase in string
                word += Character.toUpperCase(square.getTile().getLetter());
            }
        }
        return word;
    }

    /**
     * Returns true if the word is being placed vertically, false otherwise
     *
     * @author Amin Zeina, 101186297
     * @version 1.0
     *
     * @return true if the word is being placed vertically, false otherwise
     */
    public boolean isVerticalPlacement() {

        if (squaresInWord.size() < 2 || squaresInWord.get(1).getxCoord() == squaresInWord.get(0).getxCoord() + 1) {
            // squares are being placed vertically
            return true;
        }
        return false;
    }

    /**
     * Returns true if the squares in the word are connected in a row/column (all squares are together), false otherwise
     *
     * @author Amin Zeina, 101186297
     * @version 1.0
     *
     * @return true if the squares in the word are connected in a row/column (all squares are together), false otherwise
     */
    public boolean areSquaresConnected() {
        if (isVerticalPlacement()) {
            for (int i = 1; i < squaresInWord.size(); i++) {
                Square currSquare = squaresInWord.get(i);
                Square prevSquare = squaresInWord.get(i - 1);
                if (currSquare.getxCoord() != prevSquare.getxCoord() + 1 || currSquare.getyCoord() != prevSquare.getyCoord()) {
                    // prev and curr squares are not connected vertically
                    return false;
                }
            }
        } else {
            for (int i = 1; i < squaresInWord.size(); i++) {
                Square currSquare = squaresInWord.get(i);
                Square prevSquare = squaresInWord.get(i - 1);
                if (currSquare.getyCoord() != prevSquare.getyCoord() + 1 && currSquare.getxCoord() != prevSquare.getxCoord()) {
                    // prev and curr squares are not connected horizontally
                    return false;
                }
            }
        }
        return true;
    }


}
