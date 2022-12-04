import java.util.*;

/**
 * The TileBag class models a tile bag for the game of Scrabble.
 * A tile bag contains 100 tiles in total; each letter appears a
 * pre-defined number of times within the bag, and also has a pre-defined
 * point value associated with them.
 *
 * @author Pathum Danthanarayana, 101181411
 * @author Yehan De Sivla
 * @author Amin Zeina, 101186297
 * @version 3.0
 * @date November 14, 2022
 */
public class TileBag {

    /** Fields **/
    private final ArrayList<Tile> tiles;
    private static final Map<Character, Integer> FREQUENCY_VALUES = new HashMap<>();
    private static final Map<Integer, ArrayList<Character>> POINT_VALUES = new HashMap<>();

    /** Point values for each letter according to Hasbro.com */
    private static final int[] POSSIBLE_POINT_VALUES = {0,1,2,3,4,5,8,10};
    private static final List<char[]> LETTERS_WITH_POINT = new ArrayList<>();
    private static final char[] LETTERS_WITH_POINT_0 = {BlankTile.DEFAULT_BLANK_TILE_TEXT};
    private static final char[] LETTERS_WITH_POINT_1 = {'A', 'E', 'I', 'O', 'U', 'L', 'N', 'S', 'T', 'R'};
    private static final char[] LETTERS_WITH_POINT_2 = {'D','G'};
    private static final char[] LETTERS_WITH_POINT_3 = {'B','C', 'M', 'P'};
    private static final char[] LETTERS_WITH_POINT_4 = {'F','H', 'V', 'W', 'Y'};
    private static final char[] LETTERS_WITH_POINT_5 = {'K'};
    private static final char[] LETTERS_WITH_POINT_8 = {'J','X'};
    private static final char[] LETTERS_WITH_POINT_10 = {'Q','Z'};

    /** Constructor **/
    public TileBag()
    {
        this.tiles = new ArrayList<>();
        this.constructLetterPoints();
        this.prepareTiles();
        this.fillBag();
    }

    /**
     * Copy constructor for TileBag.
     * @param bag TileBag to be copied.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 03, 2022
     */
    public TileBag(TileBag bag) {
        this.tiles = bag.tiles;
    }

    public void fillBag() {
        this.tiles.clear();
        this.prepareTileBag();

    }

    /**
     * Constructs an ArrayList containing all the letters corresponding to a point value
     * @author Yehan De Silva
     * @author Amin Zeina, 101186297
     * @version 3.0
     * @date November 14, 2022
     */
    private void constructLetterPoints() {
        LETTERS_WITH_POINT.add(LETTERS_WITH_POINT_0);
        LETTERS_WITH_POINT.add(LETTERS_WITH_POINT_1);
        LETTERS_WITH_POINT.add(LETTERS_WITH_POINT_2);
        LETTERS_WITH_POINT.add(LETTERS_WITH_POINT_3);
        LETTERS_WITH_POINT.add(LETTERS_WITH_POINT_4);
        LETTERS_WITH_POINT.add(LETTERS_WITH_POINT_5);
        LETTERS_WITH_POINT.add(LETTERS_WITH_POINT_8);
        LETTERS_WITH_POINT.add(LETTERS_WITH_POINT_10);
    }

    /**
     * The prepareTiles method prepares all the tiles by assigning
     * the appropriate frequency and point values to each letter
     * that will be present in the tile bag.
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @author Amin Zeina, 101186297
     * @version 3.0
     * @date November 14, 2022
     */
    private void prepareTiles()
    {
        // Set up frequency values
        FREQUENCY_VALUES.put('A', 9);
        FREQUENCY_VALUES.put('B', 2);
        FREQUENCY_VALUES.put('C', 2);
        FREQUENCY_VALUES.put('D', 4);
        FREQUENCY_VALUES.put('E', 12);
        FREQUENCY_VALUES.put('F', 2);
        FREQUENCY_VALUES.put('G', 3);
        FREQUENCY_VALUES.put('H', 2);
        FREQUENCY_VALUES.put('I', 9);
        FREQUENCY_VALUES.put('J', 1);
        FREQUENCY_VALUES.put('K', 1);
        FREQUENCY_VALUES.put('L', 4);
        FREQUENCY_VALUES.put('M', 2);
        FREQUENCY_VALUES.put('N', 6);
        FREQUENCY_VALUES.put('O', 8);
        FREQUENCY_VALUES.put('P', 2);
        FREQUENCY_VALUES.put('Q', 1);
        FREQUENCY_VALUES.put('R', 6);
        FREQUENCY_VALUES.put('S', 4);
        FREQUENCY_VALUES.put('T', 6);
        FREQUENCY_VALUES.put('U', 4);
        FREQUENCY_VALUES.put('V', 2);
        FREQUENCY_VALUES.put('W', 2);
        FREQUENCY_VALUES.put('X', 1);
        FREQUENCY_VALUES.put('Y', 2);
        FREQUENCY_VALUES.put('Z', 1);
        FREQUENCY_VALUES.put(BlankTile.DEFAULT_BLANK_TILE_TEXT, BlankTile.DEFAULT_FREQUENCY_BLANK_TILE);

        // Set up all possible point values
        for (int possiblePointValue : POSSIBLE_POINT_VALUES) {
            POINT_VALUES.put(possiblePointValue, new ArrayList<>());
        }

        //Loop through each possible point value
        for (int pointValueIndex = 0; pointValueIndex < POSSIBLE_POINT_VALUES.length; pointValueIndex++) {
            //Add each letter corresponding to the point value
            for (int letterIndex = 0; letterIndex < LETTERS_WITH_POINT.get(pointValueIndex).length; letterIndex++) {
                POINT_VALUES.get(POSSIBLE_POINT_VALUES[pointValueIndex]).add(LETTERS_WITH_POINT.get(pointValueIndex)[letterIndex]);
            }
        }
    }

    /**
     * The prepareTileBag method creates and adds all the necessary tiles for the tile bag.
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 2.0
     * @date November 11, 2022
     */
    private void prepareTileBag()
    {
        // Traverse through each point value
        for (Integer pointValue : POINT_VALUES.keySet()) {
            // Traverse through each letter for the current point value
            for (char letter : POINT_VALUES.get(pointValue)) {
                // Determine the frequency of the tile
                int tileFrequency = FREQUENCY_VALUES.get(letter);
                if (Character.isLetter(letter)) {
                    this.addTileWithFrequency(letter, pointValue, tileFrequency);
                }
                else if (letter == BlankTile.DEFAULT_BLANK_TILE_TEXT) {
                    this.addBlankTileWithFrequency(tileFrequency);
                }
            }
        }
    }

    /**
     * Helper function to create a tile and add it to the tile bag with the given frequency.
     * @param letter Letter of tile.
     * @param pointValue Point value of tile.
     * @param frequency Frequency of tile.
     *
     * @author Yehan De Silva
     * @version 3.0
     * @date November 17, 2022
     */
    private void addTileWithFrequency(char letter, int pointValue, int frequency) {
        // Repeatedly add the tile to the tile bag according to its frequency value
        for (int i = 0; i < frequency; i++) {
            this.addTile(new Tile(letter, pointValue));
        }
    }

    /**
     * Helper function to create a blank tile and it to the tile bag with the given frequency.
     * @param frequency Frequency of tile.
     *
     * @author Yehan De Silva
     * @version 3.0
     * @date November 17, 2022
     */
    private void addBlankTileWithFrequency(int frequency) {
        // Repeatedly add the tile to the tile bag according to its frequency value
        for (int i = 0; i < frequency; i++) {
            this.addTile(new BlankTile());
        }
    }

    /**
     * The addTile method adds a tile to the tile bag.
     * @author Pathum Danthanarayana, 101181411
     *
     * @param tile - The tile that will be added to the tile bag
     */
    public void addTile(Tile tile)
    {
        tiles.add(tile);
    }

    /**
     * The dealTile method deals a tile from the tile bag to the player.
     * @author Pathum Danthanarayana, 101181411
     *
     * @return a tile that will be dealt to the corresponding player
     */
    public Tile dealTile()
    {
        // Generate a random valid index
        Random random = new Random();
        int randomIndex = random.nextInt(tiles.size());
        // Return the tile that was drawn at the random index
        return tiles.remove(randomIndex);
    }

    /**
     * Returns a corresponding tile given the String letter
     * @return Tile that contains inputted letter
     *
     * @author Michael Kyrollos
     * @version 1.0
     * @date November 13, 2022
     */
    public Tile takeTile(char letter)
    {
        for (Tile tile : tiles)
        {
            if (letter == tile.getLetter()) {
                return tile;
            }
        }
        return null;
    }

    /**
     * Returns true if the tile bag is empty, false otherwise
     *
     * @author Amin Zeina, 101186297
     * @version 1.0
     *
     * @return true if the tile bag is empty, false otherwise
     */
    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    /**
     * Returns the tiles within the tile bag.
     * @return ArrayList of tiles within the tile bag.
     *
     * @author Yehan De Silva
     * @version 1.0
     * @date November 13, 2022
     */
    public ArrayList<Tile> getTiles() {
        return this.tiles;
    }

    /**
     * The toString method overrides the default toString method to return a String
     * describing the tile bag, including all the tiles within the bag.
     * @return a String describing the tile bag
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 3.0
     * @date November 22, 2022
     */
    @Override
    public String toString()
    {
        return "Tile bag:\n" + this.tilesToString(this.tiles);
    }

    /**
     * Returns a formatted string of the given tiles.
     * @param tiles Tiles to be written in a string format.
     * @return Formatted string of the given tiles.
     * @author Yehan De Silva
     * @version 3.0
     * @date November 22, 2022
     */
    public String tilesToString(ArrayList<Tile> tiles) {
        // Initialize string and counter
        StringBuilder tileBagStr = new StringBuilder();
        int counter = 1;

        // Traverse through all the tiles in the tile bag
        for (Tile tile : tiles)
        {
            tileBagStr.append("#").append(counter).append(" Tile letter: ").append(tile.getLetter()).append(", Points: ").append(tile.getValue()).append("\n");
            counter += 1;
        }
        return tileBagStr.toString();
    }
}
