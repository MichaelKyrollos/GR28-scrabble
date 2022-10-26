import static java.lang.Character.isDigit;
import java.util.*;
/**
 * The Board class models a typical board of Scrabble.
 * The board is composed of 15 X 15 Squares. Each of these
 * Squares contain a game Tile
 *
 * @author Michael Kyrollos, 101183521
 * @author Pathum Danthanarayana, 101181411
 * @author Amin Zeina, 101186297
 * @version 1.2
 * @date October 25, 2022
 */


import java.lang.*;

/**
 * Constructs a board object, which contains a 2-D array of Squares
 */
public class Board {
    public static final int SIZE = 15;
    private Square squares[][];

    public Board() {
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
     * @version 1.2
     * @date October 25, 2022
     *
     * @return the score of the word, if placed successfully. Return -1 if unsuccessful
     *
     */
    public int placeWord(Command command,ArrayList<Tile> tilesToPlay){
        HashMap<Character,Integer> columnMap = new HashMap<>();
        columnMap.put('A',0);
        columnMap.put('B',1);
        columnMap.put('C',2);
        columnMap.put('D',3);
        columnMap.put('E',4);
        columnMap.put('F',5);
        columnMap.put('G',6);
        columnMap.put('H',7);
        columnMap.put('I',8);
        columnMap.put('J',9);
        columnMap.put('K',10);
        columnMap.put('L',11);
        columnMap.put('M',12);
        columnMap.put('N',13);
        columnMap.put('O',14);

        // Save the state of the board before placing any tiles
        // (create a copy of the array of squares)
        Square[][] savedSquares;
        savedSquares = new Square[SIZE][SIZE];

        // Traverse through each square on the current board
        for (int i = 0; i < squares.length; i++)
        {
            for (int j = 0; j < squares[i].length; j++)
            {
                // Save a copy of the current square
                savedSquares[i][j] = new Square(squares[i][j]);
            }
        }
        // Keep track of the score of the word
        int tempScore = 0;

        // adjust String word so that all existing letters (already on the board) are uppercase
        // e.g if word == h(e)llo -> hEllo
        char[] wordChars = command.getSecondWord().toLowerCase().toCharArray();
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
        String word = new String(wordChars);
        word = word.replaceAll("[()]", ""); //remove brackets


        // Store first character of command
        char firstChar = command.getThirdWord().charAt(0);
        // Store second character of command
        char secondChar = command.getThirdWord().charAt(1);

        // Check if the player has specified a double-digit row (e.g. 10, 11, 12, 13, 14)
        if (command.getThirdWord().length() == 3)
        {
            // Store the third character of command
            char thirdChar = command.getThirdWord().charAt(2);
            // Check if the first character is an integer (e.g. player has entered 10A)
            if (isDigit(firstChar))
            {
                //System.out.println("Printing horizontally for double-digit row!");
                int row = Character.getNumericValue(firstChar + secondChar) - 1;
                int col = columnMap.get(thirdChar);
                int tilesPlayed = 0;
                for (int currCol = col; currCol < word.length() + col ; currCol++) {
                    if (squares[row][currCol].getTile() == null) {
                        // Square is empty -> attempt to place tile
                        if (!attemptSquarePlacement(row, currCol, tilesToPlay.get(tilesPlayed))) {
                            // Reset the state of the board
                            this.squares = savedSquares;
                            return -1;
                        } else {
                            // tile placed successfully -> increase score
                            tempScore += tilesToPlay.get(tilesPlayed).getValue();
                            tilesPlayed++;
                        }
                    } else {
                        // Square not empty -> validate that existing tile is the same as entered in the user's word
                        char letter = squares[row][currCol].getTile().getLetter();
                        if (letter != word.charAt(currCol-col)) {
                            // existing tile is not the same as the user entered
                            System.out.println("There is an existing tile in the way; this placement is invalid.");
                            this.squares = savedSquares;
                            return -1;
                        } else {
                            // existing tile is the same as the user entered -> increase score
                            tempScore += squares[row][currCol].getTile().getValue();
                        }
                    }
                }
                // Print out the updated board
                System.out.println(this);
            }

            // Otherwise, player has entered e.g. A10
            else
            {
                //System.out.println("Printing vertically for double-digit row!");
                int col = columnMap.get(firstChar);
                int row = Character.getNumericValue(secondChar + thirdChar) - 1;
                int tilesPlayed = 0;
                for (int currRow = row; currRow < word.length() + row ; currRow++) {
                    if (squares[currRow][col].getTile() == null) {
                        // Square is empty -> attempt to place tile
                        if (!attemptSquarePlacement(currRow, col, tilesToPlay.get(tilesPlayed))) {
                            // Reset the state of the board
                            this.squares = savedSquares;
                            return -1;
                        } else {
                            // tile placed successfully -> increase score
                            tempScore += tilesToPlay.get(tilesPlayed).getValue();
                            tilesPlayed++;
                        }
                    } else {
                        // Square not empty -> validate that existing tile is the same as entered in the user's word
                        char letter = squares[currRow][col].getTile().getLetter();
                        if (letter != word.charAt(currRow-row)) {
                            // existing tile is not the same as the user entered
                            System.out.println("There is an existing tile in the way; this placement is invalid.");
                            this.squares = savedSquares;
                            return -1;
                        } else {
                            // existing tile is the same as the user entered -> increase score
                            tempScore += squares[currRow][col].getTile().getValue();
                        }
                    }
                }
                // Print the updated board
                System.out.println(this);
            }
        }

        // Otherwise, the player has specified a single-digit row (e.g. 1, 2, 3, 4, 5, 6, 7, 8, 9)
        else
        {
            // Check if the first character is an integer (e.g. player has entered 6A)
            if (isDigit(firstChar)){
                //System.out.println("Printing horizontally!");
                int row = Character.getNumericValue(firstChar) - 1;
                int col = columnMap.get(secondChar);
                int tilesPlayed = 0;
                for (int currCol = col; currCol < word.length() + col ; currCol++) {
                    if (squares[row][currCol].getTile() == null) {
                        // Square is empty -> attempt to place tile
                        if (!attemptSquarePlacement(row, currCol, tilesToPlay.get(tilesPlayed))) {
                            // Reset the state of the board
                            this.squares = savedSquares;
                            return -1;
                        } else {
                            // tile placed successfully -> increase score
                            tempScore += tilesToPlay.get(tilesPlayed).getValue();
                            tilesPlayed++;
                        }
                    } else {
                        // Square not empty -> validate that existing tile is the same as entered in the user's word
                        char letter = squares[row][currCol].getTile().getLetter();
                        if (letter != word.charAt(currCol-col)) {
                            // existing tile is not the same as the user entered
                            System.out.println("There is an existing tile in the way; this placement is invalid.");
                            this.squares = savedSquares;
                            return -1;
                        } else {
                            // existing tile is the same as the user entered -> increase score
                            tempScore += squares[row][currCol].getTile().getValue();
                        }
                    }
                }
                // Print out the updated board
                System.out.println(this);
            }
            else {
                //System.out.println("Printing vertically!");
                int col =  columnMap.get(firstChar);
                int row = Character.getNumericValue(secondChar) - 1;
                int tilesPlayed = 0;
                for (int currRow = row; currRow < word.length() + row ; currRow++) {
                    if (squares[currRow][col].getTile() == null) {
                        // Square is empty -> attempt to place tile
                        if (!attemptSquarePlacement(currRow, col, tilesToPlay.get(tilesPlayed))) {
                            // Reset the state of the board
                            this.squares = savedSquares;
                            return -1;
                        } else {
                            // tile placed successfully -> increase score
                            tempScore += tilesToPlay.get(tilesPlayed).getValue();
                            tilesPlayed++;
                        }
                    } else {
                        // Square not empty -> validate that existing tile is the same as entered in the user's word
                        char letter = squares[currRow][col].getTile().getLetter();
                        if (letter != word.charAt(currRow-row)) {
                            // existing tile is not the same as the user entered
                            System.out.println("There is an existing tile in the way; this placement is invalid.");
                            this.squares = savedSquares;
                            return -1;
                        } else {
                            // existing tile is the same as the user entered -> increase score
                            tempScore += squares[currRow][col].getTile().getValue();
                        }
                    }
                }
                // Print out the updated board
                System.out.println(this);
            }
        }
        // Entire word placed Successfully
        return tempScore;
    }

    /**
     * Attempts to place a tile in the square at a given location on the board.
     *
     * @param i the row of the square to place the tile
     * @param j the column of the square to place the tile
     * @param tile the tile to place
     * @return true if the tile was placed, false otherwise
     * @author Amin Zeina
     */
    private boolean attemptSquarePlacement(int i, int j, Tile tile) {
        try {
            squares[i][j].placeSquare(tile);
        } catch (Exception e) {
            System.out.println("Cannot place word. A letter has gone out of bounds.");
            return false;
        }
        return true;
    }

    /**
     *
     * Returns a string representing the current state of the game.  Looks like a
     * scrabble board.
     *
     * @author Michael Kyrollos, ID: 101183521
     *
     * @return String representing the scrabble game state
     *
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

