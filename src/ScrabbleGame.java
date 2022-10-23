/**
 * Milestone 1 of the SYSC 3110 Project.
 * A text-based playable version of the game "Scrabble", where players play the game through the console using the keyboard.
 *
 * @author Amin Zeina, Michael Kyrollos, Pathum Danthanarayana, Yehan De Silva
 * @version 1.0 October 10, 2022
 */

import java.util.ArrayList;

public class ScrabbleGame {

    /**
     * The list of players playing this scrabble game.
     */
    private List<Player> players;
    /**
     * The board this scrabble game is being played on.
     */
    private Board gameBoard;
    /**
     * Interprets the player's instructions.
     */
    private Parser parser;
    /**
     * The dictionary for this scrabble game.
     */
    private Dictionary dictionary;
    /**
     * The bag of tiles for this scrabble game.
     */
    public static final TileBag GAME_TILE_BAG;

    /**
     * Creates a new scrabble game.
     */
    public ScrabbleGame() {
        players = new ArrayList<Player>();
        gameBoard = new Board();
        parser = new Parser();
        dictionary = new Dictionary();
        GAME_TILE_BAG = new TileBag();
    }

    /**
     * Adds a player to this scrabble game.
     * @param p Player to be added to this scrabble game.
     */
    public void addPlayer(Player p) {
        players.add(p);
    }

    /**
     * Update the game board with the newly placed word.
     * @param word Word to be placed on the board.
     * @param coord Coordinates of where the word should be placed.
     */
    private void updateBoard(String word, String coord) {
        gameBoard.update();
    }

    /**
     * Calculate the score of the given word.
     * @param word Word whose score is to be calculated.
     * @return Score of the given word.
     */
    private int calculateScore(String word) {
        return 0;
    }

    /**
     * Validate if a given word is legal and can be placed on the scrabble board.
     * @param word Word to be validated.
     * @param coord Coordinates of the word to be placed on the scrabble board.
     * @return True if the given word is legal and can be placed on the scrabble board, false otherwise.
     */
    private boolean validateWord(String word, String coord) {
        return dictionary.validateWord(word) && board.validateWord(coord);
    }

    /**
     * Starts this game of scrabble.
     */
    public void startGame() {

    }
}
