import java.util.ArrayList;

public class AIBoard {
    private final char[][] tiles;
    private final int size;

    public AIBoard(char[][] tiles, int size) {
        this.tiles = tiles;
        this.size = size;
    }

    public ArrayList<int[]> all_positions(){
        ArrayList<int[]> result = new ArrayList<>();
        for(int row=0;row<size;row++){
            for(int col=0;col<size;col++) {
                int[] pos = {row,col};
                result.add(pos);
            }
        }
        return result;
    }

    public char get_tile(int[] pos){
        return tiles[pos[0]][pos[1]];
    }

    public char set_tile(int[] pos, char tile){
        int row = pos[0];
        int col = pos[1];
        return tiles[row][col] = tile;
    }

    public boolean in_bounds(int[] pos){
        int row = pos[0];
        int col = pos[1];
        return (row>=0) && (row < size) && (col>=0) && (col < size);
    }

    public boolean is_empty(int[] pos) {
        return in_bounds(pos) && get_tile(pos)=='_';
    }

    public boolean is_filled(int[] pos) {
        return in_bounds(pos) && get_tile(pos)!='_';
    }

    public AIBoard copy(){
        char[][] cloned = new char[size][size];
        for (int[] pos: all_positions()){
            cloned[pos[0]][pos[1]] =  get_tile(pos);
        }
        AIBoard copy = new AIBoard(cloned,size);
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder();
        for(int row=0;row<size;row++){
            for(int col=0;col<size;col++) {
                int[] pos = {row,col};
                repr.append(get_tile(pos)).append(" ");
            }
            repr.append("\n");
        }
        return repr.toString();
    }

//    for testing
//    public static void main(String[] args)
//    {
//        int size = 7;
//        char[][] c = new char[size][size];
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                    c[i][j] = '_';
//            }
//        }
//        AIBoard board = new AIBoard(c,size);
//        int[] pos = {1,1};
//        board.set_tile(pos,'o');
//        pos[0] = 2;
//        board.set_tile(pos,'f');
//        System.out.print(board);
//    }
}
