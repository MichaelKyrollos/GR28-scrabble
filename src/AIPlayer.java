import java.util.ArrayList;
import java.util.HashMap;

/**
 * The AIPlayer class models an AI player in the Scrabble game.
 *
 * @author Pathum Danthanarayana, 101181411
 * @version 1.0
 * @date November 19th, 2022
 */
public class AIPlayer {
    /** Fields **/

    private String name;
    /**
     * Score of the player.
     */
    private int score;
    /**
     * The player's tile rack.
     */
    private RackModel rack;
    /**
     * The board the player is playing on.
     */
    private BoardModel board;

    public static final ScrabbleDictionary SCRABBLE_DICTIONARY= new ScrabbleDictionary();

    private String direction;
    private HashMap<int[],String> cross_check_results;

    public AIPlayer(BoardModel board) {
        this.name = "AI";
        this.board = board;
        this.score = 0;
        this.rack = new RackModel();
        this.rack.fillRack();
        this.direction = "";
    }


    private int[] before(int[] pos) {
        int x = pos[0];
        int y = pos[1];
        if (direction.equals("across")) {
            return new int[]{x,y-1};
        }
        else {
            return new int[]{x-1,y};
        }
    }

    private int[] after(int[] pos) {
        int x = pos[0];
        int y = pos[1];
        if (direction.equals("across")) {
            return new int[]{x,y+1};
        }
        else {
            return new int[]{x+1,y};
        }
    }

    private int[] before_cross(int[] pos) {
        int x = pos[0];
        int y = pos[1];
        if (direction.equals("across")) {
            return new int[]{x-1,y};
        }
        else {
            return new int[]{x,y-1};
        }
    }

    private int[] after_cross(int[] pos) {
        int x = pos[0];
        int y = pos[1];
        if (direction.equals("across")) {
            return new int[]{x+1,y};
        }
        else {
            return new int[]{x,y+1};
        }
    }
    private Square get_square(int[] pos) {
        Square[][] squares = board.getSquares();
        Square square = squares[pos[0]][pos[1]];
        return square;
    }
    private Character get_letter(int[] pos) {
        Square square = get_square(pos);
        return square.getTile().getLetter();
    }
    private boolean is_filled(int[] pos){
        Square square = get_square(pos);
        if (square.getTile() != null){
            return true;
        }
        return false;
    }
    
    private void legal_move(String word, int[] last_pos) {
        BoardModel board_to_be = board.copyBoardSquaresToAnother();
        int[] play_pos = last_pos;
        int word_i = word.length()-1;
        while (word_i >= 0){
            Tile tile = new Tile(word.charAt(word_i),0);
            board_to_be.getSquares()[play_pos[0]][play_pos[1]].placeSquare(tile);
        }
        System.out.println(board);
    }
    private HashMap cross_check() {
        HashMap result = new HashMap<int[],String>();

        int[] pos = new int[15];
        int[] scan_pos;
        for (int x =0; x < 15; x++) {
            for (int y =0; y < 15; y++){
                pos[0] = x;
                pos[1] = y;
                if (is_filled(pos)){
                    continue;
                }
                String letters_before ="";
                scan_pos = pos;
                while (is_filled(before_cross(scan_pos))){
                    scan_pos = before_cross(scan_pos);
                    letters_before = get_letter(scan_pos) + letters_before;
                }
                String letters_after ="";
                scan_pos = pos;
                while (is_filled(after_cross(scan_pos))){
                    scan_pos = after_cross(scan_pos);
                    letters_after = get_letter(scan_pos) + letters_after;
                }
                String all_letters = "abcdefghijklmnopqrstuvwxyz";
                String legal_here = "";
                if (letters_before.length() == 0 && letters_after.length() == 0){
                    legal_here = all_letters;
                } else {
                    for (int i=0; i < 26; i++){
                        char letter =  all_letters.charAt(i);
                        String word_formed = letters_before + letter + letters_after;
                        if (SCRABBLE_DICTIONARY.validateWord(word_formed)) {
                            legal_here += letter;
                        }
                    }
                }
                result.put(pos,legal_here);
                }
        }
        return result;
    }

    private ArrayList find_anchors() {
        ArrayList<int[]> anchors = new ArrayList<>();
        int[] pos = new int[15];
        for (int x =0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                pos[0] = x;
                pos[1] = y;
                boolean empty = !is_filled(pos);
                boolean neighbor_filled = (is_filled(before(pos)) | is_filled(after(pos))
                        | is_filled(before_cross(pos)) | is_filled(after_cross(pos)));
                if (empty && neighbor_filled) {
                    anchors.add(pos);
                }
                }
            }
        return anchors;
        }
    private String get_letters(){
        ArrayList<Tile> tiles = rack.getTiles();
        String result = "";
        for (Tile tile : tiles){
            result += tile.getLetter();
        }
        return result;
    }
    private void extend_after(String partial_word,ScrabbleDictionary.DictionaryNode current_node, int[] next_pos, boolean anchor_filled){
        if (!is_filled(next_pos) && current_node.terminal && anchor_filled){
            legal_move(partial_word,before(next_pos));
        }
        if (next_pos[0] < 15 && next_pos[1]<15) {
            if (!is_filled(next_pos)){
                int l = current_node.children.length;
                String rack_letters = get_letters();
                for (int i = 0; i < l; i++){
                    String next_letter = current_node.intToChar(i);
                    String cc_result = cross_check_results.get(next_pos);
                    if (rack_letters.indexOf(next_letter)!=-1 && cc_result.indexOf(next_letter)!=-1){
                        Tile removed = rack.removeTile(next_letter.charAt(0));
                        extend_after(partial_word + next_letter,
                                current_node.children[i],after(next_pos),true);
                        rack.getTiles().add(removed);
                    }
                }
            } else {
                char existing_letter = get_letter(next_pos);
                int l = current_node.children.length;
                for (int i = 0; i < l; i++){
                    String test_letter = current_node.intToChar(i);
                    if (test_letter.charAt(0)==existing_letter){
                        extend_after(partial_word + existing_letter, current_node.children[i],after(next_pos),true);
                    }
                }
            }
        }
    }
    private void before_part(String partial_word, ScrabbleDictionary.DictionaryNode current_node, int[] anchor_pos, int limit){
        extend_after(partial_word,current_node, anchor_pos,false);
        if (limit>0){
            int l = current_node.children.length;
            String rack_letters = get_letters();
            for (int i = 0; i < l; i++){
                String next_letter = current_node.intToChar(i);
                if (rack_letters.indexOf(next_letter)!=-1){
                    Tile removed = rack.removeTile(next_letter.charAt(0));
                    before_part(partial_word + next_letter,
                            current_node.children[i],anchor_pos,limit - 1);
                    rack.getTiles().add(removed);
                }
            }
        }

    }
    public void find_all_options(){
        for (int i=0;i<2;i++){
            if (i==0){
                direction = "across";
            } else {
                direction = "down";
            }
            ArrayList<int[]> anchors = find_anchors();
            cross_check_results = cross_check();
            for (int[] anchor_pos : anchors){
                if (is_filled(before(anchor_pos))){
                    int[] scan_pos = before(anchor_pos);
                    String partial_word = get_letter(scan_pos).toString();
                    while (is_filled(before(scan_pos))) {
                        scan_pos = before(scan_pos);
                        partial_word = get_letter(scan_pos) + partial_word;
                    }
                    ScrabbleDictionary.DictionaryNode pw_node = SCRABBLE_DICTIONARY.dictionary.wordLookup(partial_word.toCharArray());
                    if (pw_node == null){
                        extend_after(partial_word,pw_node,anchor_pos,false);
                    }
                } else {
                    int limit = 0;
                    int[] scan_pos = anchor_pos;
                    while (!is_filled(before(scan_pos)) && anchors.contains(before(scan_pos))){
                        limit += 1;
                        scan_pos = before(scan_pos);
                    }
                    before_part("",SCRABBLE_DICTIONARY.dictionary,anchor_pos,limit);
                }
            }

        }

}
    public RackModel getRack()
    {
        return this.rack;
    }



}
