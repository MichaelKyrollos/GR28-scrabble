// Import libraries
import java.util.ArrayList;

/**
 * The Rack class models a player's rack in the game of Scrabble.
 * A rack contains a maximum of 7 tiles. Whenever the rack is empty,
 * the player must fill the rack by drawing 7 tiles from the tile bag.
 *
 * @author Pathum Danthanarayana, 101181411
 * @version 1.0
 * @date October 22, 2022
 */
public class Rack {

    /** Fields **/
    private ArrayList<Tile> tiles;
    private TileBag bag;
    private static final int MAX_TILES = 7;

    /** Constructor **/
    public Rack()
    {
        // Initialize ArrayList with a size of 7 tiles
        tiles = new ArrayList<>(7);

        // Initialize the tile bag to the same tile bag of the game
        bag = ScrabbleGame.GAME_TILE_BAG;
    }

    /** Methods **/

    /**
     * The addTile method adds a tile that is drawn from the tile bag
     * to the player's rack.
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 1.1
     *
     * @return True if tile is added to rack, false otherwise
     */
    public boolean addTile()
    {
        // Check if the player's rack is not full of tiles
        if (tiles.size() < 7)
        {
            // If so, draw a tile from the tile bag and add it to the player's rack
            tiles.add(bag.dealTile());
            return true;
        }
        // Otherwise, notify the player that their rack is currently full
        System.out.println("You cannot draw a tile from the bag. Your rack is currently full of tiles.");
        return false;
    }

    /**
     * The addTiles method adds the specified ArrayList of tiles to
     * the player's rack.
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 1.1
     *
     * @param tiles - the ArrayList of tiles that will be to the player's rack
     * @return True if the all the tiles are added to the rack, false otherwise
     */
    public boolean addTiles(ArrayList<Tile> tiles)
    {
        // Traverse through the provided tiles
        for (Tile tile : tiles)
        {
            // Add the tile to the player's rack
            if(!(this.tiles.add(tile))) {
                return false;
            }
        }
        return true;
    }

    /**
     * The removeTile method removes a tile from the player's rack that corresponds
     * to the specified letter, and returns it. If no tile matching the specified
     * letter exists, the method returns null.
     * @author Pathum Danthanarayana, 101181411
     *
     * @param letter - The letter of the tile that will be removed from the player's rack
     * @return the tile that will be removed from the player's rack, and return null if a tile with
     * the specified letter does not exist in the player's rack
     */
    public Tile removeTile(char letter)
    {
        // Traverse through all the tiles in the player's rack
        for (Tile tile : tiles)
        {
            // Check if the current tile's letter is the same as the specified letter
            if (tile.getLetter() == letter)
            {
                // If so, store a reference to the tile that will be removed
                Tile removedTile = tile;
                // Remove the tile from the player's rack
                tiles.remove(tile);
                return removedTile;
            }
        }
        // If no match was found, return null
        return null;
    }

    /**
     * The fillRack method fills the player's rack with up to 7 tiles.
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 1.1
     */
    public void fillRack()
    {
        //Keep adding tiles to the rack while it is not full
        while(tiles.size() < 7) {
            this.addTile();
        }
    }

    /**
     * The toString method overrides the default toString method to return a String describing
     * the player's rack.
     * @author Pathum Danthanarayana, 101181411
     *
     * @return a String describing the player's rack
     */
    @Override
    public String toString()
    {
        // Initialize String and counter
        String rackStr = "Current rack:\n";
        int counter = 1;

        // Traverse through each tile in the player's rack
        for (Tile tile : tiles)
        {
            rackStr += "#" + counter + " Tile letter: " + tile.getLetter() + ", Points: " + tile.getValue() + "\n";
            counter += 1;
        }
        return rackStr;
    }
    
}
