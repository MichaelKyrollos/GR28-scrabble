import java.util.ArrayList;
/**
 * The AIBoard class models the ScrabbleGame board.
 * The AI uses a copy of the BoardModel in a char[][]
 * representation. This aids in the encapsulation of the AI as a separate entity
 * from the game. This aids in increasing computation speed, testing, debugging, modularity.
 * (SEE DOCUMENTATION FOR MORE INFORMATION)
 * Scrabble game.
 *
 * @author Michael Kyrollos, 101183521
 * @version 1.2
 * @date December 1st 2022
 */

public class AIBoard {
    private final char[][] tiles;
    private final int SIZE;

    /**
     * Constructs a new AIBoard with given tiles and board size
     * @param tiles char[][] representation of tiles to be placed on board
     * @param SIZE the size of the scrabble board.
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 1st 2022
     */
    public AIBoard(char[][] tiles, int SIZE) {
        this.tiles = tiles;
        this.SIZE = SIZE;
    }

    /**
     * Used to find integer positions that represent
     * all possible positions on a Scrabble SIZExSIZE board.
     * @return ArrayList of int[] positions
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 4th 2022
     */
    public ArrayList<int[]> createAllPositions(){
        ArrayList<int[]> result = new ArrayList<>();
        for(int row = 0; row< SIZE; row++){
            for(int col = 0; col< SIZE; col++) {
                int[] pos = {row,col};
                result.add(pos);
            }
        }
        return result;
    }

    /**
     * Find the letter representation of a tile at a given position
     * @param pos A coordinate of the wanted tile from the board
     * @return char of the letter at a given int[] position on the board
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 4th 2022
     */
    public char getAITile(int[] pos){
        return tiles[pos[0]][pos[1]];
    }

    /**
     * Set the letter representation of a tile at a given position
     * @param pos A coordinate of the wanted tile from the board
     * @param tile The letter to be placed
     * @return The letter that has been placed.
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 4th 2022
     */
    public char setAITile(int[] pos, char tile){
        int row = pos[0];
        int col = pos[1];
        return tiles[row][col] = tile;
    }

    /**
     * Check if a position that is inputted is within the boundary of the board.
     * @param pos A coordinate of the wanted tile from the board
     * @return Return true if a position is in bounds of the board, false otherwise.
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 4th 2022
     */
    public boolean isInBounds(int[] pos){
        int row = pos[0];
        int col = pos[1];
        return (row>=0) && (row < SIZE) && (col>=0) && (col < SIZE);
    }

    /**
     * Check if a position that is inputted does not have a tile placed
     * Two separate functions written for this to aid with readability in AI code.
     * @param pos A coordinate of the wanted tile from the board
     * @return Return true if a position does not have a tile placed, false otherwise.
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 4th 2022
     */
    public boolean isEmpty(int[] pos) {
    //  empty spaces on board are represented as a '_'
        return isInBounds(pos) && getAITile(pos)=='_';
    }

    /**
     * Check if a position that is inputted has a tile placed
     * Two separate functions written for this to aid with readability in AI code.
     * @param pos A coordinate of the wanted tile from the board
     * @return Return true if a position does have a tile placed, false otherwise.
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 4th 2022
     */
    public boolean isFilled(int[] pos) {
        //  empty spaces on board are represented as a '_'
        return isInBounds(pos) && getAITile(pos)!='_';
    }

    /**
     * Creates a copy of the AIBoard, used for when the AI is checking if the move is legal.
     * It places the word on the copy of the board to keep the original copy of the AIBoard intact.
     * @return A copy of the AIBoard
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 4th 2022
     */
    public AIBoard copyBoard(){
        char[][] cloned = new char[SIZE][SIZE];
        for (int[] pos: createAllPositions()){
            cloned[pos[0]][pos[1]] =  getAITile(pos);
        }
        AIBoard copy = new AIBoard(cloned, SIZE);
        return copy;
    }

    /**
     * Return a string representation of the board. Useful for testing and debugging.
     * @return A String representation of the AIBoard and tiles placed on it.
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 4th 2022
     */
    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder();
        for(int row = 0; row< SIZE; row++){
            for(int col = 0; col< SIZE; col++) {
                int[] pos = {row,col};
                repr.append(getAITile(pos)).append(" ");
            }
            repr.append("\n");
        }
        return repr.toString();
    }

}
