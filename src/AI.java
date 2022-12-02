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
    private int best_score;
    private StringBuilder  rack;
    private AIBoard board;
    private static AIBoard best_board;
    private AIMove best_move;
    private LetterTree dictionary;
    private String direction;
    private HashMap<int[],String> cross_check_results;

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

        @Override
        public String toString() {
            return "AIMove{" +
                    "direction='" + direction + '\'' +
                    ", pos=" + Arrays.toString(pos) +
                    ", word='" + word + '\'' +
                    '}';
        }

        public AIMove(String word, int[] last_pos, String direction){
            this.word = word;
            this.pos = last_pos;
            this.direction = direction;

        }
    }
    public AI(LetterTree dictionary,AIBoard board,StringBuilder rack) {
        this.board = board;
        this.rack = rack;
        this.direction = "";
        this.cross_check_results = null;
        this.dictionary = dictionary;
        this.best_score = 0;
        this.best_board = null;
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
        while (board_to_be.in_bounds(play_pos) && board_to_be.get_tile(play_pos) != '_'){
            play_pos = before(play_pos);
        }
        play_pos = after(play_pos);
        while (board_to_be.in_bounds(play_pos) && board_to_be.get_tile(play_pos) != '_'){
            char curr_letter = board_to_be.get_tile(play_pos);
            score += points.get(Character.valueOf(curr_letter));
            if((board_to_be.in_bounds(before_cross(play_pos)) && board_to_be.get_tile(before_cross(play_pos)) != '_') | (board_to_be.in_bounds(after_cross(play_pos)) && board_to_be.get_tile(after_cross(play_pos)) != '_')){
                score += getVertScore(board_to_be, play_pos);
            }
            play_pos = after(play_pos);
        }
        return score;
    }

    private int getVertScore(AIBoard board_to_be, int[] last_pos){
        int[] play_pos = last_pos;
        int score = 0;
        while (board_to_be.in_bounds(play_pos) && board_to_be.get_tile(play_pos) != '_'){
            play_pos = before_cross(play_pos);
        }
        play_pos = after_cross(play_pos);
        while (board_to_be.in_bounds(play_pos) && board_to_be.get_tile(play_pos) != '_'){
            char curr_letter = board_to_be.get_tile(play_pos);
            score += points.get(Character.valueOf(curr_letter));
            play_pos = after_cross(play_pos);
        }
        return score;
    }
    private void legal_move(String word, int[] last_pos) {
        AIBoard board_to_be = board.copy();
        int[] play_pos = last_pos;
        for (int word_i = word.length()-1; word_i >= 0; word_i--){
            List<Integer> arr = Arrays.asList(play_pos[0],play_pos[1]);
            if (cross_check_results.get(arr) != null && cross_check_results.get(arr).indexOf(word.charAt(word_i)) == -1){
                return;
            }
            board_to_be.set_tile(play_pos,word.charAt(word_i));
            play_pos = before(play_pos);
        }
        int score = getScore(board_to_be,last_pos);
        System.out.println(board_to_be);
        if (score>best_score){
            best_score = score;
            best_move = new AIMove(word,last_pos,direction);
            best_board = board_to_be;
        }
    }

    private HashMap cross_check() {
        HashMap result = new HashMap<List<Integer>,String>();
        for (int[] pos: board.all_positions()) {
            if (board.is_filled(pos)){
                continue;
            }
            String letters_before ="";
            int[] scan_pos = pos;
            while (board.is_filled(before_cross(scan_pos))){
                scan_pos = before_cross(scan_pos);
                letters_before = board.get_tile(scan_pos) + letters_before;
            }
            String letters_after ="";
            scan_pos = pos;
            while (board.is_filled(after_cross(scan_pos))){
                scan_pos = after_cross(scan_pos);
                letters_after = letters_after + board.get_tile(scan_pos);
            }
            String all_letters = "abcdefghijklmnopqrstuvwxyz";
            String legal_here = "";
            if (letters_before.length() == 0 && letters_after.length() == 0){
                legal_here = all_letters;
            } else {
                for (int i=0; i < all_letters.length(); i++){
                    char letter =  all_letters.charAt(i);
                    String word_formed = letters_before + letter + letters_after;
                    if (dictionary.is_word(word_formed)) {
                        legal_here += letter;
                    }
                }
            }
            List<Integer> arr = Arrays.asList(pos[0],pos[1]);;
            result.put(arr,legal_here);
        }
        return result;
    }

    private ArrayList<int[]> find_anchors() {
        ArrayList<int[]> anchors = new ArrayList<>();
        for (int[] pos: board.all_positions()) {
                boolean empty = board.is_empty(pos);
                boolean neighbor_filled = (board.is_filled(before(pos)) | board.is_filled(after(pos))
                        | board.is_filled(before_cross(pos)) | board.is_filled(after_cross(pos)));
                if (empty && neighbor_filled) {
                    anchors.add(pos);
                }
        }
        return anchors;
    }
    private void before_part(String partial_word, LetterTree.LetterTreeNode current_node, int[] anchor_pos, int limit){
        extend_after(partial_word,current_node, anchor_pos,false);
        if (limit>0){
            HashMap<Character, LetterTree.LetterTreeNode> children = current_node.children;
            for (Character next_letter : children.keySet()) {
                int rack_i = rack.indexOf(String.valueOf(next_letter));
                if (rack_i!=-1){
                    rack.deleteCharAt(rack_i);
                    before_part(partial_word + next_letter,
                            current_node.children.get(next_letter),anchor_pos,limit - 1);
                    rack.append(next_letter);
                }
            }
        }

    }
    private void extend_after(String partial_word,LetterTree.LetterTreeNode current_node, int[] next_pos, boolean anchor_filled){
        if (!board.is_filled(next_pos) && current_node.is_word && anchor_filled){
            legal_move(partial_word,before(next_pos));
        }
        if (board.in_bounds(next_pos)) {
            if (board.is_empty(next_pos)){
                HashMap<Character, LetterTree.LetterTreeNode> children = current_node.children;
                for (Character next_letter : children.keySet()) {
                    int rack_i = rack.indexOf(String.valueOf(next_letter));
                    List<Integer> arr = Arrays.asList(next_pos[0],next_pos[1]);
                    int cc_i = cross_check_results.get(arr).indexOf(next_letter);
                    if (rack_i!=-1 && cc_i!=-1){
                        rack.deleteCharAt(rack_i);
                        extend_after(partial_word + next_letter,
                                current_node.children.get(next_letter),after(next_pos),true);
                        rack.append(next_letter);
                    }
                }
            } else {
                Character existing_letter = board.get_tile(next_pos);
                HashMap<Character, LetterTree.LetterTreeNode> children = current_node.children;
                for (Character next_letter : children.keySet()) {
                    if (next_letter==existing_letter){
                        extend_after(partial_word + existing_letter, current_node.children.get(existing_letter),after(next_pos),true);
                    }
                }
            }
        }
    }
    public AIMove find_all_options(){
        for (int i=0;i<1;i++){
            if (i==0){
                direction = "across";
            } else {
                direction = "down";
            }
            ArrayList<int[]> anchors = find_anchors();
            cross_check_results = cross_check();
            for (int[] anchor_pos : anchors){
                if (board.is_filled(before(anchor_pos))){
                    int[] scan_pos = before(anchor_pos);
                    String partial_word = Character.toString(board.get_tile(scan_pos));
                    while (board.is_filled(before(scan_pos))) {
                        scan_pos = before(scan_pos);
                        partial_word = board.get_tile(scan_pos) + partial_word;
                    }
                    LetterTree.LetterTreeNode pw_node = dictionary.lookup(partial_word);
                    if (pw_node != null){
                        extend_after(partial_word,pw_node,anchor_pos,false);
                    }
                } else {
                    int limit = 0;
                    int[] scan_pos = anchor_pos;
                    while (board.is_empty(before(scan_pos)) && !anchors.contains(before(scan_pos))){
                        limit += 1;
                        scan_pos = before(scan_pos);
                    }
                    before_part("",dictionary.root,anchor_pos,limit);
                }
            }

        }
        return best_move;
    }

    public static void main(String[] args)
    {
        LetterTree dict = new LetterTree();
        int size = 15;
        char[][] c = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                c[i][j] = '_';
            }
        }
        AIBoard board = new AIBoard(c,size);
        int[] pos = {7,7};
        board.set_tile(pos,'m');
        pos[1] = 8;
        board.set_tile(pos,'e');
        pos[1] = 9;
        board.set_tile(pos,'d');
        pos[0] = 8;
        pos[1] = 7;
        board.set_tile(pos,'e');
        pos[1] = 8;
        board.set_tile(pos,'z');
        System.out.print(board);
        StringBuilder rack = new StringBuilder("gugeeje");
        AI aiplayer = new AI(dict,board,rack);
        System.out.println(aiplayer.find_all_options());
        System.out.println(best_board);

    }
}

