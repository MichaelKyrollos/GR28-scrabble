import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Character.toLowerCase;

/**
 * The RackModel class models a player's rack in the game of Scrabble.
 * A rack contains a maximum of 7 tiles. Whenever the rack is empty,
 * the player must fill the rack by drawing 7 tiles from the tile bag.
 *
 * @author Pathum Danthanarayana, 101181411
 * @author Yehan De Silva, 101185388
 * @version 1.0
 * @date November 11, 2022
 */
public class RackModel extends ScrabbleModel implements Serializable {

    /** Fields **/
    private final ArrayList<Tile> tiles;
    private final TileBag bag;
    public static final int MAX_TILES = 7;

    /** Constructor **/
    public RackModel()
    {
        // Initialize ArrayList with a size of 7 tiles
        tiles = new ArrayList<>(MAX_TILES);

        // Initialize the tile bag to the same tile bag of the game
        bag = ScrabbleGameModel.GAME_TILE_BAG;
    }

    /**
     * Copy constructor for rack model.
     * @param rack Rack to be copied.
     *
     * @author Yehan De Silva, 101185388
     * @version 4.0
     * @date December 03, 2022
     */
    public RackModel(RackModel rack) {
        this.tiles = new ArrayList<>(rack.tiles);
        bag = new TileBag(ScrabbleGameModel.GAME_TILE_BAG);
    }

    /**
     * Returns the tiles in the rack.
     * @return The tiles in the rack.
     */
    public ArrayList<Tile> getTiles() {
        return this.tiles;
    }

    /**
     * The addTile method adds a tile that is drawn from the tile bag
     * to the player's rack.
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva, 101185388
     * @version 1.2
     *
     * @return True if tile is added to rack, false otherwise
     */
    public boolean addTile()
    {
        // Check if the player's rack is not full of tiles
        if (tiles.size() < MAX_TILES)
        {
            // If so, draw a tile from the tile bag and add it to the player's rack
            tiles.add(bag.dealTile());
            updateScrabbleViews();
            return true;
        }
        return false;
    }

    /**
     * Adds the specified tile to the rack if it is not full
     * @param tile Tile to be added
     *
     * @author Yehan De Silva, 101185388
     * @version 4.0
     * @date December 05, 2022
     */
    public void addSpecifiedTile(Tile tile) {
        if(this.tiles.size() < MAX_TILES) {
            this.tiles.add(tile);
            updateScrabbleViews();
        }
    }

    /**
     * The addTiles method adds the specified ArrayList of tiles to
     * the player's rack.
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva, 101185388
     * @author Amin Zeina, 101186297
     * @version 3.1
     *
     * @param tiles - the ArrayList of tiles that will be added to the player's rack
     */
    public void addTiles(ArrayList<Tile> tiles)
    {
        // Traverse through the provided tiles
        for (Tile tile : tiles)
        {
            if (tile instanceof BlankTile) {
                ((BlankTile) tile).resetToBlank();
            }
            // Add the tile to the player's rack
            this.tiles.add(tile);
        }
        updateScrabbleViews();
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
                // Remove the tile from the player's rack
                tiles.remove(tile);
                updateScrabbleViews();
                return tile;
            }
        }
        // If no match was found, return null
        return null;
    }

    /**
     * Removes list of tiles from player's rack.
     * @param tiles Tiles to be redrawn.
     *
     * @author Yehan De Silva, 101185388
     * @version 1.1
     * @date November 13, 2022
     */
    public void removeTiles(ArrayList<Tile> tiles) {
        for (Tile tile : tiles) {
            int tileIndex = this.tiles.indexOf(tile);
            bag.addTile(this.tiles.remove(tileIndex));
        }
        updateScrabbleViews();
    }

    /**
     * The fillRack method fills the player's rack with up to 7 tiles.
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva, 101185388
     * @author Amin Zeina, 101186297
     * @version 1.2
     */
    public void fillRack()
    {
        //Keep adding tiles to the rack while it is not full
        while(tiles.size() < MAX_TILES && !bag.isEmpty()) {
            this.addTile();
        }

        if (tiles.size() < MAX_TILES) {
            this.messageScrabbleViews("Cannot fill rack completely," +
                    " the Tile Bag is empty.");
        }
    }

    /**
     * Returns true if the rack is empty, false otherwise
     *
     * @author Amin Zeina, 101186297
     * @version 1.0
     *
     * @return true if the rack is empty, false otherwise
     *
     */
    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    /**
     * The toString method overrides the default toString method to return a String describing
     * the player's rack
     * @return a String describing the player's rack
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva, 101185388
     * @version 3.0
     * @date November 22, 2022
     */
    @Override
    public String toString()
    {
        return "Current rack:\n" + bag.tilesToString(this.tiles);
    }

    public StringBuilder forAI()
    {
        StringBuilder rackString = new StringBuilder("");
        for (Tile tile : tiles)
        {
            if (tile instanceof BlankTile){
                ((BlankTile) tile).setLetter('A');
            }
            rackString.append(toLowerCase(tile.getLetter()));
        }
        return rackString;
    }
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
}
