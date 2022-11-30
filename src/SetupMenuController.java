import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The SetupMenuController is a Controller tasked with controlling inputs made in the setup menu of the scrabble game.
 *
 * @author Yehan De Silva
 * @version 4.0
 * @date November 30, 2022
 */
public class SetupMenuController implements ActionListener {

    private final ScrabbleGameModel scrabbleModel;
    private final ScrabbleFrameView scrabbleFrame;

    /**
     * Constructs a SetupMenuController object.
     * @param model Corresponding model.
     * @param frame Corresponding frame.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date November 30, 2022
     */
    public SetupMenuController(ScrabbleGameModel model, ScrabbleFrameView frame) {
        this.scrabbleModel = model;
        this.scrabbleFrame = frame;
    }

    /**
     * This method executes when the "Let's play" button is clicked in the setup menu.
     * The method attempts to configure all the player and AI player information, and starts the Scrabble game.
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 4.0
     * @date November 30, 2022
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Configure player information for regular (human) players
        int numPlayers = configurePlayerInformation();

        // Check for errors in configuring regular player information
        if (numPlayers != -1)
        {
            // Configure AI player information
            int numAIPlayers = configureAIPlayerInformation(numPlayers);
            // Check for any errors in configuring AI player information
            if (numAIPlayers != -1)
            {
                // If no errors, start the game
                scrabbleFrame.startGame();
            }
        }
    }

    /**
     * Configures starting player information once a Scrabble game is started.
     * @return the number of players configured, or return -1 if error in configuring players
     *
     * @author Yehan De Silva
     * @author Pathum Danthanarayana, 101181411
     * @version 4.0
     * @date November 30, 2022
     */
    private int configurePlayerInformation() {
        int numberPlayers = 0;
        boolean validNumberPlayers = false;

        //Keep looping till valid number of players (Between 2-4) is chosen by user
        while (!validNumberPlayers) {
            String numPlayersStr = JOptionPane.showInputDialog(scrabbleFrame, "Please enter the number of players (Between 0 and 4):");
            // Convert String into integer
            try {
                //Return if user cancels the input prompt
                if (numPlayersStr == null) {
                    return -1;
                }
                numberPlayers = Integer.parseInt(numPlayersStr);
            } catch (NumberFormatException e) {
                continue;
            }
            //Stop looping once a valid integer is given
            if (numberPlayers <= ScrabbleGameModel.MAX_PLAYERS && numberPlayers >= 0) {
                validNumberPlayers = true;
            }
        }

        // Prompt the user to enter a player name for each player
        for (int i = 0; i < numberPlayers; i++)
        {
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
        return numberPlayers;
    }

    /**
     * The configureAIPlayerInformation method configures the AI player
     * information.
     * @return the number of AI players configured, and return -1 if error in configuring AI players
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 4.0
     * @date November 30th, 2022
     */
    private int configureAIPlayerInformation(int numRegularPlayers)
    {
        // Check if the number of regular players is less than 4
        if (numRegularPlayers < 4)
        {
            boolean validNumAIPlayers = false;
            int numAIPlayers = 0;

            // Calculate maximum number of accepted AI players
            int maxAIPlayers = ScrabbleGameModel.MAX_PLAYERS - numRegularPlayers;
            // Calculate minimum number of accepted AI players
            int minAIPlayers = 0;
            if (numRegularPlayers <= ScrabbleGameModel.MIN_PLAYERS)
            {
                minAIPlayers = ScrabbleGameModel.MIN_PLAYERS - numRegularPlayers;
            }

            // Perform loop until a valid number of AI players has been specified
            while (!validNumAIPlayers) {
                String numAIPlayersStr = JOptionPane.showInputDialog(scrabbleFrame, String.format("Please enter the number of AI players (Between %d and %d):", minAIPlayers, maxAIPlayers));
                // Convert String into integer
                try {
                    //Return if user cancels the input prompt
                    if (numAIPlayersStr == null) {
                        return -1;
                    }
                    numAIPlayers = Integer.parseInt(numAIPlayersStr);
                } catch (NumberFormatException e) {
                    continue;
                }

                // Stop looping until a valid number of AI players is specified
                if (numAIPlayers <= maxAIPlayers && numAIPlayers >= minAIPlayers) {
                    validNumAIPlayers = true;
                }
            }
            // Add the AI players to the game
            for (int i = 0; i < numAIPlayers; i++) {
                scrabbleModel.addAI("BOT " + (i + 1));
            }
            return numAIPlayers;
        }
        // Return default value of 0 if no AI players were configured
        return 0;
    }
}
