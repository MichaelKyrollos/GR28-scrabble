import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Character.toUpperCase;

//import java.util.ArrayList;
//import java.util.HashMap;
//
///**
// * The AIPlayer class models an AI player in the Scrabble game.
// *
// * @author Pathum Danthanarayana, 101181411
// * @version 1.0
// * @date November 19th, 2022
// */
public class AIPlayer extends PlayerModel{
    /** Fields **/
    private LetterTree dict;

    public AIPlayer(String name, BoardModel board) {
        super(name,board);
        this.dict = new LetterTree();

    }

    private PlayWordEvent convertEvent(AI.AIMove move) {
        if (move==null){
            return null;
        }
        ArrayList<Square> squaresForWord = new ArrayList<Square>();
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        String word = move.getWord();

        int[] pos = move.getPos();
        int row = pos[0];
        int col = pos[1];
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
        Collections.reverse(squaresForWord);
        Collections.reverse(tiles);
        return new PlayWordEvent(getBoard().getGame(), squaresForWord, tiles);
    }

    public PlayWordEvent makeMove(){
        char[][] c = getBoard().forAI();
        AIBoard aiBoard = new AIBoard(c,BoardModel.SIZE);
        StringBuilder aiRack = getRack().forAI();
        AI aiPlayer = new AI(dict,aiBoard,aiRack);
        AI.AIMove bestMove = aiPlayer.find_all_options();
        PlayWordEvent wordEvent = convertEvent(bestMove);
        return wordEvent;
    }
}
