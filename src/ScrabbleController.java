import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.Character.isAlphabetic;
import static java.lang.Character.toUpperCase;

/**
 * The ScrabbleController implements the Controller in the MVC design pattern. It listens to user-input performed on
 * the view, and communicates it to the model.
 *
 * @author Yehan De Silva
 * @author Amin Zeina, 101186297
 * @version 3.0
 * @date November 14, 2022
 */
public class ScrabbleController implements ActionListener {

    /** Corresponding model **/
    private ScrabbleGameModel scrabbleModel;
    /** ScrabbleFrame to modify visual elements only**/
    private ScrabbleFrameView scrabbleFrame;

    private Tile selectedTile;
    private boolean isPlaying;
    private boolean isRedrawing;

    private ArrayList<Tile> tilesPlaced;
    private ArrayList<Tile> tilesToRedraw;
    private ArrayList<Square> squaresInWord;

    /**
     * Constructs a ScrabbleController object.
     * @param model Corresponding model.
     * @param frame Corresponding frame.
     */
    public ScrabbleController(ScrabbleGameModel model, ScrabbleFrameView frame) {
        this.scrabbleModel = model;
        this.scrabbleFrame = frame;
        this.selectedTile = null;
        this.isPlaying = false;
        this.isRedrawing = false;
        tilesPlaced = new ArrayList<>();
        tilesToRedraw = new ArrayList<>();
        squaresInWord = new ArrayList<>();
        addControllerToGameTiles();
    }

    /**
     * Method is invoked whenever a user performs input that the Controller is listening to.
     * @param e the event to be processed.
     *
     * @author Yehan De Silva
     * @author Amin Zeina 101186297
     * @author Pathum Danthanarayana, 101181411
     * @version 3.1
     * @date November 17, 2022
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //If tile is selected
        if (e.getSource() instanceof Tile) {
            Tile tile = (Tile) e.getSource();
            //Tile is to be played
            if (isPlaying) {
                playTileSelected(tile);
            }
            //Tile is to be redrawn
            else if (isRedrawing) {
                redrawTileSelected(tile);
            }
        }
        //Square is to be played on
        if (e.getSource() instanceof Square) {
            Square square = (Square) e.getSource();
            if (isPlaying) {
                playSquareSelected(square);
            }
        }

        //Game start button clicked
        if (e.getActionCommand().equals("Let's play!")) {
            this.configurePlayerInformation();
        }
        //Play button clicked
        else if (e.getActionCommand().equals("Play")) {
            this.playSelected();
        }
        //Redraw button clicked
        else if (e.getActionCommand().equals("Redraw")) {
            this.redrawSelected();
        }
        //Skip button clicked
        else if (e.getActionCommand().equals("Skip")) {
            scrabbleModel.endTurn();
        }
        //Submit button clicked
        else if (e.getActionCommand().equals("Submit")) {
            //Submit clicked while playing
            if (isPlaying) {
                submitPlaySelected();
            }
            //Submit clicked while redrawing
            else if (isRedrawing) {
                submitRedrawSelected();
            }
        }
        //Quit game selected
        else if (e.getActionCommand().equals("Quit Game")) {
            scrabbleModel.endGame();
        }
    }

    /**
     * Plays the currently selected tile if there is one in the given square.
     * @param square Square the selected tile is to be placed on
     *
     * @author Yehan De Silva
     * @version 3.0
     * @date November 17, 2022
     */
    private void playSquareSelected(Square square) {
        if (square.getTile() == null && selectedTile != null) {
            // square is empty, so place the tile
            if (selectedTile instanceof BlankTile) {
                // blank tile has been selected, so set the letter to what the user wants
                BlankTile blankTile = (BlankTile) selectedTile;
                boolean isCharLetter = false;
                while (!isCharLetter) {
                    String tileLetter = JOptionPane.showInputDialog(scrabbleFrame, "Enter the letter " +
                            "you'd like to use for this blank tile: ");
                    if (tileLetter.length() == 1 && isAlphabetic(tileLetter.charAt(0))) {
                        blankTile.setLetter(toUpperCase(tileLetter.charAt(0)));
                        isCharLetter = true;
                    } else {
                        JOptionPane.showMessageDialog(scrabbleFrame, "That letter is invalid, try again");
                    }
                }
            }
            selectedTile.setBackground(Color.WHITE);
            scrabbleModel.getCurrentPlayer().playTile(square, selectedTile);
            squaresInWord.add(square);
            tilesPlaced.add(selectedTile);
            selectedTile = null;
            square.setEnabled(false);
        } else if (square.getTile() != null && selectedTile != null) {
            // Change colour of tile to show it is unselected
            selectedTile.setBackground(Color.WHITE);
            // square not empty -> unselect tile and prompt user
            selectedTile = null;
            JOptionPane.showMessageDialog(null, "That square is full. Tile unselected.");
        } else if (square.getTile() != null && selectedTile == null){
            // square not empty but no tile selected -> add existing clicked square to word
            squaresInWord.add(square);
            square.setEnabled(false);
        }
        // otherwise, square empty and no tile selected, do nothing
    }

    /**
     * Adds the given tile to the list of tiles to be redrawn or removes it if it already exists.
     * @param tile Tile to be added/removed from the redrawn tiles list.
     *
     * @author Yehan De Silva
     * @version 3.0
     * @date November 17, 2022
     */
    private void redrawTileSelected(Tile tile) {
        //Add tile to list of tiles to redraw
        if(!(tilesToRedraw.contains(tile))) {
            // Change background colour of the selected tile to show it has been selected
            tile.setBackground(ScrabbleFrameView.SELECTED_TILE_COLOR);
            tilesToRedraw.add(tile);
        }
        //Remove tile if it is already in the list of tiles to redraw
        else {
            tilesToRedraw.remove(tile);
            tile.setBackground(Color.WHITE);
        }
    }

    /**
     * Sets the given tile as the selected tile.
     * @param tile Tile to be set as the selected tile.
     *
     * @author Yehan De Silva
     * @version 3.0
     * @date November 17, 2022
     */
    private void playTileSelected(Tile tile) {
        // Check if a tile is already selected
        if (selectedTile != null)
        {
            // If so, unselect the currently selected tile
            selectedTile.setBackground(Color.WHITE);
        }
        selectedTile = tile;

        // Change background colour of the selected tile to show it has been selected
        selectedTile.setBackground(ScrabbleFrameView.SELECTED_TILE_COLOR);
    }

    /**
     * Redraws the list of the tiles to be redrawn.
     *
     * @author Yehan De Silva
     * @version 3.0
     * @date November 17, 2022
     */
    private void submitRedrawSelected() {
        //Redrawing is submitted, so change button views
        isRedrawing = false;
        scrabbleFrame.getPlayButton().setEnabled(true);
        scrabbleFrame.getSkipButton().setEnabled(true);
        scrabbleFrame.getRedrawButton().setText("Redraw");
        scrabbleFrame.getRedrawButton().setBackground(ScrabbleFrameView.ACCENT_COLOR);
        //Only perform redraw method in model if at least one tile is selected to redraw
        if (!tilesToRedraw.isEmpty()) {
            this.resetRedrawnTilesColour();
            scrabbleModel.redraw(tilesToRedraw);
            tilesToRedraw.clear();
        }
    }

    /**
     * Attempts to play the user created word on the board.
     *
     * @author Yehan De Silva
     * @version 3.0
     * @date November 17, 2022
     */
    private void submitPlaySelected() {
        //Playing is submitted, so change button views
        isPlaying = false;
        scrabbleFrame.getRedrawButton().setEnabled(true);
        scrabbleFrame.getSkipButton().setEnabled(true);
        scrabbleFrame.getPlayButton().setText("Play");
        scrabbleFrame.getPlayButton().setBackground(ScrabbleFrameView.ACCENT_COLOR);
        scrabbleModel.playWord(new PlayWordEvent(scrabbleModel, squaresInWord, tilesPlaced));
        for (Square square : squaresInWord) {
            square.setEnabled(true);
        }
        squaresInWord.clear();
        tilesPlaced.clear();
        tilesToRedraw.clear();
    }

    /**
     * Updates the controller state and game views to the play status.
     *
     * @author Yehan De Silva
     * @version 3.0
     * @date November 17, 2022
     */
    private void playSelected() {
        //Update state and views if play is selected
        isPlaying = true;
        scrabbleFrame.getRedrawButton().setEnabled(false);
        scrabbleFrame.getSkipButton().setEnabled(false);
        scrabbleModel.getGameBoard().copyBoardSquares();
        scrabbleFrame.getPlayButton().setText("Submit");
        scrabbleFrame.getPlayButton().setBackground(ScrabbleFrameView.SELECTED_BUTTON_COLOR);
    }

    /**
     * Updates the controller state and game views to the redraw status.
     *
     * @author Yehan De Silva
     * @version 3.0
     * @date November 17, 2022
     */
    private void redrawSelected() {
        //Update state and views if redraw is selected
        this.isRedrawing = true;
        scrabbleFrame.getPlayButton().setEnabled(false);
        scrabbleFrame.getSkipButton().setEnabled(false);
        scrabbleFrame.getRedrawButton().setText("Submit");
        scrabbleFrame.getRedrawButton().setBackground(ScrabbleFrameView.SELECTED_BUTTON_COLOR);
    }

    /**
     * Configures starting player information once a Scrabble game is started.
     *
     * @author Yehan De Silva
     * @version 1.0
     * @date November 11, 2022
     */
    private void configurePlayerInformation() {
        int numberPlayers = 0;
        boolean validNumberPlayers = false;

        //Keep looping till valid number of players (Between 2-4) is chosen by user
        while (!validNumberPlayers) {
            String numPlayersStr = JOptionPane.showInputDialog(scrabbleFrame, "Please enter the number of players (Between 2-4):");
            // Convert String into integer
            try {
                //Return if user cancels the input prompt
                if (numPlayersStr == null) {
                    return;
                }
                numberPlayers = Integer.parseInt(numPlayersStr);
            } catch (NumberFormatException e) {
                continue;
            }
            //Stop looping once a valid integer is given
            if (numberPlayers <= 4 && numberPlayers >= 2) {
                validNumberPlayers = true;
            }
        }

        // Prompt the user to enter a player name for each player
        for (int i = 0; i < numberPlayers; i++) {
            String message = "Enter Player " + (i + 1) + " name:";
            String playerName = JOptionPane.showInputDialog(scrabbleFrame, message);

            // Check if the user has entered an empty player name
            if (playerName == null)
            {
                // If so, assign a default player name to the player
                playerName = "Player " + (i + 1);
            }
            scrabbleModel.addPlayer(playerName.toUpperCase());
        }
        scrabbleFrame.startGame();
        scrabbleFrame.update();
    }

    /**
     * The resetRedrawnTilesColour method resets the background colour of the tiles to
     * be redrawn to white, so that when the same tiles are drawn from the tile bag
     * by a different player, they will not have the selected background color.
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     * @date November 13, 2022
     */
    private void resetRedrawnTilesColour()
    {
        // Traverse through each tile in the tiles to redraw
        for (Tile tile : tilesToRedraw)
        {
            // Set the tile's background colour back to white
            tile.setBackground(Color.WHITE);
        }
    }

    /**
     * Adds this ScrabbleController as a listener to all game tiles.
     *
     * @author Yehan De Silva
     * @version 1.0
     * @date November 13, 2022
     */
    private void addControllerToGameTiles() {
        for (Tile tile : scrabbleModel.GAME_TILE_BAG.getTiles()) {
            tile.addActionListener(this);
        }
    }
}
