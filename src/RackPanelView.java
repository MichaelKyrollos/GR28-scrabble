import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * RackPaneView is a view that visualizes a Scrabble rack.
 *
 * @author Yehan De Silva
 * @version 1.0
 * @date November 11, 2022
 */
public class RackPanelView extends JPanel implements ScrabbleView {

    /**
     * Tiles on the rack.
     */
    private ArrayList<JButton> rackLetters;
    /**
     * Model this view is related to.
     */
    private RackModel rackModel;

    /**
     * Constructs a RackPanelView.
     * @param rackModel Model this view is related to.
     */
    public RackPanelView(RackModel rackModel) {
        this.rackModel = rackModel;
        this.rackLetters = new ArrayList<>();
        this.rackModel.addScrabbleView(this);

        this.setLayout(new FlowLayout());
        this.setBackground(ScrabbleFrameView.ACCENT_COLOR);

        this.addTilesToPanel();
    }

    /**
     * The updateRack method updates the letters in the player's rack.
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 1.1
     * @date November 11th, 2022
     */
    @Override
    public void update() {
        //Clear all components before adding new
        Component[] componentList = this.getComponents();
        for (Component component : componentList) {
            this.remove(component);
        }
        this.revalidate();
        this.repaint();
        this.rackLetters.clear();
        addTilesToPanel();
    }

    /**
     * Helper function that adds the current tiles in the rack model to this view.
     *
     * @author Yehan De Silva
     * @version 1.0
     * @date November 11, 2022
     */
    private void addTilesToPanel() {
        ArrayList<Tile> tiles = rackModel.getTiles();
        for (Tile tile : tiles) {
            // Create and configure a new JButton
            JButton rackLetter = new JButton(Character.toString(tile.getLetter()));
            rackLetter.setBackground(ScrabbleFrameView.ACCENT_COLOR);
            rackLetter.setFocusPainted(false);
            rackLetter.setForeground(Color.WHITE);
            rackLetter.setFont(ScrabbleFrameView.PLAYER_BODY_FONT);
            // Keep a reference to the letter
            rackLetters.add(rackLetter);
            // Add the letter to the rack panel
            this.add(rackLetter);
        }
    }
}
