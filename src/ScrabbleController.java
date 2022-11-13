import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The ScrabbleController implements the Controller in the MVC design pattern. It listens to user-input performed on
 * the view, and communicates it to the model.
 *
 * @author Yehan De Silva
 * @version 1.0
 * @date November 11, 2022
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
    }

    /**
     * Method is invoked whenever a user performs input that the Controller is listening to.
     * TODO Finish implemntation of each user input.
     * @param e the event to be processed.
     *
     * @author Yehan De Silva
     * @author Amin Zeina 101186297
     * @author Pathum Danthanarayana, 101181411
     * @version 1.4
     * @date November 13, 2022
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Tile) {
            Tile tile = (Tile) e.getSource();
            if (isPlaying) {
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
            if (isRedrawing) {
                if(!(tilesToRedraw.contains(tile))) {
                    // Change background colour of the selected tile to show it has been selected
                    tile.setBackground(ScrabbleFrameView.SELECTED_TILE_COLOR);
                    tilesToRedraw.add(tile);
                }
            }
        }

        if (e.getSource() instanceof Square) {
            Square square = (Square) e.getSource();
            if (isPlaying) {
                if (square.getTile() == null && selectedTile != null) {
                    // square is empty, so place the tile
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
                    System.out.println("LETTER IN WAY: " + square.getTile().getLetter());
                    System.out.println(scrabbleModel.getGameBoard());
                } else if (square.getTile() != null && selectedTile == null){
                    // square not empty but no tile selected -> add existing clicked square to word
                    squaresInWord.add(square);
                    square.setEnabled(false);
                }
                // otherwise, square empty and no tile selected, do nothing
            }
        }

        if (e.getActionCommand().equals("Let's play!")) {
            configurePlayerInformation();
            scrabbleFrame.update();
        }
        else if (e.getActionCommand().equals("Play")) {
            isPlaying = true;
            scrabbleFrame.getRedrawButton().setEnabled(false);
            scrabbleModel.getGameBoard().copyBoardSquares();
            JButton playButton = (JButton) e.getSource();
            playButton.setText("Submit");
            playButton.setBackground(ScrabbleFrameView.SELECTED_BUTTON_COLOR);
        }
        else if (e.getActionCommand().equals("Redraw")) {
            this.isRedrawing = true;
            scrabbleFrame.getPlayButton().setEnabled(false);
            JButton redrawButton = (JButton) e.getSource();
            redrawButton.setText("Submit");
            redrawButton.setBackground(ScrabbleFrameView.SELECTED_BUTTON_COLOR);
        }
        else if (e.getActionCommand().equals("Skip")) {
            scrabbleModel.endTurn();
        } else if (e.getActionCommand().equals("Submit")) {
            if (isPlaying) {
                isPlaying = false;
                scrabbleFrame.getRedrawButton().setEnabled(true);
                JButton submitButton = (JButton) e.getSource();
                submitButton.setText("Play");
                submitButton.setBackground(ScrabbleFrameView.ACCENT_COLOR);
                scrabbleModel.playWord(new PlayWordEvent(scrabbleModel, squaresInWord, tilesPlaced));
                for (Square square : squaresInWord) {
                    square.setEnabled(true);
                }
                squaresInWord.clear();
                tilesPlaced.clear();
                tilesToRedraw.clear();
            }
            else if (isRedrawing) {
                isRedrawing = false;
                scrabbleFrame.getPlayButton().setEnabled(true);
                JButton submitButton = (JButton) e.getSource();
                submitButton.setText("Redraw");
                submitButton.setBackground(ScrabbleFrameView.ACCENT_COLOR);
                this.resetRedrawnTilesColour();
                scrabbleModel.redraw(tilesToRedraw);
                tilesToRedraw.clear();
            }
        }
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
                numberPlayers = Integer.parseInt(numPlayersStr);
            } catch (Exception e) {
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
            if (playerName.isEmpty())
            {
                // If so, assign a default player name to the player
                playerName = "Player " + (i + 1);
            }
            scrabbleModel.addPlayer(playerName.toUpperCase());
        }
        scrabbleFrame.startGame();
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
}
