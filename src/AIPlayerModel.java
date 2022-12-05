import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Character.toUpperCase;
/**
 * The AIPlayerModel class models an AI in the game. It extends a regular PlayerModel. It will be using
 * many of the same methods.
 *
 * @author Michael Kyrollos, 101183521
 * @version 1.2
 * @date December 1st 2022
 */
public class AIPlayerModel extends PlayerModel implements ScrabblePlayer{
    /** Fields **/
    // Stores a field of the dictionary which is to be used
    // when playing.
    private final LetterTree dict;

    /**
     * Constructs a new AIPlayerModel with given name and board
     * @param board Passes the current board being used in the game
     * @param name Name of the AI Bot.
     *
     * @author Michael Kyrollos, 101183521
     * @version 2.1
     * @date December 1st 2022
     */
    public AIPlayerModel(String name, BoardModel board) {
        // calls the constructor of PlayerModel
        super(name,board);
        this.dict = new LetterTree();

    }

    /**
     * Copy constructor for AIPlayerModel.
     * @param player AIPlayerModel to be copied.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 05, 2022
     */
    public AIPlayerModel(AIPlayerModel player) {
        super(player);
        this.dict = player.dict;
    }

    /**
     * Coverts an AIMove into a PlayWordEvent which is to be used by the rest of the ScrabbleGame.
     * @param move AIMove which represents the final move that the AI has created.
     * @return PlayWordEvent representing a possible move made by the AI.
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 1st 2022
     */
    private PlayWordEvent convertEvent(AI.AIMove move) {
        // if move is null, the AI cannot play with the given tiles and board.
        // Tiles are redrawn in that case.
        if (move==null){
            return null;
        }
        // Initializing the parameters required to make a playWordEvent
        ArrayList<Square> squaresForWord = new ArrayList<>();
        ArrayList<Tile> tiles = new ArrayList<>();
        String word = move.getWord();

        int[] pos = move.getPos();
        int row = pos[0];
        int col = pos[1];
        // Go through each letter in the word, find the corresponding tile
        // add it to the list of tiles to be used and place it on a square.
        // these values are stored in squaresForWord and tiles.
        for (int word_i = word.length()-1; word_i >= 0; word_i--){
            char letter = word.charAt(word_i);
            if (getBoard().getSquares()[row][col].getTile() == null) {
                Tile curr_tile = getRack().takeTile(toUpperCase(letter));
                tiles.add(curr_tile);
                getBoard().getSquares()[row][col].placeSquare(curr_tile);
                squaresForWord.add(getBoard().getSquares()[row][col]);
            }
            if (move.getDirection().equals("across")) {
                col -= 1;
            } else {
                row -= 1;
            }
        }
        // the arraylists are reversed as the output of the AI is done in reverse order (part of the algorithm, it
        // returns the last position of the word not the first) - this is to ensure words will fit on the Board.
        Collections.reverse(squaresForWord);
        Collections.reverse(tiles);
        return new PlayWordEvent(getBoard().getGame(), squaresForWord, tiles);
    }


    /**
     * Uses a PlayWordEvent to make a move, NOTICE: this function does not do what PlayerModel's playWord() method does.
     * PlayWord is still called by AIPlayerModel, this method is what allows the playWord() method to also work for this
     * class in the ScrabbleController.
     * @return PlayWordEvent representing the final move/best move made by the AI. Ensures a readable format by the
     *         rest of the classes.
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 1st 2022
     */
    public PlayWordEvent makeMove(){
        char[][] c = getBoard().forAI();
        AIBoard aiBoard = new AIBoard(c,BoardModel.SIZE);
        StringBuilder aiRack = getRack().forAI();
        AI aiPlayer = new AI(dict,aiBoard,aiRack);
        AI.AIMove bestMove = aiPlayer.findAllOptions();
        PlayWordEvent wordEvent = convertEvent(bestMove);
        return wordEvent;
    }
}
