import java.util.*;
import java.util.HashMap;

import static java.util.Map.entry;

public class AI {

    private Map<Character, Integer> points = Map.ofEntries(
            entry(Character.valueOf('a'), 1),entry(Character.valueOf('n'), 1),
            entry(Character.valueOf('b'), 3),entry(Character.valueOf('o'), 1),
            entry(Character.valueOf('c'), 3),entry(Character.valueOf('p'), 3),
            entry(Character.valueOf('d'), 2),entry(Character.valueOf('q'), 10),
            entry(Character.valueOf('e'), 1),entry(Character.valueOf('r'), 1),
            entry(Character.valueOf('f'), 4),entry(Character.valueOf('s'), 1),
            entry(Character.valueOf('g'), 2),entry(Character.valueOf('t'), 1),
            entry(Character.valueOf('h'), 4),entry(Character.valueOf('u'), 1),
            entry(Character.valueOf('i'), 1),entry(Character.valueOf('v'), 4),
            entry(Character.valueOf('j'), 8),entry(Character.valueOf('w'), 4),
            entry(Character.valueOf('k'), 5),entry(Character.valueOf('x'), 8),
            entry(Character.valueOf('l'), 1),entry(Character.valueOf('y'), 4),
            entry(Character.valueOf('m'), 3),entry(Character.valueOf('z'), 10)
            );
    private int bestScore;
    private StringBuilder stringRack;
    private AIBoard aiBoard;
    static AIBoard bestBoard;
    private AIMove bestMove;
    private LetterTree dictionary;
    private String direction;
    private HashMap<int[],String> crossCheckResults;

    public class AIMove{
        private final String direction;
        private final int[] pos;
        private final String word;

        public String getDirection() {
            return direction;
        }

        public int[] getPos() {
            return pos;
        }

        public String getWord() {
            return word;
        }

        public AIMove(String word, int[] last_pos, String direction){
            this.word = word;
            this.pos = last_pos;
            this.direction = direction;

        }

        @Override
        public String toString() {
            return "AIMove{" +
                    "direction='" + direction + '\'' +
                    ", pos=" + Arrays.toString(pos) +
                    ", word='" + word + '\'' +
                    '}';
        }
    }
    public AI(LetterTree dictionary, AIBoard aiBoard, StringBuilder stringRack) {
        this.aiBoard = aiBoard;
        this.stringRack = stringRack;
        this.direction = "";
        this.crossCheckResults = null;
        this.dictionary = dictionary;
        this.bestScore = 0;
        this.bestBoard = null;
    }


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

    private int[] before_cross(int[] pos) {
        int row = pos[0];
        int col = pos[1];
        if (direction.equals("across")) {
            return new int[]{row-1,col};
        }
        else {
            return new int[]{row,col-1};
        }
    }

    private int[] after_cross(int[] pos) {
        int row = pos[0];
        int col = pos[1];
        if (direction.equals("across")) {
            return new int[]{row+1,col};
        }
        else {
            return new int[]{row,col+1};
        }
    }

    private int getScore(AIBoard board_to_be, int[] last_pos){
        int[] play_pos = last_pos;
        int score = 0;
        while (board_to_be.isInBounds(play_pos) && board_to_be.getAITile(play_pos) != '_'){
            play_pos = before(play_pos);
        }
        play_pos = after(play_pos);
        while (board_to_be.isInBounds(play_pos) && board_to_be.getAITile(play_pos) != '_'){
            char curr_letter = board_to_be.getAITile(play_pos);
            score += points.get(Character.valueOf(curr_letter));
            if((board_to_be.isInBounds(before_cross(play_pos)) && board_to_be.getAITile(before_cross(play_pos)) != '_') | (board_to_be.isInBounds(after_cross(play_pos)) && board_to_be.getAITile(after_cross(play_pos)) != '_')){
                score += getVertScore(board_to_be, play_pos);
            }
            play_pos = after(play_pos);
        }
        return score;
    }

    private int getVertScore(AIBoard board_to_be, int[] last_pos){
        int[] play_pos = last_pos;
        int score = 0;
        while (board_to_be.isInBounds(play_pos) && board_to_be.getAITile(play_pos) != '_'){
            play_pos = before_cross(play_pos);
        }
        play_pos = after_cross(play_pos);
        while (board_to_be.isInBounds(play_pos) && board_to_be.getAITile(play_pos) != '_'){
            char curr_letter = board_to_be.getAITile(play_pos);
            score += points.get(Character.valueOf(curr_letter));
            play_pos = after_cross(play_pos);
        }
        return score;
    }
    private void legalMove(String word, int[] last_pos) {
        AIBoard board_to_be = aiBoard.copyBoard();
        int[] play_pos = last_pos;
        String full_word = word;
        for (int word_i = word.length()-1; word_i >= 0; word_i--){
            List<Integer> arr = Arrays.asList(play_pos[0],play_pos[1]);
            if (crossCheckResults.get(arr) != null && crossCheckResults.get(arr).indexOf(word.charAt(word_i)) == -1){
                return;
            }
            board_to_be.setAITile(play_pos,word.charAt(word_i));
            play_pos = before(play_pos);
        }
        while (board_to_be.isInBounds(play_pos) && board_to_be.isFilled(play_pos)){
            full_word = board_to_be.getAITile(play_pos) + full_word;
            play_pos = before(play_pos);
        }
        if (!dictionary.isWord(full_word)){
            return;
        }
        int score = getScore(board_to_be,last_pos);
        if (score> bestScore){
            bestScore = score;
            bestMove = new AIMove(word,last_pos,direction);
            bestBoard = board_to_be;
        }
    }

    private HashMap crossCheck() {
        HashMap result = new HashMap<List<Integer>,String>();
        for (int[] pos: aiBoard.createAllPositions()) {
            if (aiBoard.isFilled(pos)){
                continue;
            }
            String letters_before ="";
            int[] scan_pos = pos;
            while (aiBoard.isFilled(before_cross(scan_pos))){
                scan_pos = before_cross(scan_pos);
                letters_before = aiBoard.getAITile(scan_pos) + letters_before;
            }
            String letters_after ="";
            scan_pos = pos;
            while (aiBoard.isFilled(after_cross(scan_pos))){
                scan_pos = after_cross(scan_pos);
                letters_after = letters_after + aiBoard.getAITile(scan_pos);
            }
            String all_letters = "abcdefghijklmnopqrstuvwxyz";
            String legal_here = "";
            if (letters_before.length() == 0 && letters_after.length() == 0){
                legal_here = all_letters;
            } else {
                for (int i=0; i < all_letters.length(); i++){
                    char letter =  all_letters.charAt(i);
                    String word_formed = letters_before + letter + letters_after;
                    if (dictionary.isWord(word_formed)) {
                        legal_here += letter;
                    }
                }
            }
            List<Integer> arr = Arrays.asList(pos[0],pos[1]);;
            result.put(arr,legal_here);
        }
        return result;
    }

    private ArrayList<int[]> findAnchors() {
        ArrayList<int[]> anchors = new ArrayList<>();
        for (int[] pos: aiBoard.createAllPositions()) {
                boolean empty = aiBoard.isEmpty(pos);
                boolean neighbor_filled = (aiBoard.isFilled(before(pos)) | aiBoard.isFilled(after(pos))
                        | aiBoard.isFilled(before_cross(pos)) | aiBoard.isFilled(after_cross(pos)));
                if (empty && neighbor_filled) {
                    anchors.add(pos);
                }
        }
        return anchors;
    }
    private void beforePart(String partial_word, LetterTree.LetterTreeNode current_node, int[] anchor_pos, int limit){
        extendAfter(partial_word,current_node, anchor_pos,false);
        if (limit>0){
            HashMap<Character, LetterTree.LetterTreeNode> children = current_node.children;
            for (Character next_letter : children.keySet()) {
                int rack_i = stringRack.indexOf(String.valueOf(next_letter));
                if (rack_i!=-1){
                    stringRack.deleteCharAt(rack_i);
                    beforePart(partial_word + next_letter,
                            current_node.children.get(next_letter),anchor_pos,limit - 1);
                    stringRack.append(next_letter);
                }
            }
        }

    }
    private void extendAfter(String partial_word, LetterTree.LetterTreeNode current_node, int[] next_pos, boolean anchor_filled){
        if (!aiBoard.isFilled(next_pos) && current_node.isWord && anchor_filled){
            legalMove(partial_word,before(next_pos));
        }
        if (aiBoard.isInBounds(next_pos)) {
            if (aiBoard.isEmpty(next_pos)){
                HashMap<Character, LetterTree.LetterTreeNode> children = current_node.children;
                for (Character next_letter : children.keySet()) {
                    int rack_i = stringRack.indexOf(String.valueOf(next_letter));
                    List<Integer> arr = Arrays.asList(next_pos[0],next_pos[1]);
                    int cc_i = crossCheckResults.get(arr).indexOf(next_letter);
                    if (rack_i!=-1 && cc_i!=-1){
                        stringRack.deleteCharAt(rack_i);
                        extendAfter(partial_word + next_letter,
                                current_node.children.get(next_letter),after(next_pos),true);
                        stringRack.append(next_letter);
                    }
                }
            } else {
                Character existing_letter = aiBoard.getAITile(next_pos);
                HashMap<Character, LetterTree.LetterTreeNode> children = current_node.children;
                for (Character next_letter : children.keySet()) {
                    if (next_letter==existing_letter){
                        extendAfter(partial_word + existing_letter, current_node.children.get(existing_letter),after(next_pos),true);
                    }
                }
            }
        }
    }

    private void makeFirstMove(String word, LetterTree.LetterTreeNode current_node) {
        int[] start_pos = {7, 7};
        if(dictionary.isWord(word)) {
            legalMove(word, start_pos);
        }
        HashMap<Character, LetterTree.LetterTreeNode> children = current_node.children;
        for (Character next_letter : children.keySet()) {
            int rack_i = stringRack.indexOf(String.valueOf(next_letter));
            if (rack_i != -1) {
                stringRack.deleteCharAt(rack_i);
                makeFirstMove(word + next_letter,current_node.children.get(next_letter));
                stringRack.append(next_letter);
            }
        }
    }



    public AIMove findAllOptions(){
        int[] start_pos = {7,7};
        for (int i=0;i<2;i++){
            if (i==0){
                direction = "across";
            } else {
                direction = "down";
            }
            ArrayList<int[]> anchors = findAnchors();
            crossCheckResults = crossCheck();
            if (!aiBoard.isFilled(start_pos)) {
                makeFirstMove("",dictionary.root);
                return bestMove;
            }
            for (int[] anchor_pos : anchors){
                if (aiBoard.isFilled(before(anchor_pos))){
                    int[] scan_pos = before(anchor_pos);
                    String partial_word = Character.toString(aiBoard.getAITile(scan_pos));
                    while (aiBoard.isFilled(before(scan_pos))) {
                        scan_pos = before(scan_pos);
                        partial_word = aiBoard.getAITile(scan_pos) + partial_word;
                    }
                    LetterTree.LetterTreeNode pw_node = dictionary.lookup(partial_word);
                    if (pw_node != null){
                        extendAfter(partial_word,pw_node,anchor_pos,false);
                    }
                } else {
                    int limit = 0;
                    int[] scan_pos = anchor_pos;
                    while (aiBoard.isEmpty(before(scan_pos)) && !anchors.contains(before(scan_pos))){
                        limit += 1;
                        scan_pos = before(scan_pos);
                    }
                    beforePart("",dictionary.root,anchor_pos,limit);
                }
            }

        }
        return bestMove;
    }
}
