import static java.lang.Character.isDigit;
import java.util.*;
/**
 * The Board class models a typical board of Scrabble.
 * The board is composed of 15 X 15 Squares. Each of these
 * Squares contain a game Tile
 *
 * @author Michael Kyrollos, 101183521
 * @version 1.0
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

        char firstChar = command.getThirdWord().charAt(0);
        char secondChar = command.getThirdWord().charAt(1);

        if (isDigit(firstChar)){
            int rows = Character.getNumericValue(firstChar);
            int i =  column.get(secondChar);
            for (int j = rows; j < tilesToPlay.size() + rows ; j++) {
                    squares[j][i].placeSquare(tilesToPlay.get(j-rows));}
            System.out.println(this);
        }
        else {
            int j =  column.get(firstChar);
            int columns = Character.getNumericValue(secondChar);
            for (int i = columns; i < tilesToPlay.size() + columns ; i++) {
                squares[j][i].placeSquare(tilesToPlay.get(i-columns));}
            System.out.println(this);
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

