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
    private ArrayList<Tile> rackLetters;
    /**
     * Model this view is related to.
     */
    private RackModel rackModel;

    /**
     * Controller that interacts with this view
     */
    private ScrabbleController scrabbleController;

    /**
     * Constructs a RackPanelView.
     * @param rackModel Model this view is related to.
     */
    public RackPanelView(RackModel rackModel, ScrabbleController controller) {
        this.rackModel = rackModel;
        this.scrabbleController = controller;
        this.rackLetters = rackModel.getTiles();
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

        addTilesToPanel();
        this.revalidate();
        this.repaint();
    }

    /**
     * Helper function that adds the current tiles in the rack model to this view.
     *
     *
     * @author Yehan De Silva
     * @author Amin Zeina, 101186297
     * @version 1.1
     * @date November 11, 2022
     */
    private void addTilesToPanel() {
        this.rackLetters = rackModel.getTiles();
        for (int i = 0; i < rackLetters.size(); i++) {
            Tile tile = rackLetters.get(i);
            tile.setEnabled(true);
            this.add(tile);
        }
    }
}
