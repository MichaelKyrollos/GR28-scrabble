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
    private final RackModel rackModel;

    /**
     * Constructs a RackPanelView.
     * @param rackModel Model this view is related to.
     *
     * @author Yehan De Silva
     * @version 3.0
     * @date November 22, 2022
     */
    public RackPanelView(RackModel rackModel) {
        this.rackModel = rackModel;
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
     * @author Yehan De Silva
     * @author Amin Zeina, 101186297
     * @version 1.1
     * @date November 11, 2022
     */
    private void addTilesToPanel() {
        this.rackLetters = rackModel.getTiles();
        for (Tile tile : rackLetters) {
            tile.setEnabled(true);
            this.add(tile);
        }
    }
}
