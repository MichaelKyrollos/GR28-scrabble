/**
 * ScrabbleView implements the view of the MVC design pattern. It is used as the user-interface of the program,
 * that subscribes to a model and updates whenever a status change is made.
 *
 * @author Yehan De Silva, 101185388
 * @version 1.0
 * @date November 11, 2022
 */
public interface ScrabbleView {

    /**
     * Check for status change and update view.
     *
     * @author Yehan De Silva, 101185388
     * @version 1.0
     * @date November 11, 2022
     */
    void update();

    /**
     * Show message with view.
     * @param message Message to be shown.
     *
     * @author Yehan De Silva, 101185388
     * @version 4.0
     * @date December 05, 2022
     */
    void getMessage(String message);
}
