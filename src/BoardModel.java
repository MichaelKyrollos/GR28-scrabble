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
 * @version 3.0
 * @date November 14, 2022
 */
public class BoardModel extends ScrabbleModel{
    public static final int SIZE = 15;
    public static final int[][] DOUBLE_LETTER_SQUARE_COORDS = new int[][] {{0,3},{0,11},{2,6},{2,8},{3,0},{3,7},{3,14},
            {6,2},{6,6},{6,8},{6,12},{7,3},{7,11},{8,2},{8,6},{8,8},{8,12},{11,0},{11,7},{11,14},{12,6},{12,8},
            {14,3},{14,11}};
    public static final int[][] TRIPLE_LETTER_SQUARE_COORDS = new int[][] {{1,5},{1,9},{5,1},{5,5},{5,9},{5,13},{9,1},
            {9,5},{9,9},{9,13},{13,5},{13,9}};
    public static final int[][] DOUBLE_WORD_SQUARE_COORDS = new int[][] {{1,1},{1,13},{2,2},{2,12},{3,3},{3,11},{4,4},
            {4,10},{7,7},{10,4},{10,10},{11,3},{11,11},{12,2},{12,12},{13,1},{13,13}};
    public static final int[][] TRIPLE_WORD_SQUARE_COORDS = new int[][] {{0,0},{0,7},{0,14},{7,0},{7,14},{14,0},{14,7},
            {14,14}};
    private Square squares[][];

    private Square copiedSquares[][];



    private ScrabbleGameModel game;

    private boolean isEmpty;

    /**
     * Constructs a board object, which contains a 2-D array of Squares
     */
    public BoardModel(ScrabbleGameModel game) {
        this.game = game;

        squares = new Square[SIZE][SIZE];

        createLetterPremiumSquares(DOUBLE_LETTER_SQUARE_COORDS, 2);
        createLetterPremiumSquares(TRIPLE_LETTER_SQUARE_COORDS, 3);
        createWordPremiumSquares(DOUBLE_WORD_SQUARE_COORDS, 2);
        createWordPremiumSquares(TRIPLE_WORD_SQUARE_COORDS, 3);

        createDefaultSquares();

        copiedSquares = null; // not needed until a play occurs
        isEmpty = true;
    }

    /**
     * Creates the default squares and sets them as part of the board.
     *
     * @author Amin Zeina, 101186297
     * @version 3.0
     */
    private void createDefaultSquares() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (squares[i][j] == null) {
                    squares[i][j] = new Square(i,j);
                }
            }
        }
    }

    /**
     * Creates the letter premium squares that double or triple the letter placed on them, and sets them as part of the
     * board.
     *
     * @author Amin Zeina, 101186297
     * @version 3.0
     *
     * @param squareCoords the coordinates of the premium squares to be created
     * @param multiplier The score multiplier for this letter (2 or 3)
     */
    private void createLetterPremiumSquares(int[][] squareCoords, int multiplier) {
        for (int[] coord : squareCoords) {
            int x = coord[0];
            int y = coord[1];
            squares[x][y] = new LetterPremiumSquare(x,y, multiplier);
        }
    }

    /**
     * Creates the word premium squares that double or triple the word placed on them, and sets them as part of the
     * board.
     *
     * @author Amin Zeina, 101186297
     * @version 3.0
     *
     * @param squareCoords the coordinates of the premium squares to be created
     * @param multiplier The score multiplier for the word placed (2 or 3)
     */
    private void createWordPremiumSquares(int[][] squareCoords, int multiplier) {
        for (int[] coord : squareCoords) {
            int x = coord[0];
            int y = coord[1];
            squares[x][y] = new WordPremiumSquare(x,y, multiplier);
        }
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
                Square currSquare = squares[i][j];
                // Save a copy of the current square depending on its type
                if (currSquare instanceof LetterPremiumSquare) {
                    savedSquares[i][j] = new LetterPremiumSquare((LetterPremiumSquare) currSquare);
                } else if (currSquare instanceof WordPremiumSquare) {
                    savedSquares[i][j] = new WordPremiumSquare((WordPremiumSquare) currSquare);
                } else {
                    savedSquares[i][j] = new Square(currSquare);
                }

            }
        }
        this.copiedSquares = savedSquares;
    }

    public BoardModel copyBoardSquaresToAnother() {
        // Save the state of the board before placing any tiles
        // (create a copy of the array of squares)
        Square[][] savedSquares = new Square[SIZE][SIZE];


        // Traverse through each square on the current board
        for (int i = 0; i < squares.length; i++)
        {
            for (int j = 0; j < squares[i].length; j++)
            {
                Square currSquare = squares[i][j];
                // Save a copy of the current square depending on its type
                if (currSquare instanceof LetterPremiumSquare) {
                    savedSquares[i][j] = new LetterPremiumSquare((LetterPremiumSquare) currSquare);
                } else if (currSquare instanceof WordPremiumSquare) {
                    savedSquares[i][j] = new WordPremiumSquare((WordPremiumSquare) currSquare);
                } else {
                    savedSquares[i][j] = new Square(currSquare);
                }

            }
        }
        return new BoardModel(game);
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
     * Returns the 2D array of the copied squares making up the board.
     * This is mostly used for help in unit testing.
     *
     * @return 2D array of squares making up the board.
     *
     * @author Michael Kyrollos
     * @version 1.0
     * @date November 12, 2022
     */
    public Square[][] getCopiedSquares() {
        return this.copiedSquares;
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
        int originalWordScore = 0;
        int adjacentWordScore = 0;
        boolean isConnectedToExistingWord = false;

        if (!playEvent.areSquaresConnected())
        {
            return notifyInvalidPlacement("Invalid placement: The selected squares are not connected in order");
        }

        if (this.isEmpty && (squares[7][7].getTile() == null || word.length() < 2))
        {
            return notifyInvalidPlacement("Invalid placement: The first word placed must cover square H8 and be at least 2 letters long.");
        }

        if (isVertical)
        {
            // placed vertically
            for (int i = 0; i < wordSquares.size(); i++)
            {
                originalWordScore += wordSquares.get(i).getSquareValue();
                if (isLowerCase(word.charAt(i))) {
                    // this is a new letter, so check its horizontal adjacent words
                    int adjScore = checkHorizontalAdjacentWord(wordSquares.get(i), String.valueOf(word.charAt(i)));
                    if (adjScore == -1)
                    {
                        return notifyInvalidPlacement("Invalid placement: The adjacent tiles do not form valid words");
                    }
                    else
                    {
                        adjacentWordScore += adjScore;
                        if (adjScore > 0)
                        {
                            // there is a valid adjacent word, so this word is connected to another word
                            isConnectedToExistingWord = true;
                        }
                    }
                }
                else
                {
                    // there is an existing tile in the word, no need to check adjacent
                    isConnectedToExistingWord = true;
                }
            }
            // check that there isnt a word directly connected to this word vertically
            int adjScore = checkVerticalAdjacentWord(wordSquares.get(0), word);
            if (adjScore == -1)
            {
                return notifyInvalidPlacement("Invalid placement: The adjacent tiles do not form valid words");
            }
            else
            {
                adjacentWordScore += adjScore;
                if (adjScore > 0)
                {
                    // there is a valid adjacent word, so this word is connected to another word
                    isConnectedToExistingWord = true;
                }
            }

        }
        else
        {
            // placed horizontally
            for (int i = 0; i < wordSquares.size(); i++)
            {
                originalWordScore += wordSquares.get(i).getSquareValue();
                if (isLowerCase(word.charAt(i)))
                {
                    // this is a new letter, so check its horizontal adjacent words
                    int adjScore = checkVerticalAdjacentWord(wordSquares.get(i), String.valueOf(word.charAt(i)));
                    if (adjScore == -1)
                    {
                        return notifyInvalidPlacement("Invalid placement: The adjacent tiles do not form valid words");
                    }
                    else
                    {
                        adjacentWordScore += adjScore;
                        if (adjScore > 0)
                        {
                            // there is a valid adjacent word, so this word is connected to another word
                            isConnectedToExistingWord = true;
                        }
                    }
                }
                else
                {
                    // there is an existing tile in the word, no need to check adjacent
                    isConnectedToExistingWord = true;
                }
            }
            // check that there isnt a word directly connected to this word vertically
            int adjScore = checkHorizontalAdjacentWord(wordSquares.get(0), word);
            if (adjScore == -1)
            {
                return notifyInvalidPlacement("Invalid placement: The adjacent tiles do not form valid words");
            }
            else
            {
                adjacentWordScore += adjScore;
                if (adjScore > 0)
                {
                    // there is a valid adjacent word, so this word is connected to another word
                    isConnectedToExistingWord = true;
                }
            }
        }

        // Check if the placed word is connected to another word on the board
        if (!isConnectedToExistingWord && !isEmpty)
        {
            return notifyInvalidPlacement("Invalid placement: The word is not connected to any existing words");
        }
        isEmpty = false;
        return originalWordScore * this.checkWordBonus(wordSquares) + adjacentWordScore;
    }

    /**
     * The notifyInvalidPlacement method notifies the user
     * of an invalid placement. The method notifies the user via
     * a JOptionPane message dialog with the specified message.
     *
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     * @date November 19th, 2022
     */
    private int notifyInvalidPlacement(String message)
    {
        // Create a message dialog with the specified message
        JOptionPane.showMessageDialog(null, message);
        // Revert the board to its previous state
        revertBoard();
        return -1;
    }

    /**
     * Returns the bonus score multiplier for this word, which will be 1 if there is no premium tile used for this word,
     * or > 1 if one or multiple premium squares are used.
     * @param squares the list of squares the word to check was placed on
     * @return the bonus score multipler of the placed word
     */
    private int checkWordBonus(ArrayList<Square> squares) {
        int multiplier = 1;
        for (Square square : squares) {
            if (square instanceof WordPremiumSquare) {
                multiplier *= ((WordPremiumSquare) square).getWordValueMultiplier();
            }
        }
        return multiplier;
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
        int row = initialSquare.getXCoord();
        int col = initialSquare.getYCoord();
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
        int row = initialSquare.getXCoord();
        int col = initialSquare.getYCoord();
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
                if (getSquares()[i][j] !=null){
                    s += "   |";
                }
                else {
                    s += getSquares()[i][j].getTile().getLetter();
                }
            }
            s += "\n------------------------------------------------------------\n";
        }
        return s;

        }


    public ScrabbleGameModel getGame() {
        return game;
    }
    /**
     *
     * Returns a char[][] representing the current state of the game.
     * Used by the AI to come up with a play
     *
     * @author Michael Kyrollos, ID: 101183521
     * @version 1.0
     * @date November 22, 2022
     * @return String representing the scrabble game state
     */
    public char[][] forAI() {
        char[][] c = new char[15][15];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (this.getSquares()[i][j].getTile() == null) {
                    c[i][j] = '_';
                }
                else {
                    c[i][j] = toLowerCase(this.getSquares()[i][j].getTile().getLetter());
                }
            }
        }
        return c;
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

