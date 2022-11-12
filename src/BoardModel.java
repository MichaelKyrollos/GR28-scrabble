import javax.swing.*;

import java.util.*;
import java.lang.*;

import static java.lang.Character.*;

/**
 * The BoardModel class models a typical board of Scrabble.
 * The board is composed of 15 X 15 Squares. Each of these
 * Squares contain a game Tile
 *
 * @author Michael Kyrollos, 101183521
 * @author Pathum Danthanarayana, 101181411
 * @author Amin Zeina, 101186297
 * @author Yehan De Silva, 101185388
 * @version 2.3
 * @date November 9, 2022
 */
public class BoardModel extends ScrabbleModel{
    public static final int SIZE = 15;
    private Square squares[][];

    private Square copiedSquares[][];

    private ScrabbleGameModel game;

    /**
     * Constructs a board object, which contains a 2-D array of Squares
     */
    public BoardModel(ScrabbleGameModel game) {
        this.game = game;

        squares = new Square[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                squares[i][j] = new Square(i,j);
            }
        }
        copiedSquares = null; // not needed until a play occurs
    }

    /**
     * Returns the 2D array of squares making up the board.
     * @return 2D array of squares making up the board.
     *
     * @author Yehan De Silva
     * @version 1.0
     * @date November 11, 2022
     */
    public Square[][] getSquares() {
        return this.squares;
    }

    /**
     * Validates the placement of the word on the board. Returns the score of the word, if placement is valid.
     * Return -1 if invalid.
     *
     * @author Michael Kyrollos, ID: 101183521
     * @author Pathum Danthanarayana, 101181411
     * @author Amin Zeina, 101186297
     * @version 2.3
     * @date November 12, 2022
     *
     * @param playEvent the PlayWordEvent that was generated to play this word
     * @return the score of the word, if placed successfully. Return -1 if unsuccessful
     *
     */
    public int placeWord(PlayWordEvent playEvent){
        boolean isVertical = playEvent.isVerticalPlacement();
        ArrayList<Square> wordSquares = playEvent.getSquaresInWord();
        String word = playEvent.getWord();
        int tempScore = 0;
        boolean isConnectedToExistingWord = false;

        if (!playEvent.areSquaresConnected()) {
            JOptionPane.showMessageDialog(null, "Invalid placement: The selected squares " +
                    "are not connected in order");
            revertBoard();
            return -1;
        }

        if (game.getCurrentTurn() == 0 && (squares[7][7].getTile() == null) || word.length() < 2) {
            JOptionPane.showMessageDialog(null, "Invalid placement: The first word placed must " +
                    "cover square H8 and be at least 2 letters long.");
            revertBoard();
            return -1;
        }

        if (isVertical) {
            // placed vertically
            for (int i = 0; i < wordSquares.size(); i++) {
                tempScore += wordSquares.get(i).getTile().getValue();
                if (isLowerCase(word.charAt(i))) {
                    // this is a new letter, so check its horizontal adjacent words
                    int adjScore = checkHorizontalAdjacentWord(wordSquares.get(i), String.valueOf(word.charAt(i)));
                    if (adjScore == -1) {
                        // adjacent tiles don't form a valid word, so word placement invalid
                        JOptionPane.showMessageDialog(null, "Invalid placement: The adjacent " +
                                "tiles do not form valid words");
                        revertBoard();
                        return -1;
                    } else {
                        tempScore += adjScore;
                        if (adjScore > 0) {
                            // there is a valid adjacent word, so this word is connected to another word
                            isConnectedToExistingWord = true;
                        }
                    }
                } else {
                    // there is an existing tile in the word, no need to check adjacent
                    isConnectedToExistingWord = true;
                }
            }
            // check that there isnt a word directly connected to this word vertically
            int adjScore = checkVerticalAdjacentWord(wordSquares.get(0), word);
            if (adjScore == -1) {
                // adjacent tiles don't form a valid word, so word placement invalid
                JOptionPane.showMessageDialog(null, "Invalid placement: The adjacent " +
                        "tiles do not form valid words");
                revertBoard();
                return -1;
            } else {
                tempScore += adjScore;
                if (adjScore > 0) {
                    // there is a valid adjacent word, so this word is connected to another word
                    isConnectedToExistingWord = true;
                }
            }

        } else {
            // placed horizontally
            for (int i = 0; i < wordSquares.size(); i++) {
                tempScore += wordSquares.get(i).getTile().getValue();
                if (isLowerCase(word.charAt(i))) {
                    // this is a new letter, so check its horizontal adjacent words
                    int adjScore = checkVerticalAdjacentWord(wordSquares.get(i), String.valueOf(word.charAt(i)));
                    if (adjScore == -1) {
                        // adjacent tiles don't form a valid word, so word placement invalid
                        JOptionPane.showMessageDialog(null, "Invalid placement: The adjacent " +
                                "tiles do not form valid words");
                        revertBoard();
                        return -1;
                    } else {
                        tempScore += adjScore;
                        if (adjScore > 0) {
                            // there is a valid adjacent word, so this word is connected to another word
                            isConnectedToExistingWord = true;
                        }
                    }
                } else {
                    // there is an existing tile in the word, no need to check adjacent
                    isConnectedToExistingWord = true;
                }
            }
            // check that there isnt a word directly connected to this word vertically
            int adjScore = checkHorizontalAdjacentWord(wordSquares.get(0), word);
            if (adjScore == -1) {
                // adjacent tiles don't form a valid word, so word placement invalid
                JOptionPane.showMessageDialog(null, "Invalid placement: The adjacent " +
                        "tiles do not form valid words");
                revertBoard();
                return -1;
            } else {
                tempScore += adjScore;
                if (adjScore > 0) {
                    // there is a valid adjacent word, so this word is connected to another word
                    isConnectedToExistingWord = true;
                }
            }
        }

        // Check if the placed word is connected to another word on the board
        if (!isConnectedToExistingWord && game.getCurrentTurn() != 0) {
            JOptionPane.showMessageDialog(null, "Invalid placement: The word is not " +
                    "connected to any existing words ");
            revertBoard();
            return -1;
        }

        return tempScore;
    }

    /**
     * Stores a copy of the board's current state (it's 2D array of Squares) in the copiedSquares field
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Amin Zeina, 101186297
     */
    public void copyBoardSquares() {
        // Save the state of the board before placing any tiles
        // (create a copy of the array of squares)
        Square[][] savedSquares = new Square[SIZE][SIZE];

        // Traverse through each square on the current board
        for (int i = 0; i < squares.length; i++)
        {
            for (int j = 0; j < squares[i].length; j++)
            {
                // Save a copy of the current square
                savedSquares[i][j] = new Square(squares[i][j]);
            }
        }
        this.copiedSquares = savedSquares;
    }

    /**
     * Revert the board back to its copied state
     *
     * @author Amin Zeina, 101186297
     * @version 1.0
     */
    public void revertBoard() {
        this.squares = copiedSquares;
        copiedSquares = null;
        updateScrabbleViews();
        System.out.println(this);
    }
    
    /**
     * Helper method for playWord(). Checks for vertical words that are adjacent to the given square. Returns
     * the score of the adjacent word if valid, or returns -1 if the word is invalid, or returns
     * 0 if there is no adjacent word.
     *
     * @param initialSquare the square to check the adjacent squares of
     * @param placedWord the word or letter to be placed who's adjacent squares are being checked.
     * @return the score of the adjacent word, or -1 if invalid word, or 0 if there is no adjacent word
     * @author Amin Zeina, 101186297
     */
    private int checkVerticalAdjacentWord(Square initialSquare, String placedWord) {
        int row = initialSquare.getxCoord();
        int col = initialSquare.getyCoord();
        while (row >= 1 && squares[row - 1][col].getTile() != null) {
            row--;
        }
        // at this point, row is the row of the highest connected tile in this column
        // Now iterate down until we reach a null tile, and store the results
        Tile currTile = squares[row][col].getTile();
        String word = "";
        int score = 0;
        while (currTile != null) {
            word += currTile.getLetter();
            score += currTile.getValue();
            row++;
            currTile = squares[row][col].getTile();
        }
        // ensure there is an adjacent word (not only the tile just placed in playWord(), which is already counted)
        if (word.equals(placedWord.toUpperCase())) {
            return 0;
        }
        // check if word is a valid word
        if (!game.SCRABBLE_DICTIONARY.validateWord(word)) {
            return -1;
        }
        // word is valid, return score
        return score;
    }

    /**
     * Helper method for playWord(). Checks for horizontal words that are adjacent to the given. Returns
     * the score of the adjacent word if valid, or returns -1 if the word is invalid, or returns
     * 0 if there is no adjacent word.
     *
     * @param initialSquare the square to check the adjacent squares of
     * @param placedWord the word or letter to be placed who's adjacent squares are being checked.
     * @return the score of the adjacent word, or -1 if invalid word, or 0 if there is no adjacent word
     * @author Amin Zeina, 101186297
     */
    private int checkHorizontalAdjacentWord(Square initialSquare, String placedWord) {
        int row = initialSquare.getxCoord();
        int col = initialSquare.getyCoord();
        while (col >= 1 && squares[row][col - 1].getTile() != null) {
            col--;
        }
        // at this point, col is the column of the leftmost connected tile in this row
        // Now iterate down until we reach a null tile, and store the results
        Tile currTile = squares[row][col].getTile();
        String word = "";
        int score = 0;
        while (currTile != null) {
            word += currTile.getLetter();
            score += currTile.getValue();
            col++;
            currTile = squares[row][col].getTile();
        }
        // ensure there is an adjacent word (not only the tile just placed in playWord(), which is already counted)
        if (word.equals(placedWord.toUpperCase())) {
            return 0;
        }
        // check if word is a valid word
        if (!game.SCRABBLE_DICTIONARY.validateWord(word)) {
            return -1;
        }
        // word is valid (or there is no adjacent word), return score
        return score;
    }

    /**
     *
     * Returns a string representing the current state of the game.  Looks like a
     * scrabble board.
     *
     * @author Michael Kyrollos, ID: 101183521
     *
     * @return String representing the scrabble game state
     */
    @Override
    public String toString(){
        String s = "\n------------------------------------------------------------\n" ;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                s += " " + squares[i][j] + " |";
            }
            s += "\n------------------------------------------------------------\n";
        }
        return s;

        }

    /**
     * Places the given tile on the given square on the board
     *
     * @param square the square to place the tile in
     * @param tile the tile to place
     */
    public void playTile(Square square, Tile tile) {
        square.placeSquare(tile);
        updateScrabbleViews();
    }
}

