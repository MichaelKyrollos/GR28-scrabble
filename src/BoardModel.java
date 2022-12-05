import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
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
public class BoardModel extends ScrabbleModel implements Serializable {
    public static final int SIZE = 15;

    public static final File DEFAULT_BOARD = new File("src/resources/DefaultBoard.xml");

    private Square[][] squares;

    private Square copiedSquares[][];



    private ScrabbleGameModel game;

    private boolean isEmpty;

    /**
     * Constructs a board object, which contains a 2-D array of Squares, using the default board layout
     *
     * @author Amin Zeina, 101186297
     * @version 4.0
     * @date December 4, 2022
     */
    public BoardModel(ScrabbleGameModel game) throws ParserConfigurationException, IOException, SAXException {
        this(game, DEFAULT_BOARD);
    }

    /**
     * Copy constructor for BoardModel.
     * @param board BoardModel to be copied.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 03, 2022
     */
    public BoardModel(BoardModel board) {
        this.game = board.game;
        this.isEmpty = board.isEmpty;
        this.squares = board.copyBoardSquaresToAnother();
        this.copiedSquares = board.copiedSquares;
    }

    /**
     * Constructs a board object with a custom layout matching the XML file given in param customBoard. A default board
     * will be created if there is an issue with the XML file
     *
     * @param game the ScrabbleGameModel this belongs to
     * @param xmlBoard the custom board layout XML file
     *
     * @author Amin Zeina, 101186297
     * @version 4.0
     * @date December 1, 2022
     */
    public BoardModel(ScrabbleGameModel game, File xmlBoard) throws ParserConfigurationException, SAXException, IOException {
        this.game = game;

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        CustomBoardHandler handler = new CustomBoardHandler();
        saxParser.parse(xmlBoard, handler);

        squares = new Square[SIZE][SIZE];

        createLetterPremiumSquares(handler.getDoubleLetterSquareCoords(), 2);
        createLetterPremiumSquares(handler.getTripleLetterSquareCoords(), 3);
        createWordPremiumSquares(handler.getDoubleWordSquareCoords(), 2);
        createWordPremiumSquares(handler.getTripleWordSquareCoords(), 3);

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
    private void createLetterPremiumSquares(List<int[]> squareCoords, int multiplier) {
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
    private void createWordPremiumSquares(List<int[]> squareCoords, int multiplier) {
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
     * @author Yehan De Silva
     * @version 4.0
     * @date December 04, 2022
     */
    public void copyBoardSquares() {
        this.copiedSquares = this.copyBoardSquaresToAnother();
    }

    /**
     * Helper function to copy construct an 2D array list of squares.
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Amin Zeina, 101186297
     * @author Yehan De Silva
     * @version 4.0
     * @date December 04, 2022
     */
    private Square[][] copyBoardSquaresToAnother() {
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
        return savedSquares;
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
     * @author Michael Kyrollos
     * @version 3.1
     * @date December 4th, 2022
     */
    private int checkVerticalAdjacentWord(Square initialSquare, String placedWord) {
        int row = initialSquare.getXCoord();
        int col = initialSquare.getYCoord();
        while (row >= 1 && squares[row - 1][col].getTile() != null) {
            row--;
        }
        // at this point, row is the row of the highest connected tile in this column
        // Now iterate down until we reach a null tile, and store the results

        String word = "";
        int score = 0;
        for(int row_i =row;row_i<SIZE;row_i++) {
            Tile currTile = squares[row_i][col].getTile();
            if (currTile==null){
                break;
            }
            word += currTile.getLetter();
            score += currTile.getValue();
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
     * @author Michael Kyrollos
     * @version 2.1
     * @date December 4th, 2022
     */
    private int checkHorizontalAdjacentWord(Square initialSquare, String placedWord) {
        int row = initialSquare.getXCoord();
        int col = initialSquare.getYCoord();
        while (col >= 1 && squares[row][col - 1].getTile() != null) {
            col--;
        }
        // at this point, col is the column of the leftmost connected tile in this row
        // Now iterate down until we reach a null tile, and store the results
        String word = "";
        int score = 0;
        for(int col_i =col;col_i<SIZE;col_i++) {
            Tile currTile = squares[row][col_i].getTile();
            if (currTile==null){
                break;
            }
            word += currTile.getLetter();
            score += currTile.getValue();
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
        StringBuilder repr = new StringBuilder();
        repr.append("-------------------------------\n");
        for (int i = 0; i < SIZE; i++) {
            repr.append("|");
            for (int j = 0; j < SIZE; j++) {
                if (this.getSquares()[i][j].getTile() == null) {
                    repr.append(" |");
                }
                else {
                    repr.append(this.getSquares()[i][j].getTile().getLetter() + "|");
                }
            }
            repr.append("\n");
            repr.append("-------------------------------\n");
        }
        return repr.toString();
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

