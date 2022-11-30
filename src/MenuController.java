import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The MenuController is a Controller tasked with controlling inputs made in the MenuBar of the scrabble game.
 *
 * @author Yehan De Silva
 * @version 4.0
 * @date November 30, 2022
 */
public class MenuController implements ActionListener {

    private final ScrabbleGameModel scrabbleModel;

    /**
     * Constructs a MenuController object.
     * @param model Corresponding model.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date November 30, 2022
     */
    public MenuController(ScrabbleGameModel model) {
        this.scrabbleModel = model;
    }

    /**
     * This method is executed whenever a menu item is clicked within the menu bar.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date November 30, 2022
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //Quit game selected
        if (e.getActionCommand().equals("Quit Game")) {
            scrabbleModel.endGame();
        }
    }
}
