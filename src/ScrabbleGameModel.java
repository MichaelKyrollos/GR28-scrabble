
// Import libraries
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Milestone 2 of the SYSC 3110 Project.
 * A GUI-based playable version of the game "Scrabble", where players play the game through an interactive GUI.
 * This scrabble game is compatible for 2-4 players.
 *
 * @author Michael Kyrollos, 101183521
 * @author Yehan De Silva
 * @author Pathum Danthanarayana, 101181411
 * @version 2.0
 * @date November 11, 2022
 */
public class ScrabbleGameModel extends ScrabbleModel implements Serializable {

    /** Player number constants **/
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 4;

    /**
     * BoardModel the game is being played on.
     */
    private BoardModel gameBoard;

    /**
     * Players of the scrabble game.
     */
    private List<PlayerModel> players;

    /**
     * The current turn of the scrabble game.
     */
    private int currentTurn;

    /**
     * The stacks to hold game status' inorder to undo/redo.
     */
    private transient Stack<ScrabbleGameStatus> undoStack;
    private transient Stack<ScrabbleGameStatus> redoStack;
    private Boolean gameStatusChanged;

    /**
     * The tile bag used to store all the tiles for this Scrabble game.
     */
    public static TileBag GAME_TILE_BAG = new TileBag();

    /**
     * The dictionary used to validate words for this Scrabble game.
     */
    public static final ScrabbleDictionary SCRABBLE_DICTIONARY= new ScrabbleDictionary();

    /**
     * Creates a new scrabble game.
     * @author Michael Kyrollos, 101183521
     * @author Yehan De Silva
     * @author Pathum Danthanarayana, 101181411
     * @version 2.1
     * @date October 25, 2022
     */
    public ScrabbleGameModel() {
        players = new ArrayList<>();
        // Make the first player in the ArrayList have the first turn
        currentTurn = 0;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.gameStatusChanged = false;
    }

    /**
     * Creates the BoardModel for this scrabble model. If a file is given, the board will be a custom file matching
     * the XML. If there is an error with the XML, a default board will be created
     *
     * @param customBoard the custom board XML file to load (optional)
     */
    public void createGameBoard(File customBoard) {
        if (customBoard == null) {
            try {
                gameBoard = new BoardModel(this);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Default board XML is either missing or " +
                        "has been modified. Please ensure you have the correct XML file downloaded, then try again. " +
                        "The game will now exit");
                System.exit(0);
            }
        } else {
            try {
                gameBoard = new BoardModel(this, customBoard);
            } catch (Exception e) {
                // invalid XML -> create default board
                try {
                    gameBoard = new BoardModel(this);
                } catch (Exception ex) {
                    // Default XML has been modified or removed
                    JOptionPane.showMessageDialog(null, "Default board XML is either missing or " +
                            "has been modified. Please ensure you have the correct XML file downloaded, then try again. " +
                            "The game will now exit");
                    System.exit(0);
                }

            }
        }

    }

    /**
     * Returns the PlayerModel whose currently playing their turn
     *
     * @return the current player
     *
     * @author Yehan De Silva
     * @version 1.0
     * @date November 11, 2022
     */
    public PlayerModel getCurrentPlayer() {
        return players.get(currentTurn % players.size());
    }

    /**
     * Returns the undo stack of the game model.
     *
     * @return the game undo stack.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 02, 2022
     */
    public Stack<ScrabbleGameStatus> getUndoStack() {return this.undoStack;}

    /**
     * Returns the redo stack of the game model.
     *
     * @return the game redo stack.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 02, 2022
     */
    public Stack<ScrabbleGameStatus> getRedoStack() {return this.redoStack;}

    /**
     * Returns the list of players playing this Scrabble Game.
     * @return List of players
     *
     * @author Yehan De Silva
     * @version 1.0
     * @date November 11, 2022
     */
    public List<PlayerModel> getPlayers() {return this.players;}

    /**
     * Returns the game board this Scrabble game is being played on.
     * @return The game board.
     *
     * @author Yehan De Silva
     * @version 1.0
     * @date November 11, 2022
     */
    public BoardModel getGameBoard() {return this.gameBoard;}

    /**
     * The loadScrabbleGame method loads a Scrabble game save file
     * from the specified file path.
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     * @date December 4th, 2022
     *
     * @param loadFilePath - The file path at which the Scrabble game save file will be imported from
     * @return true if the Scrabble game was successfully loaded, and false otherwise
     */
    public boolean loadScrabbleGame(String loadFilePath)
    {
        System.out.println("Using serialized import method...");
        try
        {
            FileInputStream fileInputStream = new FileInputStream(loadFilePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            // Read the written Scrabble game model
            ScrabbleGameStatus gameStatus = (ScrabbleGameStatus) objectInputStream.readObject();
            this.gameBoard = gameStatus.getBoard();
            this.players = gameStatus.getPlayers();
            this.currentTurn = gameStatus.getCurrentTurn();
            GAME_TILE_BAG = gameStatus.getTileBag();

            // Clear the undo and redo stack
            this.undoStack.clear();
            this.redoStack.clear();
            // Set the updated board for all the players
            for (PlayerModel playerModel : this.players)
            {
                playerModel.setBoard(this.gameBoard);
            }
            // Push the current game's status to the undo stack
            this.pushStatusToUndoStack();
            // Update the Scrabble game views
            this.gameStatusChanged = true;
            this.updateScrabbleViews();
            this.gameStatusChanged = false;

            // Enable the tiles on the rack for the player with the current turn
            for (PlayerModel playerModel : this.players)
            {
                this.setEnableTiles(playerModel, playerModel.equals(this.getCurrentPlayer()));
            }

            // Close the object input stream
            objectInputStream.close();
            // Close the file input stream
            fileInputStream.close();
            return true;
        }
        catch (EOFException e)
        {
            // System.out.println("End of file reached: " + e);
        }
        catch (IOException e)
        {
            System.out.println("IO Exception: " + e);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Class not found: " + e);
        }
        return false;
    }

    /**
     * The saveScrabbleGame method saves the current Scrabble game to a
     * save file at the specified file path.
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     * @date December 4th, 2022
     *
     * @param exportFilePath - The file path at which the Scrabble game will be saved to
     * @return true if the Scrabble game was successfully saved, and false otherwise
     */
    public boolean saveScrabbleGame(String exportFilePath)
    {
        System.out.println("Using serialized save method...");
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(exportFilePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // Write the current Scrabble game's status
            objectOutputStream.writeObject(new ScrabbleGameStatus(this.gameBoard, this.players, this.currentTurn, GAME_TILE_BAG));
            // Close the object output stream
            objectOutputStream.close();
            // Close the file output stream
            fileOutputStream.close();
            return true;
        }
        catch (IOException e)
        {
            System.out.println("Error in exporting Scrabble game model: " + e);
        }
        return false;
    }

    /**
     * Adds a player to this scrabble game. Only 4 players may be playing at one time.
     * @param playerName Name of player.
     * @return True if player was added, false otherwise.
     * @author Yehan De Silva
     * @version 2.1
     * @date November 11, 2022
     */
    public boolean addPlayer(String playerName) {
        if (players.size() <= 4) {
            players.add(new PlayerModel(playerName, gameBoard));
            return true;
        }
        return false;
    }

    public boolean addAI(String nameAI) {
        if (players.size() <= 4) {
            players.add(new AIPlayerModel(nameAI, gameBoard));
            return true;
        }
        return false;
    }

    /**
     * Plays a word that was entered by the player, using the "play" button.
     *
     *
     * @param playEvent the PlayWordEvent that was generated to play this word
     * @return true if the word was played successfully, false otherwise
     * @author Amin Zeina, 101186297
     * @date November 12, 2022
     */
    public boolean playWord(PlayWordEvent playEvent) {
        PlayerModel currentPlayer = getCurrentPlayer();
        String word = playEvent.getWord();
        boolean isWord = true;
        // check that the word is a valid english scrabble word
        if (!(currentPlayer instanceof AIPlayerModel)) {
                  isWord = SCRABBLE_DICTIONARY.validateWord(word);
        }
        if (isWord)
        {
            // check if the word can actually be played
            if (currentPlayer.playWord(playEvent)) {
                JOptionPane.showMessageDialog(null, "You have successfully played \"" +
                        word.toUpperCase() + "\". You now have " + currentPlayer.getScore() + " points!");

                // check if the game should end (rack empty and no tiles in bag)
                if (currentPlayer.getRack().isEmpty() && GAME_TILE_BAG.isEmpty()) {
                    // end game
                    this.endGame();
                }

                this.endTurn();
                return true;
            }
        } else {
            JOptionPane.showMessageDialog(null, "\""+ word.toUpperCase() +
                    "\" is not a valid word. Try again.");
            this.gameBoard.revertBoard();
            currentPlayer.getRack().addTiles(playEvent.getTilesPlaced());
        }
        return false;
    }

    /**
     * Ends the current turn.
     *
     * Switches the current player, updates the turn count, updates the view, enables the new current player's rack
     * tile buttons, and disables the previous player's rack tile buttons.
     *
     * @author Amin Zeina, 101186297
     * @auuthor Yehan De Silva
     * @version 1.1
     * @date November 12, 2022
     */
    public void endTurn() {
        this.setEnableTiles(this.getCurrentPlayer(), false);
        this.currentTurn++;
        this.pushStatusToUndoStack();
        this.redoStack.clear();
        this.setEnableTiles(this.getCurrentPlayer(), true);
        updateScrabbleViews();
    }

    /**
     * Sets up the rack tile buttons for the first turn. Disables all players' racks execpt for the first player's
     *
     * @author Amin Zeina, 101186297
     * @version 1.0
     */
    public void setupFirstTurn() {
        // disable rack tile buttons for all but the first player
        for (int i = 1; i < players.size(); i++) {
            for (Tile tile : players.get(i).getRack().getTiles()) {
                tile.setEnabled(false);
            }
        }
    }

    /**
     * Redraws the given tiles for the current player.
     *
     * @author Michael Kyrollos, 101183521
     * @author Yehan De Silva
     * @version  2.0
     * @date November 13, 2022
     *
     * @param redrawTiles Tiles to be redrawn
     */
    public void redraw(ArrayList<Tile> redrawTiles) {
        this.getCurrentPlayer().redraw(redrawTiles);
        this.endTurn();
    }

    /**
     * Ends the game and displays the winner.
     *
     * @author Amin Zeina, 101186297
     * @version 1.0
     * @date November 13, 2022
     */
    public void endGame() {

        this.calculateEndGameScore();

        ArrayList<PlayerModel> winners = this.determineWinners();
        if (winners.size() == 1) {
            PlayerModel winner = winners.get(0);
            JOptionPane.showMessageDialog(null, "The game has ended.\nPlayer " + winner.getName() +
                    " is the winner!" + " They had " + winner.getScore() + " points.");
        } else {
            String tieMessage = "";
            for (PlayerModel player: winners) {
                tieMessage += "\n" + player.getName() + " - " + player.getScore() + " points";
            }
            JOptionPane.showMessageDialog(null, "The game has ended. The following players have tied:" + tieMessage);
         }

        System.exit(0);
    }

    /**
     * Helper method for endGame method. Calculates the score of the players at the end of the game, following the
     * rules given on the Scrabble wiki:
     *
     * "When the game ends, each player's score is reduced by the sum of their unused
     * letters; in addition, if a player has used all of their letters (known as "going out" or "playing out"),
     * the sum of all other players' unused letters is added to that player's score."
     *
     * @author Amin Zeina, 101186297
     * @version 1.0
     *
     */
    private void calculateEndGameScore() {
        // get the sum of each player's unused letters
        ArrayList<Integer> unusedTileScore = new ArrayList<>(players.size());
        int totalUnusedScore = 0;
        for (PlayerModel player : players) {
            int tempSum = 0;
            for (Tile tile : player.getRack().getTiles()) {
                tempSum += tile.getValue();
            }
            unusedTileScore.add(tempSum);
            totalUnusedScore += tempSum;
        }
        for (int i = 0; i < players.size(); i++) {
            PlayerModel currPlayer = players.get(i);
            if (unusedTileScore.get(i) == 0) {
                // This player has no unused tiles, so add the sum of all other unused tiles to this player's score
                currPlayer.adjustScore(totalUnusedScore);
            } else {
                // This player has unused tiles, so subract the sum of those tiles
                currPlayer.adjustScore(-1 * unusedTileScore.get(i));
            }
        }
    }

    /**
     * Helper method for endGame method. Determines the winner of the game, or "winners" if the game is tied.
     *
     * If there is a tie, multiple players will be returned.
     *
     *
     * @return a list of players at or tied for the highest score.
     */
    public ArrayList<PlayerModel> determineWinners() {
        ArrayList<PlayerModel> winnerList = new ArrayList<>();
        int highestScore = -1;
        for (PlayerModel player : players) {
            int currScore = player.getScore();
            if (currScore > highestScore) {
                winnerList.clear();
                winnerList.add(player);
                highestScore = currScore;
            } else if (currScore == highestScore) {
                winnerList.add(player);
            }
        }
        return winnerList;
    }

    /**
     * Returns the game status changed boolean.
     * @return game status changed boolean
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 03, 2022
     */
    public Boolean getGameStatusChanged() {
        return this.gameStatusChanged;
    }

    /**
     * Undo the last move made in the scrabble game.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 03, 2022
     */
    public void undoTurn() {
        this.redoStack.push(this.undoStack.pop());
        ScrabbleGameStatus lastTurn = this.undoStack.pop();
        restoreStatus(lastTurn);
    }

    /**
     * Redo the last move that was undone.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 03, 2022
     */
    public void redoTurn() {
        ScrabbleGameStatus lastTurn = this.redoStack.pop();
        restoreStatus(lastTurn);
    }

    /**
     * Restores game status to the one specified.
     * @param status Status to be set to.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 03, 2022
     */
    private void restoreStatus(ScrabbleGameStatus status) {
        this.gameBoard = status.getBoard();
        this.players = status.getPlayers();
        for (PlayerModel pm : this.players) {
            pm.setBoard(this.gameBoard);
        }
        this.currentTurn = status.getCurrentTurn();
        GAME_TILE_BAG = status.getTileBag();

        this.pushStatusToUndoStack();

        this.gameStatusChanged = true;
        this.updateScrabbleViews();
        this.gameStatusChanged = false;

        for (PlayerModel pm : this.players) {
            this.setEnableTiles(pm, pm.equals(this.getCurrentPlayer()));
        }
    }

    /**
     * Pushes the current game status onto the undo stack.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 03, 2022
     */
    public void pushStatusToUndoStack() {
        ArrayList<PlayerModel> lastPlayers = new ArrayList<>();
        for (PlayerModel pm : this.players) {
            if (pm instanceof AIPlayerModel) {
                lastPlayers.add(new AIPlayerModel((AIPlayerModel) pm));
            }
            else {
                lastPlayers.add(new PlayerModel(pm));
            }
        }
        this.undoStack.push(new ScrabbleGameStatus(new BoardModel(this.gameBoard), lastPlayers, this.currentTurn, new TileBag(GAME_TILE_BAG)));
    }

    /**
     * Enables/disables the tiles of the current player
     * @param enable True to enable tiles, false to disable tiles.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 02, 2022
     */
    private void setEnableTiles(PlayerModel player, boolean enable) {
        for (Tile tile : player.getRack().getTiles()) {
            tile.setEnabled(enable);
        }
    }
}
