import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    /**
     * Constructs a ScrabbleController object.
     * @param model Corresponding model.
     * @param frame Corresponding frame.
     */
    public ScrabbleController(ScrabbleGameModel model, ScrabbleFrameView frame) {
        this.scrabbleModel = model;
        this.scrabbleFrame = frame;
    }

    /**
     * Method is invoked whenever a user performs input that the Controller is listening to.
     * TODO Finish implemntation of each user input.
     * @param e the event to be processed.
     *
     * @author Yehan De Silva
     * @version 1.0
     * @date November 11, 2022
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Let's play!")) {
            configurePlayerInformation();
        }
        else if (e.getActionCommand().equals("Play")) {
            //TODO
        }
        else if (e.getActionCommand().equals("Redraw")) {
            //TODO
        }
        else if (e.getActionCommand().equals("Skip")) {
            //TODO
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
