import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a player of the scrabble game.
 *
 * @author Amin Zeina 101186297
 * @author Yehan De Silva
 * @version 1.2
 * @date October 25, 2022
 */
public class Player {

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
    private Rack rack;
    /**
     * The board the player is playing on.
     */
    private Board board;

    /**
     * Constructs a new Player with a given name. The player's score is initially zero.
     * @author Amin Zeina
     * @author Yehan De Silva
     * @version 1.1
     * @date October 25, 2022
     *
     * @param name the name of the player
     * @param board the board the player is playing on
     */
    public Player(String name, Board board) {
        this.name = name;
        this.board = board;
        this.score = 0;
        this.rack = new Rack();
        this.rack.fillRack();
    }

    /**
     * Plays the given word entered by the user at the given coordinates. Returns true if the word was successfully
     * placed (i.e. the user had the necessary tiles and the placement of the word on the board was valid)
     *
     * Words are entered as strings with preplaced letters enclosed in (). For example, placing "h(e)ll(o)" states that
     * the "e" and "o" are already on the board in the correct location, thus the player doesn't need to have an e or o
     * tile to play this word.
     * @author Amin Zeina
     * @author Yehan De Silva
     * @version 1.1
     * @date October 25, 2022
     *
     * @param command The command entered by the user to play a word
     * @return true if the word was successfully placed, false otherwise
     */
    public boolean playWord(Command command) {

        ArrayList<Tile> tilesToPlay = new ArrayList<>();

        String word = command.getSecondWord();
        // remove letters that are already on the board - i.e. letters between ( )
        String playerLetters = word.replaceAll("\\(.*?\\)", "");
        char[] individualPlayerLetters = playerLetters.toCharArray();
        //Loop through each letter to ensure player has the letter in their rack
        for (char c : individualPlayerLetters) {
            Tile tile = rack.removeTile(c);
            if (tile != null) {
                tilesToPlay.add(tile);
            } else {
                // user doesn't have a tile with the required letter
                rack.addTiles(tilesToPlay); //Add all previously removed tiles back to the rack
                System.out.println("Invalid word, you do not have a \"" + c + "\" tile.");
                return false;
            }
        }

        // User has all required tiles
        // Validate word placement on board

        if (board.placeWord(command, tilesToPlay)) {
            //Word is valid and has been played
            rack.fillRack(); //refill rack
            System.out.println("Word placed, rack has been refilled");
            System.out.println(rack);
            return true;
        }
        else {
            //Otherwise, return the tiles back to the rack
            rack.addTiles(tilesToPlay);
            System.out.println("Word cannot be placed");
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
                ScrabbleGame.GAME_TILE_BAG.addTile(removedTile);
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
     * Adds scoreToAdd points to the current score of this player, then returns the new score
     * @author Amin Zeina
     *
     * @param scoreToAdd the amount of points that must be added to the player's score
     * @return the player's new, increased score
     */
    public int increaseScore(int scoreToAdd) {
        this.score += scoreToAdd;
        return this.score;
    }

    /**
     * Returns the current score of this player.
     * Author - Amin Zeina
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
     * @version 1.0
     * @date October 25, 2022
     */
    public Rack getRack()
    {
        return this.rack;
    }

}
