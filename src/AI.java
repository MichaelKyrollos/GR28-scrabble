import java.util.*;
import java.util.HashMap;
import static java.util.Map.entry;

/**
 * The AIModel class models the game's AI logic. This is the brain's of the AI and part of the
 * suite of classes reserved solely for the AI: AIBoard.java, AI.java, AIPlayer.java, LetterTree.java
 * This aids in the encapsulation of the AI as a separate entity
 * from the game. This aids in increasing computation speed, testing, debugging, modularity.
 * (SEE DOCUMENTATION FOR MORE INFORMATION)
 * Part of the suite of classes reserved solely for the AI: AIBoard.java, AI.java, AIPlayer.java, LetterTree.java
 * This class has a larger number of fields due to the amount of data needed to generate a Scrabble play.
 *
 * @author Michael Kyrollos, 101183521
 * @version 1.2
 * @date December 1st 2022
 */

public class AI {

    // Mapping all the point values of letters, this uses less resources than creating a TileBag instance
    private final Map<Character, Integer> points;
    // best score that the AI can produce
    private int bestScore;
    // String representation of the rack
    private final StringBuilder stringRack;
    private final AIBoard aiBoard;
    // the best board that the AI can produce (includes current board and the word that the AI placed)
    static AIBoard bestBoard;
    // Stores reference to the subclass
    private AIMove bestMove;
    private final LetterTree dictionary;
    private String direction;
    // map of the result of crossCheckResults
    private HashMap<List<Integer>, String>  crossCheckResults;

    /**
     * Subclass representing a move that the AI can make
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 1st 2022
     */
    public static class AIMove{

        private final String direction;
        private final int[] pos;
        private final String word;

        /**
         * Constructs an AIMove object.
         * @param word Word to be played.
         * @param lastPos Position of the word.
         * @param direction Direction of the word.
         *
         * @author Michael Kyrollos, 101183521
         * @version 1.2
         * @date December 1st 2022
         */
        public AIMove(String word, int[] lastPos, String direction){
            this.word = word;
            this.pos = lastPos;
            this.direction = direction;
        }

        /**
         * Returns the direction of the move.
         * @return Direction of the move
         *
         * @author Michael Kyrollos, 101183521
         * @version 1.2
         * @date December 1st 2022
         */
        public String getDirection() {
            return direction;
        }

        /**
         * Getter method for position
         *
         * @return position of an AI move
         *
         * @author Michael Kyrollos, 101183521
         * @version 1.2
         * @date December 1st 2022
         */
        public int[] getPos() {
            return pos;
        }

        /**
         * Getter method for word
         *
         * @return word in an AI move
         *
         * @author Michael Kyrollos, 101183521
         * @version 1.2
         * @date December 1st 2022
         */
        public String getWord() {
            return word;
        }

        /**
         * Printing for the AIMove, useful for testing
         *
         * @return String representation of the AIMove
         *
         * @author Michael Kyrollos, 101183521
         * @version 1.2
         * @date December 1st 2022
         */
        @Override
        public String toString() {
            return "AIMove{" +
                    "direction='" + direction + '\'' +
                    ", pos=" + Arrays.toString(pos) +
                    ", word='" + word + '\'' +
                    '}';
        }
    }

    /**
     * Constructs an AI object.
     * @param dictionary LetterTree of the AI.
     * @param aiBoard Board of the AI.
     * @param stringRack String representing rack of the AI.
     */
    public AI(LetterTree dictionary, AIBoard aiBoard, StringBuilder stringRack) {
        points = Map.ofEntries(
                entry('a', 1),entry(('n'), 1),
                entry('b', 3),entry(('o'), 1),
                entry(('c'), 3),entry(('p'), 3),
                entry(('d'), 2),entry(('q'), 10),
                entry(('e'), 1),entry(('r'), 1),
                entry(('f'), 4),entry(('s'), 1),
                entry(('g'), 2),entry(('t'), 1),
                entry(('h'), 4),entry(('u'), 1),
                entry(('i'), 1),entry(('v'), 4),
                entry(('j'), 8),entry(('w'), 4),
                entry(('k'), 5),entry(('x'), 8),
                entry(('l'), 1),entry(('y'), 4),
                entry(('m'), 3),entry(('z'), 10)
        );
        this.aiBoard = aiBoard;
        this.stringRack = stringRack;
        this.direction = "";
        this.crossCheckResults = null;
        this.dictionary = dictionary;
        this.bestScore = 0;
        bestBoard = null;
    }

    /**
     * Helper method to find the position before inputted position
     * @param pos the position that we will be using
     * @return  int[] of the position before pos
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 1st 2022
     */
    private int[] before(int[] pos) {
        int row = pos[0];
        int col = pos[1];
        if (direction.equals("across")) {
            return new int[]{row,col-1};
        }
        else {
            return new int[]{row-1,col};
        }
    }

    /**
     * Helper method to find the position after inputted position
     * @param pos the position that we will be using
     * @return  int[] of the position after pos
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 1st 2022
     */
    private int[] after(int[] pos) {
        int row = pos[0];
        int col = pos[1];
        if (direction.equals("across")) {
            return new int[]{row,col+1};
        }
        else {
            return new int[]{row+1,col};
        }
    }

    /**
     * Helper method to find the position before inputted position that crosses
     * @param pos the position that we will be using
     * @return int[] of the position before pos
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 1st 2022
     */
    private int[] beforeCross(int[] pos) {
        int row = pos[0];
        int col = pos[1];
        if (direction.equals("across")) {
            return new int[]{row-1,col};
        }
        else {
            return new int[]{row,col-1};
        }
    }

    /**
     * Helper method to find the position after inputted position
     * @param pos the position that we will be using
     * @return int[] of the position after pos
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 1st 2022
     */
    private int[] afterCross(int[] pos) {
        int row = pos[0];
        int col = pos[1];
        if (direction.equals("across")) {
            return new int[]{row+1,col};
        }
        else {
            return new int[]{row,col+1};
        }
    }

    /**
     * Finds the score of the play that the AI has made. This will be used when determining the highest scoring plays.
     * This does not take into account special squares.
     * This uses its own calculation system as it allows us to find score without placing anything on the actual
     * scrabble board, everything is done behind the scenes.
     * @param boardToBe AIBoard that we are looking at to find the score
     * @param lastPos int[] of the last position of the last letter in the word to be placed.
     * @return int representation of the score
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 1st 2022
     */
    private int getScore(AIBoard boardToBe, int[] lastPos){
        int[] playPos = lastPos;
        int score = 0;
        // checking where the word ends on the board.
        while (boardToBe.isInBounds(playPos) && boardToBe.getAITile(playPos) != '_'){
            playPos = before(playPos);
        }
        playPos = after(playPos);
        while (boardToBe.isInBounds(playPos) && boardToBe.getAITile(playPos) != '_'){
            // get the letter from that tile
            char currLetter = boardToBe.getAITile(playPos);
            // add the point value of the char placed on that position to the total score
            score += points.get(currLetter);
            //check until the there is no letter placed
            if((boardToBe.isInBounds(beforeCross(playPos)) && boardToBe.getAITile(beforeCross(playPos)) != '_')
                    | (boardToBe.isInBounds(afterCross(playPos)) && boardToBe.getAITile(afterCross(playPos)) != '_')){
                score += getVertScore(boardToBe, playPos);
            }
            // check any letters after the last letter in placed word
            playPos = after(playPos);
        }
        return score;
    }

    /**
     * Helper method for finding score of the AIBoard.This finds the score of words formed vertically when placed on
     * board.
     * @param boardToBe AIBoard that we are looking at to find the score
     * @param lastPos int[] of the last position of the last letter in the word to be placed.
     * @return int representation of the score
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.2
     * @date December 1st 2022
     */
    private int getVertScore(AIBoard boardToBe, int[] lastPos){
        int[] playPos = lastPos;
        int score = 0;
        while (boardToBe.isInBounds(playPos) && boardToBe.getAITile(playPos) != '_'){
            playPos = beforeCross(playPos);
        }
        playPos = afterCross(playPos);
        while (boardToBe.isInBounds(playPos) && boardToBe.getAITile(playPos) != '_'){
            char currLetter = boardToBe.getAITile(playPos);
            score += points.get(currLetter);
            playPos = afterCross(playPos);
        }
        return score;
    }

    /**
     * Check/scan where/what letters can be placed in the positions on the board.
     * i.e. if the square on the board is null, then you can place any letter in the alphabet.
     * @return Hashmap of the positions of letters that can be placed at various positions.
     *
     * @author Michael Kyrollos, 101183521
     * @version 2.1
     * @date December 1st 2022
     */
    private HashMap<List<Integer>, String> crossCheck() {
        // Store the result of the crosscheck that gives possible letters to place and positions.
        HashMap<List<Integer>, String> result = new HashMap<>();
        for (int[] pos: aiBoard.createAllPositions()) {
            if (aiBoard.isFilled(pos)){
                continue;
            }
            // checking for letters before given position
            StringBuilder lettersBefore = new StringBuilder();
            int[] scanPos = pos;
            while (aiBoard.isFilled(beforeCross(scanPos))){
                scanPos = beforeCross(scanPos);
                lettersBefore.insert(0, aiBoard.getAITile(scanPos));
            }
            // checking for letters after given position
            StringBuilder lettersAfter = new StringBuilder();
            scanPos = pos;
            while (aiBoard.isFilled(afterCross(scanPos))){
                scanPos = afterCross(scanPos);
                lettersAfter.append(aiBoard.getAITile(scanPos));
            }
            // out of all letters in the alphabet, legalHere will show the letters
            // that are possible to be played in a position.
            String allLetters = "abcdefghijklmnopqrstuvwxyz";
            StringBuilder legalHere = new StringBuilder();
            // if there's no char in the way, then you can place any letter
            if (lettersBefore.length() == 0 && lettersAfter.length() == 0){
                legalHere = new StringBuilder(allLetters);
            } else {
                //make sure that the letters that we can place adjacent to the other square will possibly
                // form a word.
                for (int i=0; i < allLetters.length(); i++){
                    char letter =  allLetters.charAt(i);
                    String wordFormed = lettersBefore.toString() + letter + lettersAfter;
                    if (dictionary.isWord(wordFormed)) {
                        legalHere.append(letter);
                    }
                }
            }
            List<Integer> arr = Arrays.asList(pos[0],pos[1]);
            // place calculation in the hashmap
            result.put(arr, legalHere.toString());
        }
        return result;
    }

    /**
     * Find the 'anchors' meaning any connection the word can be formed from.
     * i.e. if the square on the board is null, then you can place any letter in the alphabet.
     * @return ArrayList of int[] positions that indicate which anchors the AI has.
     *
     * @author Michael Kyrollos, 101183521
     * @version 2.1
     * @date December 1st 2022
     */
    private ArrayList<int[]> findAnchors() {
        ArrayList<int[]> anchors = new ArrayList<>();
        for (int[] pos: aiBoard.createAllPositions()) {
            boolean empty = aiBoard.isEmpty(pos);
            boolean isNeighborFilled = (aiBoard.isFilled(before(pos)) | aiBoard.isFilled(after(pos))
                    | aiBoard.isFilled(beforeCross(pos)) | aiBoard.isFilled(afterCross(pos)));
            if (empty && isNeighborFilled) {
                anchors.add(pos);
            }
        }
        return anchors;
    }

    /**
     * Find partial and full words that can be formed before a given position and limit.
     * NOTE: this is a recursive function.
     * @param partialWord The partial word that can be placed, crossing the anchor
     * @param currentNode The node corresponding to the partial word
     * @param anchorPos Position of the anchor
     * @param limit How far the word can go before the anchor
     *
     * @author Michael Kyrollos, 101183521
     * @version 2.1
     * @date December 1st 2022
     */
    private void beforePart(String partialWord, LetterTree.LetterTreeNode currentNode, int[] anchorPos, int limit){
        extendAfter(partialWord,currentNode, anchorPos,false);
        if (limit>0){
            //if we have space, set the character value to the node that corresponds to it
            HashMap<Character, LetterTree.LetterTreeNode> children = currentNode.children;
            for (Character nextLetter : children.keySet()) {
                int rackIndex = stringRack.indexOf(String.valueOf(nextLetter));
                if (rackIndex!=-1){
                    // while we still have a rack, call beforePart again and continue the cycle.
                    stringRack.deleteCharAt(rackIndex);
                    beforePart(partialWord + nextLetter,
                            currentNode.children.get(nextLetter),anchorPos,limit - 1);
                    stringRack.append(nextLetter);
                }
            }
        }
    }

    /**
     * Find partial and full words that can be formed after a given position and limit.
     * NOTE: this is a recursive function.
     * @param partialWord The partial word that can be placed, crossing the anchor
     * @param letterTreeNode The node corresponding to the partial word
     * @param nextPos Position of the anchor
     * @param anchorFilled If there's an anchor that already has a letter
     *
     * @author Michael Kyrollos, 101183521
     * @version 2.1
     * @date December 1st 2022
     */
    private void extendAfter(String partialWord, LetterTree.LetterTreeNode letterTreeNode, int[] nextPos, boolean anchorFilled){
        if (!aiBoard.isFilled(nextPos) && letterTreeNode.isWord && anchorFilled){
            // check if the move is legal if the anchor is already filled
            legalMove(partialWord,before(nextPos));
        }
        if (aiBoard.isInBounds(nextPos)) {
            if (aiBoard.isEmpty(nextPos)){
                //if we have space, set the character value to the node that corresponds to it
                HashMap<Character, LetterTree.LetterTreeNode> children = letterTreeNode.children;
                for (Character nextLetter : children.keySet()) {
                    int rackIndex = stringRack.indexOf(String.valueOf(nextLetter));
                    List<Integer> arr = Arrays.asList(nextPos[0],nextPos[1]);
                    // ensure all crosses still form words
                    int crossCheckIndex = crossCheckResults.get(arr).indexOf(nextLetter);
                    // continue calling the recursion until the rack is empty
                    if (rackIndex!=-1 && crossCheckIndex!=-1){
                        stringRack.deleteCharAt(rackIndex);
                        extendAfter(partialWord + nextLetter,
                                letterTreeNode.children.get(nextLetter),after(nextPos),true);
                        stringRack.append(nextLetter);
                    }
                }
            } else {
                Character existingLetter = aiBoard.getAITile(nextPos);
                HashMap<Character, LetterTree.LetterTreeNode> children = letterTreeNode.children;
                for (Character nextLetter : children.keySet()) {
                    // if the next letter already exists in this position then we can extend after it and form a word
                    if (nextLetter==existingLetter){
                        extendAfter(partialWord + existingLetter, letterTreeNode.children.get(existingLetter),after(nextPos),true);
                    }
                }
            }
        }
    }

    /**
     * Allows the AI to make the first word by ensuring that start position is the center.
     * @param word The word that can be placed, crossing the anchor
     * @param letterTreeNode The node corresponding to the word
     *
     * @author Michael Kyrollos, 101183521
     * @version 2.1
     * @date December 1st 2022
     */
    private void makeFirstMove(String word, LetterTree.LetterTreeNode letterTreeNode) {
        int[] startPos = {7, 7};
        // make sure it is a word
        if(dictionary.isWord(word)) {
            legalMove(word, startPos);
        }
        //as with other functions add it to a map and start the recursion until rack is empty
        HashMap<Character, LetterTree.LetterTreeNode> children = letterTreeNode.children;
        for (Character nextLetter : children.keySet()) {
            int rackIndex = stringRack.indexOf(String.valueOf(nextLetter));
            if (rackIndex != -1) {
                stringRack.deleteCharAt(rackIndex);
                makeFirstMove(word + nextLetter,letterTreeNode.children.get(nextLetter));
                stringRack.append(nextLetter);
            }
        }
    }

    /**
     * Determines the highest scoring board produced by the AI and sets the fields accordingly.
     * This will also ensure that the placement is still correct.
     * @param word String representation of the word to be placed.
     * @param lastPos int[] of the last position of the last letter in the word to be placed.
     *
     * @author Michael Kyrollos, 101183521
     * @version 2.1
     * @date December 1st 2022
     */
    private void legalMove(String word, int[] lastPos) {
        AIBoard boardToBe = aiBoard.copyBoard();
        int[] playPos = lastPos;
        StringBuilder fullWord = new StringBuilder(word);
        for (int wordIndex = word.length()-1; wordIndex >= 0; wordIndex--){
            List<Integer> arr = Arrays.asList(playPos[0],playPos[1]);
            if (crossCheckResults.get(arr) != null && crossCheckResults.get(arr).indexOf(word.charAt(wordIndex)) == -1){
                return;
            }
            boardToBe.setAITile(playPos,word.charAt(wordIndex));
            playPos = before(playPos);
        }
        while (boardToBe.isInBounds(playPos) && boardToBe.isFilled(playPos)){
            fullWord.insert(0, boardToBe.getAITile(playPos));
            playPos = before(playPos);
        }
        if (!dictionary.isWord(fullWord.toString())){
            return;
        }
        int score = getScore(boardToBe,lastPos);
        if (score> bestScore){
            bestScore = score;
            bestMove = new AIMove(word, lastPos, direction);
            bestBoard = boardToBe;
        }
    }

    /**
     * Find all possible plays using the possible anchors, and crossing between other words.
     * @return AIMove that is created from the options.
     *
     * @author Michael Kyrollos, 101183521
     * @version 2.1
     * @date December 1st 2022
     */
    public AIMove findAllOptions(){
        int[] startPos = {7,7};
        for (int i=0;i<2;i++){
            if (i==0){
                direction = "across";
            } else {
                direction = "down";
            }
            // find the anchors
            ArrayList<int[]> anchors = findAnchors();
            // check the crossing of words
            crossCheckResults = crossCheck();
            // if it is the first play, then...
            if (!aiBoard.isFilled(startPos)) {
                makeFirstMove("",dictionary.root);
                return bestMove;
            }
            for (int[] anchorPos : anchors){
                // can fill it before anchor
                if (aiBoard.isFilled(before(anchorPos))){
                    int[] scanPos = before(anchorPos);
                    StringBuilder partialWord = new StringBuilder(Character.toString(aiBoard.getAITile(scanPos)));
                    while (aiBoard.isFilled(before(scanPos))) {
                        scanPos = before(scanPos);
                        partialWord.insert(0, aiBoard.getAITile(scanPos));
                    }
                    // ensure word is valid
                    LetterTree.LetterTreeNode pwNode = dictionary.lookup(partialWord.toString());
                    if (pwNode != null){
                        extendAfter(partialWord.toString(),pwNode,anchorPos,false);
                    }
                } else {
                    int limit = 0;
                    int[] scanPos = anchorPos;
                    while (aiBoard.isEmpty(before(scanPos)) && !anchors.contains(before(scanPos))){
                        limit += 1;
                        scanPos = before(scanPos);
                    }
                    beforePart("",dictionary.root,anchorPos,limit);
                }
            }
        }
        return bestMove;
    }
}
