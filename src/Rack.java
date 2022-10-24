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
     */
    public void addTile()
    {
        // Check if the player's rack is not full of tiles
        if (tiles.size() < 7)
        {
            // If so, draw a tile from the tile bag and add it to the player's rack
            tiles.add(bag.dealTile());
        }
        else
        {
            // Otherwise, notify the player that their rack is currently full
            System.out.println("You cannot draw a tile from the bag. Your rack is currently full of tiles.");
        }
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
    public Tile removeTile(Character letter)
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
     * The hasTile method returns true if a tile with the specified letter exists
     * in the player's rack. If not, the method returns false.
     * @author Pathum Danthanarayana, 101181411
     *
     * @param letter - The letter of the tile that will be searched for in the player's rack
     * @return true if a tile with the specified letter exists, and false otherwise
     */
    public boolean hasTile(Character letter)
    {
        // Traverse through all the tiles in the player's rack
        for (Tile tile : tiles)
        {
            // Check if the current tile's letter is the same as the specified letter
            if (tile.getLetter() == letter)
            {
                return true;
            }
        }
        // If no match was found, return false
        return false;
    }

    /**
     * The fillRack method fills the player's rack with exactly 7 tiles,
     * by drawing 7 tiles from the tile bag and adding them to the player's rack.
     * @author Pathum Danthanarayana, 101181411
     *
     * The player's rack will be filled with 7 tiles only if the player's rack is
     * completely empty.
     */
    public void fillRack()
    {
        // Check if the player's rack is currently empty
        if (tiles.size() == 0)
        {
            // Draw 7 tiles from the tile bag and add it to the player's rack
            for (int i = 0; i < MAX_TILES; i++)
            {
                // Add the drawn tile to the player's rack
                this.addTile();
            }
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
            rackStr += "#" + counter + " Tile letter: " + tile.getLetter() + " Points: " + tile.getValue() + "\n";
            counter += 1;
        }
        return rackStr;
    }
}
