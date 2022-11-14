import javax.swing.*;
import java.awt.*;

/**
 * The PlayerCardView class models a player card in the Scrabble
 * game GUI. A player card contains the player's name, the
 * player's point value, and the player's rack which contains
 * up to 7 buttons (each modelling a letter).
 *
 * @author Pathum Danthanarayana, 101181411
 * @author Yehan De Silva
 * @version 1.2
 * @date November 11th, 2022
 */
public class PlayerCardView extends JPanel implements ScrabbleView {

    /** Fields **/
    private JLabel playerName;
    private JLabel playerScore;
    private JPanel rackPanel;
    private PlayerModel playerModel;
    private ScrabbleController scrabbleController;

    /** Constructor **/
    public PlayerCardView(PlayerModel player, ScrabbleController controller)
    {
        // Configure JPanel
        super();

        this.scrabbleController = controller;
        this.playerModel = player;
        this.playerModel.addScrabbleView(this);
        this.setMaximumSize((ScrabbleFrameView.PLAYER_CARD_DIMENSIONS));
        this.setBackground(ScrabbleFrameView.PLAYER_CARD_COLOR);
        this.setLayout(new BorderLayout());

        // Initialize a font manager
        FontManager fontManager = new FontManager();

        // Configure player name
        this.playerName = new JLabel(player.getName());
        this.playerName.setFont(fontManager.getManropeBold().deriveFont(Font.BOLD, 28f));
        this.playerName.setForeground(Color.WHITE);

        // Configure player score
        this.playerScore = new JLabel("Score:   0");
        this.playerScore.setFont(fontManager.getManropeBold().deriveFont(Font.BOLD, 22f));
        this.playerScore.setForeground(Color.WHITE);

        // Configure the JPanel that will store the letters (buttons) in the rack
        this.rackPanel = new RackPanelView(player.getRack(), scrabbleController);

        // Add the player name to the top of the panel
        this.add(this.playerName, BorderLayout.NORTH);
        // Add the player score to the center of the panel
        this.add(this.playerScore, BorderLayout.CENTER);
        // Add the rack to the bottom of the panel
        this.add(rackPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the score of the player.
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 1.1
     * @date November 11th, 2022
     */
    @Override
    public void update() {
        this.playerScore.setText("Score:   " + playerModel.getScore());
    }
}
