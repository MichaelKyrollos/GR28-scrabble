import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a player of the scrabble game.
 *
 * @author Amin Zeina 101186297
 * @author Yehan De Silva
 * @version 2.0
 * @date November 11, 2022
 */
public class PlayerModel extends ScrabbleModel{

    /**
     * Name of the player.
     */
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

    /**
     * Constructs a new PlayerModel with a given name. The player's score is initially zero.
     * @author Amin Zeina
     * @author Yehan De Silva
     * @version 1.1
     * @date October 25, 2022
     *
     * @param name the name of the player
     * @param board the board the player is playing on
     */
    public PlayerModel(String name, BoardModel board) {
        this.name = name;
        this.board = board;
        this.score = 0;
        this.rack = new RackModel();
        this.rack.fillRack();
    }

    /**
     * Plays the given word entered by the user at the given coordinates. Returns true if the word was successfully
     * placed (i.e. the user had the necessary tiles and the placement of the word on the board was valid)
     *
     * Words are entered as strings with preplaced letters enclosed in (). For example, placing "h(e)ll(o)" states that
     * the "e" and "o" are already on the board in the correct location, thus the player doesn't need to have an e or o
     * tile to play this word.
     *
     * @author Amin Zeina, 101186297
     * @author Yehan De Silva
     * @version 2.1
     * @date November 12, 2022
     *
     * @param playEvent the PlayWordEvent that was generated to play this word
     * @return true if the word was successfully placed, false otherwise
     */
    public boolean playWord(PlayWordEvent playEvent) {

        // Validate word placement on board
        int wordScore = board.placeWord(playEvent);
        if (wordScore != -1) {
            //Word is valid and has been played
            rack.fillRack(); //refill rack
            this.score += wordScore;
            updateScrabbleViews();
            return true;
        }
        else {
            //Otherwise, return the tiles back to the rack
            rack.addTiles(playEvent.getTilesPlaced());
            return false;
        }
    }

    /**
     * Called when the player has chosen to redraw their tiles. Replaces numNewTiles tiles in the player's rack
     * @author Amin Zeina
     * @author Yehan De Silva
     * @version 1.1
     * @date October 25, 2022
     *
     * @param numNewTiles the number of tiles to replace
     */
    public void playRedraw(int numNewTiles) {
        Tile removedTile;
        Scanner in = new Scanner(System.in);

        for(int i = 0; i < numNewTiles; i++){
            System.out.println("Enter the letter you want to redraw: ");
            char letter = in.nextLine().charAt(0);
            removedTile = rack.removeTile(letter);
            if (removedTile != null) {
                // User entered a valid letter to remove
                ScrabbleGameModel.GAME_TILE_BAG.addTile(removedTile);
                System.out.println("Tile \"" + letter + "\" has been removed");
                System.out.println(rack);

            } else {
                System.out.println("You do not have a tile with that letter. Try again");
                i--;
            }
        }
        rack.fillRack();
        System.out.println(rack);
    }

    /**
     * Redraws the given tiles from the player's rack.
     * @param redrawTiles Tiles to be redrawn.
     *
     * @author Yehan De Silva
     * @version 1.0
     * @date November 13, 2022
     */
    public void redraw(ArrayList<Tile> redrawTiles) {
        this.rack.removeTiles(redrawTiles);
        this.rack.fillRack();
    }

    /**
     * Returns the current score of this player.
     * @Author Amin Zeina
     *
     * @return The current score of the player
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Returns the name of this player
     * @ Amin Zeina
     *
     * @return The name of the player
     */
    public String getName() {
        return this.name;
    }

    /**
     * The getRack method returns the player's rack.
     * @author Pathum Danthanarayana, 101181411
     *
     * @version 1.0
     * @date October 25, 2022
     */
    public RackModel getRack()
    {
        return this.rack;
    }

    /**
     * Places the given tile on the given square on the board
     *
     * @param square the square to place the tile in
     * @param tile the tile to place
     */
    public void playTile(Square square, Tile tile) {
        board.playTile(square, tile);
        rack.removeTile(tile.getLetter());

    }

}
