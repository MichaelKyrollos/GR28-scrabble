import static java.lang.Character.isDigit;
import java.util.*;
/**
 * The Board class models a typical board of Scrabble.
 * The board is composed of 15 X 15 Squares. Each of these
 * Squares contain a game Tile
 *
 * @author Michael Kyrollos, 101183521
 * @version 1.1
 * @date October 22, 2022
 */


import java.lang.*;
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
     * @version 1.1
     * @date October 22, 2022
     *
     * @return True if word has been placed, False otherwise.
     *
     */
    public Boolean placeWord(Command command,ArrayList<Tile> tilesToPlay){
        HashMap<Character,Integer> column = new HashMap<>();
        column.put('A',0);
        column.put('B',1);
        column.put('C',2);
        column.put('D',3);
        column.put('E',4);
        column.put('F',5);
        column.put('G',6);
        column.put('H',7);
        column.put('I',8);
        column.put('J',9);
        column.put('K',10);
        column.put('L',11);
        column.put('M',12);
        column.put('N',13);
        column.put('O',14);

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
                int rows = Character.getNumericValue(firstChar + secondChar) - 1;
                int i = column.get(thirdChar);
                for (int j = rows; j < tilesToPlay.size() + rows ; j++) {
                    try
                    {
                        // Attempt to place the tile on the square
                        squares[i][j].placeSquare(tilesToPlay.get(j-rows));
                    }
                    catch (Exception e)
                    {
                        System.out.println("Cannot place word. A letter has gone out of bounds.");
                        // Reset the state of the board
                        this.squares = savedSquares;

                        return false;
                    }
                }
                // Print out the updated board
                System.out.println(this);
            }

            // Otherwise, player has entered e.g. A10
            else
            {
                //System.out.println("Printing vertically for double-digit row!");
                int j =  column.get(firstChar);
                int columns = Character.getNumericValue(secondChar + thirdChar) - 1;
                for (int i = columns; i < tilesToPlay.size() + columns ; i++) {
                    try
                    {
                        // Attempt to place the tile on the square
                        squares[i][j].placeSquare(tilesToPlay.get(i-columns));
                    }
                    catch (Exception e)
                    {
                        System.out.println("Cannot place word. A letter has gone out of bounds.");
                        // Reset the state of the board
                        this.squares = savedSquares;

                        return false;
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
                int rows = Character.getNumericValue(firstChar) - 1;
                int i = column.get(secondChar);
                for (int j = rows; j < tilesToPlay.size() + rows ; j++) {
                    try
                    {
                        // Attempt to place the tile on the square
                        squares[i][j].placeSquare(tilesToPlay.get(j-rows));
                    }
                    catch (Exception e)
                    {
                        System.out.println("Cannot place word. A letter has gone out of bounds.");
                        // Reset the state of the board
                        this.squares = savedSquares;

                        return false;
                    }
                }
                // Print out the updated board
                System.out.println(this);
            }
            else {
                //System.out.println("Printing vertically!");
                int j =  column.get(firstChar);
                int columns = Character.getNumericValue(secondChar) - 1;
                for (int i = columns; i < tilesToPlay.size() + columns ; i++) {
                    try
                    {
                        // Attempt to place the tile on the square
                        squares[i][j].placeSquare(tilesToPlay.get(i-columns));
                    }
                    catch (Exception e)
                    {
                        System.out.println("Cannot place word. A letter has gone out of bounds.");
                        // Reset the state of the board
                        this.squares = savedSquares;

                        return false;
                    }
                }
                // Print out the updated board
                System.out.println(this);
            }
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

