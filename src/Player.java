import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a player of the scrabble game.
 *
 * @author Amin Zeina 101186297
 * @version 1.1
 */
public class Player {

    private String name;
    private int score;
    private Rack rack;
    private ScrabbleGame game;

    /**
     * Constructs a new Player with a given name who is playing in a given game. The player's score is initially
     * zero.
     * Author - Amin Zeina
     *
     * @param name the name of the player
     * @param game the ScrabbleGame that the player is playing in
     */
    public Player(String name, ScrabbleGame game) {
        this.name = name;
        this.game = game;
        this.score = 0;
        this.rack = new Rack();
        this.rack.fillRack();
    }

    /**
     * Plays the given word entered by the user at the given coordinates. Returns true if the word was successfully
     * placed (i.e. the user had the necessary tiles, the word was valid, and the placement of the word on the board
     * was valid)
     *
     * words are entered as strings with preplaced letters enclosed in (). For example, placing "h(e)ll(o)" states that
     * the "e" and "o" are already on the board in the correct location, thus the player won't need to have an e or o
     * tile to play this word.
     *
     * @param command The command entered by the user to play a word
     * @return true if the word was successfully placed, false otherwise
     */
    public boolean playWord(Command command) {

        ArrayList<Tile> tilesToPlay = new ArrayList<>();

        // remove letters that are already on the board - i.e. letters between ( )
        String word = command.getSecondWord();
        String playerLetters = word.replaceAll("\\(.*?\\)", "");
        String[] split = playerLetters.split("");
        for (String s : split) {
            Tile tile = rack.getTile(s.charAt(0));
            if (tile != null) {
                tilesToPlay.add(tile);
            } else {
                // user doesn't have a tile with the required letter
                System.out.println("Invalid word, you do not have a \"" + s + "\" tile.");
                return false;
            }
        }

        // User has all required tiles
        // validate word legality and placement

        if (game.validateWord(command, tilesToPlay)) {
            // word is valid and has been placed, so remove the tiles from player's rack
            for (String s: split) {
                rack.removeTile(s.charAt(0));
            }
            rack.fillRack(); //refill rack
            System.out.println("Word placed, rack has been refilled");
            System.out.println(rack);
            return true;
        } else {
            System.out.println("Word cannot be placed");
            return false;
        }
    }

    /**
     * Called when the player has chosen to redraw their tiles. Replaces numNewTiles tiles in the player's rack
     * Author - Amin Zeina
     *
     * @param numNewTiles the number of tiles to replace
     */
    public void playRedraw(int numNewTiles) {
        Scanner in = new Scanner(System.in);
        for(int i = 0; i < numNewTiles; i++){
            System.out.println("Enter the letter you want to redraw: ");
            Character letter = in.nextLine().charAt(0);
            if (rack.removeTile(letter) != null) {
                // the user entered a valid letter to remove
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
     * Author - Amin Zeina
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
     * Author - Amin Zeina
     *
     * @return The name of the player
     */
    public String getName() {
        return this.name;
    }

}
