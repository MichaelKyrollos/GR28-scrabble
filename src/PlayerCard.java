// Pathum Danthanarayana, 101181411

// Import libraries
import javax.swing.*;
import java.awt.*;

/**
 * The PlayerCard class models a player card in the Scrabble
 * game GUI. A player card contains the player's name, the
 * player's point value, and the player's rack which contains
 * 7 buttons (each modelling a letter).
 *
 * @author Pathum Danthanarayana, 101181411
 * @version 1.0
 * @date November 9th, 2022
 */

public class PlayerCard extends JPanel {

    /** Fields **/
    private JLabel playerName;
    private JLabel playerScore;
    private JButton[] rackLetters;
    private static final int RACK_SIZE = 7;

    /** Constructor **/
    public PlayerCard(String playerName)
    {
        // Configure JPanel
        super();
        this.setMaximumSize((ScrabbleFrame.playerCardDimensions));
        this.setBackground(ScrabbleFrame.playerCardColor);
        this.setLayout(new BorderLayout());

        // Configure player name
        this.playerName = new JLabel(playerName);
        this.playerName.setFont(ScrabbleFrame.playerNameFont);
        this.playerName.setForeground(Color.WHITE);

        // Configure player score
        this.playerScore = new JLabel("Score:   0");
        this.playerScore.setFont(ScrabbleFrame.playerBodyFont);
        this.playerScore.setForeground(Color.WHITE);

        // Initialize the player's rack
        this.rackLetters = new JButton[RACK_SIZE];

        // Configure the JPanel that will store the letters (buttons) in the rack
        JPanel rackPanel = new JPanel(new FlowLayout());
        rackPanel.setBackground(ScrabbleFrame.accentColor);
        // Add 7 letters (buttons) to the rack panel
        for (int i = 0; i < RACK_SIZE; i++)
        {
            // Create and configure a new JButton
            JButton rackLetter = new JButton("A");
            rackLetter.setBackground(ScrabbleFrame.accentColor);
            rackLetter.setFocusPainted(false);
            rackLetter.setForeground(Color.WHITE);
            rackLetter.setFont(ScrabbleFrame.playerBodyFont);
            // Keep a reference to the letter
            rackLetters[i] = rackLetter;
            // Add the letter to the rack panel
            rackPanel.add(rackLetter);
        }

        // Add the player name to the top of the panel
        this.add(this.playerName, BorderLayout.NORTH);
        // Add the player score to the center of the panel
        this.add(this.playerScore, BorderLayout.CENTER);
        // Add the rack to the bottom of the panel
        this.add(rackPanel, BorderLayout.SOUTH);
    }
}
