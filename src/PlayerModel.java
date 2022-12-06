import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a player of the scrabble game.
 *
 * @author Amin Zeina 101186297
 * @author Yehan De Silva
 * @author Pathum Danthanarayana, 101181411
 * @version 2.0
 * @date November 11, 2022
 */
public class PlayerModel extends ScrabbleModel implements ScrabblePlayer, Serializable {

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
     * Copy constructor for PlayerModel.
     * @param player PlayerModel to be copied.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 03, 2022
     */
    public PlayerModel(PlayerModel player) {
        this.name = player.name;
        this.board = new BoardModel(player.board);
        this.score = player.score;
        this.rack = new RackModel(player.rack);
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
    @Override
    public boolean playWord(PlayWordEvent playEvent) {

        // Validate word placement on board
        int wordScore = board.placeWord(playEvent);
        if (wordScore != -1) {
            //Word is valid and has been played
            rack.fillRack(); //refill rack
            this.score += wordScore;
            //Finalize the squares
            for (Square square : playEvent.getSquaresInWord()) {
                square.setSquareFinalized(true);
            }
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
     * Redraws the given tiles from the player's rack.
     * @param redrawTiles Tiles to be redrawn.
     *
     * @author Yehan De Silva
     * @version 1.0
     * @date November 13, 2022
     */
    @Override
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
     * @author Amin Zeina
     *
     * @return The name of the player
     */
    @Override
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
     * Sets the board model of this player model.
     * @param board BoardModel to be set to.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 03, 2022
     */
    public void setBoard(BoardModel board) {this.board = board;}

    /**
     * Places the given tile on the given square on the board
     *
     * @param square the square to place the tile in
     * @param tile the tile to place
     */
    @Override
    public void playTile(Square square, Tile tile) {
        board.playTile(square, tile);
        rack.removeTile(tile.getLetter());
    }

    /**
     * Removes the given tile from the board and returns it back to the player's rack.
     * @param square Square to be removed from.
     * @param tile Tile to be removed
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 05, 2022
     */
    public void removeTile(Square square, Tile tile) {
        board.removeTile(square);
        rack.addSpecifiedTile(tile);
    }

    /**
     * Gets the board of this player.
     *
     * @return BoardModel of this player.
     */
    public BoardModel getBoard() {
        return board;
    }


    /**
     * Adds the given score to this player's score. To decrease score, param score should be negative. If score becomes
     * negative after adjustment (score < 0), the player's score is set to 0.
     *
     *
     * @author Amin Zeina, 101186297
     * @version 1.0
     *
     * @param score the score to add to this players score (or to subtract if negative)
     */
    @Override
    public void adjustScore(int score) {
        this.score += score;
        if (this.score < 0) {
            this.score = 0;
        }
    }
}
