import javax.swing.*;
import java.awt.*;

/**
 * The PlayerCardView class models a player card in the Scrabble
 * game GUI. A player card contains the player's name, the
 * player's point value, and the player's rack which contains
 * up to 7 buttons (each modelling a letter).
 *
 * @author Pathum Danthanarayana, 101181411
 * @author Yehan De Silva, 101185388
 * @version 1.2
 * @date November 11th, 2022
 */
public class PlayerCardView extends JPanel implements ScrabbleView {

    private final JLabel playerScore;
    private final PlayerModel playerModel;

    /** Constructor **/
    public PlayerCardView(PlayerModel player)
    {
        // Configure JPanel
        super();

        this.playerModel = player;
        this.playerModel.addScrabbleView(this);
        this.setMaximumSize((ScrabbleFrameView.PLAYER_CARD_DIMENSIONS));
        this.setBackground(ScrabbleFrameView.PLAYER_CARD_COLOR);
        this.setLayout(new BorderLayout());

        // Configure player name
        JLabel playerName = new JLabel(player.getName());
        playerName.setFont(new Font("Manrope", Font.BOLD, 28));
        playerName.setForeground(Color.WHITE);

        // Configure player score
        this.playerScore = new JLabel("Score:   0");
        this.playerScore.setFont(new Font("Manrope", Font.BOLD, 22));
        this.playerScore.setForeground(Color.WHITE);

        // Configure the JPanel that will store the letters (buttons) in the rack
        JPanel rackPanel = new RackPanelView(player.getRack());

        // Add the player name to the top of the panel
        this.add(playerName, BorderLayout.NORTH);
        // Add the player score to the center of the panel
        this.add(this.playerScore, BorderLayout.CENTER);
        // Add the rack to the bottom of the panel
        this.add(rackPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the score of the player.
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva, 101185388
     * @version 1.1
     * @date November 11th, 2022
     */
    @Override
    public void update() {
        this.playerScore.setText("Score:   " + playerModel.getScore());
    }

    /**
     * Displays message from model.
     * @param message Message to be shown.
     *
     * @author Yehan De Silva, 101185388
     * @version 4.0
     * @date December 05, 2022
     */
    @Override
    public void getMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
