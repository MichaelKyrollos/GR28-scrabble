import static java.lang.Character.isDigit;
import static java.lang.Character.isUpperCase;

import java.util.*;
import java.lang.*;
/**
 * The Board class models a typical board of Scrabble.
 * The board is composed of 15 X 15 Squares. Each of these
 * Squares contain a game Tile
 *
 * @author Michael Kyrollos, 101183521
 * @author Pathum Danthanarayana, 101181411
 * @author Amin Zeina, 101186297
 * @version 2.2
 * @date November 9, 2022
 */
public class Board {
    public static final int SIZE = 15;
    private Square squares[][];

    private ScrabbleGame game;
    /**
     * Constructs a board object, which contains a 2-D array of Squares
     */
    public Board(ScrabbleGame game) {
        this.game = game;

        squares = new Square[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                squares[i][j] = new Square();
            }
        }
    }

    /**
     *
     * Returns True if word has been placed on board by player, false otherwise.
     *
     * @author Michael Kyrollos, ID: 101183521
     * @author Pathum Danthanarayana, 101181411
     * @author Amin Zeina, 101186297
     * @version 2.2
     * @date November 9, 2022
     *
     * @param word the word to play (must be uppercase)
     * @param coords the coordinate of the word to be played (must be uppercase)
     * @param tilesToPlay the new tiles to add to the board
     * @return the score of the word, if placed successfully. Return -1 if unsuccessful
     *
     */
    public int placeWord(String word, String coords,  ArrayList<Tile> tilesToPlay){
        HashMap<String,Integer> columnMap = new HashMap<>();
        columnMap.put("A",0);
        columnMap.put("B",1);
        columnMap.put("C",2);
        columnMap.put("D",3);
        columnMap.put("E",4);
        columnMap.put("F",5);
        columnMap.put("G",6);
        columnMap.put("H",7);
        columnMap.put("I",8);
        columnMap.put("J",9);
        columnMap.put("K",10);
        columnMap.put("L",11);
        columnMap.put("M",12);
        columnMap.put("N",13);
        columnMap.put("O",14);

        // Save the state of the board before placing any tiles
        // (create a copy of the array of squares)
        Square[][] savedSquares = copyBoardSquares();

        // Keep track of the score of the word
        int tempScore = 0;
        boolean isConnectedToExistingWord = false;

        word = formatWord(word);

        String[] parsedCoords = parseCoords(coords);

        if (isDigit(parsedCoords[0].charAt(0))) {
            // placing horizontal
            int row = Integer.parseInt(parsedCoords[0]) - 1;
            int col = columnMap.get(parsedCoords[1]);
            int tilesPlayed = 0;
            for (int currCol = col; currCol < word.length() + col ; currCol++) {
                if (squares[row][currCol].getTile() == null) {
                    // Check that the user should be placing this tile (i.e., this letter was not entered in (brackets))
                    if (isUpperCase(word.charAt(currCol-col))) {
                        // User entered this letter as already on the board, but tile == null, so word placement invalid
                        System.out.println("Invalid placement: There is no preexisting tile");
                        this.squares = savedSquares;
                        return -1;
                    }
                    // Square is empty -> attempt to place tile
                    try {
                        squares[row][currCol].placeTile(tilesToPlay.get(tilesPlayed));
                        // tile placed successfully -> increase score
                        tempScore += tilesToPlay.get(tilesPlayed).getValue();
                        tilesPlayed++;
                        // a new tile was placed in a horizontal word, so check its possible adjacent vertical words
                        int adjScore = checkVerticalAdjacentWord(row, currCol, String.valueOf(squares[row][currCol].getTile().getLetter()));
                        if (adjScore == -1) {
                            // adjacent tiles don't form a valid word, so word placement invalid
                            System.out.println("Invalid placement: The adjacent tiles do not form valid words");
                            this.squares = savedSquares;
                            return -1;
                        } else {
                            tempScore += adjScore;
                            if (adjScore > 0) {
                                // there is a valid adjacent word, so this word is connected to another word
                                isConnectedToExistingWord = true;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid placement: A letter has gone out of bounds.");
                        this.squares = savedSquares;
                        return -1;
                    }
                } else {
                    // Square not empty -> validate that existing tile is the same as entered in the user's word
                    char letter = squares[row][currCol].getTile().getLetter();
                    if (letter != word.charAt(currCol-col)) {
                        // existing tile is not the same as the user entered
                        System.out.println("Invalid placement: There is an existing tile in the way.");
                        this.squares = savedSquares;
                        return -1;
                    } else {
                        // existing tile is the same as the user entered -> increase score
                        tempScore += squares[row][currCol].getTile().getValue();
                        isConnectedToExistingWord = true;
                    }
                }
            }
            // check that there isnt a word directly connected to this word horizontally
            int adjScore = checkHorizontalAdjacentWord(row, col, word);
            if (adjScore == -1) {
                // adjacent tiles don't form a valid word, so word placement invalid
                System.out.println("Invalid placement: The adjacent tiles do not form valid words");
                this.squares = savedSquares;
                return -1;
            } else {
                tempScore += adjScore;
                if (adjScore > 0) {
                    // there is a valid adjacent word, so this word is connected to another word
                    isConnectedToExistingWord = true;
                }
            }
        } else {
            // placing vertical
            int col = columnMap.get(parsedCoords[0]);
            int row = Integer.parseInt(parsedCoords[1]) - 1;
            int tilesPlayed = 0;
            for (int currRow = row; currRow < word.length() + row ; currRow++) {
                if (squares[currRow][col].getTile() == null) {
                    // Check that the user should be placing this tile (i.e., this letter was not entered in (brackets))
                    if (isUpperCase(word.charAt(currRow-row))) {
                        // User entered this letter as already on the board, but tile == null, so word placement invalid
                        System.out.println("Invalid placement: There is no preexisting tile");
                        this.squares = savedSquares;
                        return -1;
                    }
                    // Square is empty -> attempt to place tile
                    try {
                        squares[currRow][col].placeTile(tilesToPlay.get(tilesPlayed));
                        // tile placed successfully -> increase score
                        tempScore += tilesToPlay.get(tilesPlayed).getValue();
                        tilesPlayed++;
                        // a new tile was placed in a vertical word, so check its possible adjacent horizontal words
                        int adjScore = checkHorizontalAdjacentWord(currRow, col, String.valueOf(squares[currRow][col].getTile().getLetter()));
                        if (adjScore == -1) {
                            // adjacent tiles don't form a valid word, so word placement invalid
                            System.out.println("Invalid placement: The adjacent tiles do not form valid words");
                            this.squares = savedSquares;
                            return -1;
                        } else {
                            tempScore += adjScore;
                            if (adjScore > 0) {
                                // there is a valid adjacent word, so this word is connected to another word
                                isConnectedToExistingWord = true;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid placement: A letter has gone out of bounds.");
                        this.squares = savedSquares;
                        return -1;
                    }
                } else {
                    // Square not empty -> validate that existing tile is the same as entered in the user's word
                    char letter = squares[currRow][col].getTile().getLetter();
                    if (letter != word.charAt(currRow-row)) {
                        // existing tile is not the same as the user entered
                        System.out.println("Invalid placement: There is an existing tile in the way.");
                        this.squares = savedSquares;
                        return -1;
                    } else {
                        // existing tile is the same as the user entered -> increase score
                        tempScore += squares[currRow][col].getTile().getValue();
                        isConnectedToExistingWord = true;
                    }
                }
            }
            // check that there isnt a word directly connected to this word vertically
            int adjScore = checkVerticalAdjacentWord(row, col, word);
            if (adjScore == -1) {
                // adjacent tiles don't form a valid word, so word placement invalid
                System.out.println("Invalid placement: The adjacent tiles do not form valid words");
                this.squares = savedSquares;
                return -1;
            } else {
                tempScore += adjScore;
                if (adjScore > 0) {
                    // there is a valid adjacent word, so this word is connected to another word
                    isConnectedToExistingWord = true;
                }
            }
        }

        if (game.getCurrentTurn() == 0 && (squares[8][columnMap.get("H")].getTile() == null) || word.length() < 2) {
            System.out.println("Invalid placement: The first word placed must cover square H8 " +
                    "and be at least 2 letters long.");
            this.squares = savedSquares;
            return -1;
        }

        // Check if the placed word is connected to another word on the board
        if (!isConnectedToExistingWord && game.getCurrentTurn() != 0) {
            System.out.println("Invalid placement: The word is not connected to any existing words");
            this.squares = savedSquares;
            return -1;
        }

        // Entire word placed Successfully
        System.out.println(this);
        return tempScore;
    }

    /**
     * Returns a copy of the board's current state (it's 2D array of Squares)
     *
     * @return a copy of the board's current Squares
     * @author Pathum Danthanarayana, 101181411
     * @author Amin Zeina, 101186297
     *
     */
    private Square[][] copyBoardSquares() {
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
        return savedSquares;
    }

    /**
     * Helper method for playWord(). Returns an array of two strings containing the coords of the word to be placed
     *
     * @param coords the coordinates of the word placement
     * @return an array of strings containing the coordinates of the word to be placed
     * @author Amin Zeina, 101186297
     */
    private String[] parseCoords(String coords) {
        if (coords.length() == 3) {
            if (isDigit(coords.charAt(0))) {
                // For example, 11A -> return ["11", "A"]
                return new String[]{coords.substring(0,2), coords.substring(2)};
            } else {
                // For example, A11 -> return ["A", "11"]
                return new String[]{coords.substring(0,1), coords.substring(1)};
            }
        } else {
            return new String[]{coords.substring(0,1), coords.substring(1)};
        }
    }

    /**
     * Helper method for playWord(). Returns a formatted String of the word to be played.
     *
     * Letters that must be placed (new tiles) are lowercase, and letters that are already placed (existing tiles)
     * are uppercase. For example, if word == h(e)llo return hEllo
     *
     * @param word the unformatted word that is being played
     * @return a formatted version of the word to be played
     * @author Amin Zeina, 101186297
     */
    private String formatWord(String word) {
        char[] wordChars = word.toLowerCase().toCharArray();
        boolean withinBracket = false;
        for (int c = 0; c < wordChars.length; c++) {
            if (withinBracket) {
                wordChars[c] = Character.toUpperCase(wordChars[c]);
            }
            if (wordChars[c] == '(') {
                withinBracket = true;
            }
            if (wordChars[c] == ')') {
                withinBracket = false;
            }
        }
        return new String(wordChars).replaceAll("[()]", ""); //remove brackets;
    }

    /**
     * Helper method for playWord(). Checks for vertical words that are adjacent to the square at the given x and y
     * coordinates. Returns the score of the adjacent word if valid, or returns -1 if the word is invalid, or returns
     * 0 if there is no adjacent word.
     *
     * @param x the x coordinate of the square to check
     * @param y the y coordinate of the square to check
     * @param placedWord the word or letter to be placed who's adjacent squares are being checked.
     * @return the score of the adjacent word, or -1 if invalid word, or 0 if there is no adjacent word
     * @author Amin Zeina, 101186297
     */
    private int checkVerticalAdjacentWord(int x, int y, String placedWord) {
        int row = x;
        while (row >= 1 && squares[row - 1][y].getTile() != null) {
            row--;
        }
        // at this point, row is the row of the highest connected tile in this column
        // Now iterate down until we reach a null tile, and store the results
        Tile currTile = squares[row][y].getTile();
        String word = "";
        int score = 0;
        while (currTile != null) {
            word += currTile.getLetter();
            score += currTile.getValue();
            row++;
            currTile = squares[row][y].getTile();
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
     * Helper method for playWord(). Checks for horizontal words that are adjacent to the square at the given x and y
     * coordinates. Returns the score of the adjacent word if valid, or returns -1 if the word is invalid, or returns
     * 0 if there is no adjacent word.
     *
     * @param x the x coordinate of the square to check
     * @param y the y coordinate of the square to check
     * @param placedWord the word or letter to be placed who's adjacent squares are being checked.
     * @return the score of the adjacent word, or -1 if invalid word, or 0 if there is no adjacent word
     * @author Amin Zeina, 101186297
     */
    private int checkHorizontalAdjacentWord(int x, int y, String placedWord) {
        int col = y;
        while (col >= 1 && squares[x][col - 1].getTile() != null) {
            col--;
        }
        // at this point, col is the column of the leftmost connected tile in this row
        // Now iterate down until we reach a null tile, and store the results
        Tile currTile = squares[x][col].getTile();
        String word = "";
        int score = 0;
        while (currTile != null) {
            word += currTile.getLetter();
            score += currTile.getValue();
            col++;
            currTile = squares[x][col].getTile();
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

}

