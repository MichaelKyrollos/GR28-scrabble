import javax.swing.*;
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

    private ArrayList<Tile> tilesPlaced;
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
        tilesPlaced = new ArrayList<>();
        squaresInWord = new ArrayList<>();
    }

    /**
     * Method is invoked whenever a user performs input that the Controller is listening to.
     * TODO Finish implemntation of each user input.
     * @param e the event to be processed.
     *
     * @author Yehan De Silva
     * @author Amin Zeina 101186297
     * @version 1.2
     * @date November 11, 2022
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Tile) {
            Tile tile = (Tile) e.getSource();
            if (isPlaying) {
                selectedTile = tile;
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
            scrabbleModel.getGameBoard().copyBoardSquares();
            JButton playButton = (JButton) e.getSource();
            playButton.setText("Submit");
        }
        else if (e.getActionCommand().equals("Redraw")) {
            //TODO
        }
        else if (e.getActionCommand().equals("Skip")) {
            scrabbleModel.endTurn();
        } else if (e.getActionCommand().equals("Submit")) {
            isPlaying = false;
            JButton submitButton = (JButton) e.getSource();
            submitButton.setText("Play");
            scrabbleModel.playWord(new PlayWordEvent(scrabbleModel, squaresInWord, tilesPlaced));
            for (Square square : squaresInWord) {
                square.setEnabled(true);
            }
            squaresInWord.clear();
            tilesPlaced.clear();
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
}
