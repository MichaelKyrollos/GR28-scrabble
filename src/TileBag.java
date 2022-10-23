// Import libraries
import java.util.*;

/**
 * The TileBag class models a tile bag for the game of Scrabble.
 * A tile bag contains 100 tiles in total; each letter appears a
 * pre-defined number of times within the bag, and also has a pre-defined
 * point value associated with them.
 *
 * @author Pathum Danthanarayana, 101181411
 * @version 1.0
 * @date October 22, 2022
 */
public class TileBag {

    /** Fields **/
    private ArrayList<Tile> tiles;
    private static final Map<Character, Integer> frequencyValues = new HashMap<>();
    private static final Map<Integer, ArrayList<Character>> pointValues = new HashMap<>();

    /** Constructor **/
    public TileBag()
    {
        // Initialize ArrayList of tiles
        tiles = new ArrayList<>();

        // Prepare the tiles
        this.prepareTiles();

        // Prepare the tile bag
        this.prepareTileBag();
    }

    /** Methods **/

    /**
     * The prepareTiles method prepares all the tiles by assigning
     * the appropriate frequency and point values to each letter
     * that will be present in the tile bag.
     */
    private void prepareTiles()
    {
        // Set up frequency values
        frequencyValues.put('A', 9);
        frequencyValues.put('B', 2);
        frequencyValues.put('C', 2);
        frequencyValues.put('D', 4);
        frequencyValues.put('E', 12);
        frequencyValues.put('F', 2);
        frequencyValues.put('G', 3);
        frequencyValues.put('H', 2);
        frequencyValues.put('I', 9);
        frequencyValues.put('J', 1);
        frequencyValues.put('K', 1);
        frequencyValues.put('L', 4);
        frequencyValues.put('M', 2);
        frequencyValues.put('N', 6);
        frequencyValues.put('O', 8);
        frequencyValues.put('P', 2);
        frequencyValues.put('Q', 1);
        frequencyValues.put('R', 6);
        frequencyValues.put('S', 4);
        frequencyValues.put('T', 6);
        frequencyValues.put('U', 4);
        frequencyValues.put('V', 2);
        frequencyValues.put('W', 2);
        frequencyValues.put('X', 1);
        frequencyValues.put('Y', 2);
        frequencyValues.put('Z', 1);
        frequencyValues.put(' ', 2);

        // Set up all possible point values
        pointValues.put(0, new ArrayList<>());
        pointValues.put(1, new ArrayList<>());
        pointValues.put(2, new ArrayList<>());
        pointValues.put(3, new ArrayList<>());
        pointValues.put(4, new ArrayList<>());
        pointValues.put(5, new ArrayList<>());
        pointValues.put(8, new ArrayList<>());
        pointValues.put(10, new ArrayList<>());

        // Letters with 0 points (blank tile)
        pointValues.get(0).add(' ');

        // Letters with 1 point
        pointValues.get(1).add('A');
        pointValues.get(1).add('E');
        pointValues.get(1).add('I');
        pointValues.get(1).add('O');
        pointValues.get(1).add('U');
        pointValues.get(1).add('L');
        pointValues.get(1).add('N');
        pointValues.get(1).add('S');
        pointValues.get(1).add('T');
        pointValues.get(1).add('R');

        // Letters with 2 points
        pointValues.get(2).add('D');
        pointValues.get(2).add('G');

        // Letters with 3 points
        pointValues.get(3).add('B');
        pointValues.get(3).add('C');
        pointValues.get(3).add('M');
        pointValues.get(3).add('P');

        // Letters with 4 points
        pointValues.get(4).add('F');
        pointValues.get(4).add('H');
        pointValues.get(4).add('V');
        pointValues.get(4).add('W');
        pointValues.get(4).add('Y');

        // Letters with 5 points
        pointValues.get(5).add('K');

        // Letters with 8 points
        pointValues.get(8).add('J');
        pointValues.get(8).add('X');

        // Letters with 10 points
        pointValues.get(10).add('Q');
        pointValues.get(10).add('Z');
    }

    /**
     * The prepareTileBag method creates and adds all the necessary tiles for the tile bag.
     */
    private void prepareTileBag()
    {
        // Traverse through each point value
        for (Integer pointValue : pointValues.keySet())
        {
            // Traverse through each letter for the current point value
            for (Character letter : pointValues.get(pointValue))
            {
                // Create a Tile object using the current letter and point value
                Tile tile = new Tile(letter, pointValue);
                // Determine the frequency of the tile
                int tileFrequency = frequencyValues.get(letter);

                // Repeatedly add the tile to the tile bag according to its frequency value
                for (int i = 0; i < tileFrequency; i++)
                {
                    this.addBag(tile);
                }
            }
        }
    }

    /**
     * The dealTile method deals a tile from the tile bag to the player.
     * @return a tile that will be dealt to the corresponding player
     */
    public Tile dealTile()
    {
        // Shuffle the tile bag before dealing a tile to the player
        Collections.shuffle(tiles);

        // Create new instance of Random
        Random random = new Random();
        // Generate a random valid index
        int randomIndex = random.nextInt(tiles.size());

        // Return the tile that was drawn at the random index
        return tiles.remove(randomIndex);
    }

    /**
     * The addTile method adds a tile to the tile bag.
     * @param tile - The tile that will be added to the tile bag
     */
    public void addBag(Tile tile)
    {
        // Add the specified tile to the tile bag
        tiles.add(tile);
    }

    /**
     * The toString method overrides the default toString method to return a String
     * describing the tile bag, including all the tiles within the bag.
     *
     * @return a String describing the tile bag
     */
    @Override
    public String toString()
    {
        // Initialize string and counter
        String tileBagStr = "Tile bag:\n";
        int counter = 1;

        // Traverse through all the tiles in the tile bag
        for (Tile tile : tiles)
        {
            tileBagStr += "#" + counter + " Tile letter: " + tile.getLetter() + " Points: " + tile.getValue() + "\n";
            counter += 1;
        }
        return tileBagStr;
    }

    /** Main method (for testing) **/
    public static void main(String[] args)
    {
        // Create a tile bag
        TileBag tileBag = new TileBag();
        // Print out all the tiles in the tile bag
        System.out.print(tileBag);
    }
}
