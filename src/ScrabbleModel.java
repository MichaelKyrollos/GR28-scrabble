import java.util.ArrayList;
import java.util.List;

/**
 * ScrabbleModel implements the model of the MVC design pattern. It is used as the logic of the program,
 * and updates all subscribed views when a status change has been made.
 *
 * @author Yehan De Silva, 101185388
 * @version 1.0
 * @date November 11, 2022
 */
public abstract class ScrabbleModel {

    /**
     * List of all scrabbleViews subscribed to this model.
     */
    private final List<ScrabbleView> scrabbleViews;

    /**
     * Constructs a Scrabble model.
     * @author Yehan De Silva, 101185388
     * @version 1.0
     * @date November 11, 2022
     */
    public ScrabbleModel() {
        scrabbleViews = new ArrayList<>();
    }

    /**
     * Subscribes a view to this model.
     * @param view View to be subscribed to this model.
     *
     * @author Yehan De Silva, 101185388
     * @version 1.0
     * @date November 11, 2022
     */
    protected void addScrabbleView(ScrabbleView view){
        this.scrabbleViews.add(view);
    }

    /**
     * Notifies all subscribers of this model of a status change.
     *
     * @author Yehan De Silva, 101185388
     * @version 1.0
     * @date November 11, 2022
     */
    protected void updateScrabbleViews() {
        for(ScrabbleView v: scrabbleViews) {
            v.update();
        }
    }

    /**
     * Notifies all subscribers of new message
     *
     * @author Yehan De Silva, 101185388
     * @version 4.0
     * @date December 05, 2022
     */
    protected void messageScrabbleViews(String message) {
        for(ScrabbleView v: scrabbleViews) {
            v.getMessage(message);
        }
    }
}
