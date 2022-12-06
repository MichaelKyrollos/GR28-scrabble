import javax.swing.*;
import java.awt.*;

/**
 * BoardPanelView is a view that visualizes the Scrabble game board.
 *
 * @author Yehan De Silva
 * @author Pathum Danthanarayana, 101181411
 * @version 1.0
 * @date November 11, 2022
 */
public class BoardPanelView extends JPanel implements ScrabbleView {

    /**
     * The model this view is related to.
     */
    private final BoardModel boardModel;
    /**
     * 2D array of squares (which extend JButton) used to represent the squares of a board.
     */
    private Square[][] squares;

    private final ScrabbleController scrabbleController;

    /**
     * Constructor that constructs a BoardPanelView.
     * @param boardModel Model this view is related to.
     *
     * @author Yehan De Silva
     * @version 1.0
     * @date November 11, 2022
     */
    public BoardPanelView(BoardModel boardModel, ScrabbleController controller) {
        this.boardModel = boardModel;
        boardModel.addScrabbleView(this);
        this.scrabbleController = controller;
        this.setPreferredSize(ScrabbleFrameView.BOARD_DIMENSIONS);
        this.setBackground(ScrabbleFrameView.BOARD_COLOR);
        this.setLayout(new GridLayout(BoardModel.SIZE, BoardModel.SIZE));

        squares = boardModel.getSquares();
        this.addSquares();
    }

    /**
     * The addSquares method creates squares for a 15x15
     * Scrabble board. Each square is a JButton and is added to a
     * JPanel (board) that is configured to have a 15x15 grid layout.
     * TODO refactor this to solve the issue of buttons not showing correctly unless they are remade here
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @author Amin Zeina, 101186297
     * @version 1.3
     * @date November 11, 2022
     */
    private void addSquares()
    {
        // Traverse through each row and column
        for (int i = 0; i < BoardModel.SIZE; i++)
        {
            for (int j = 0; j < BoardModel.SIZE; j++)
            {
                Square square = squares[i][j];
                // only add actionListener if there isn't one already
                if (square.getActionListeners().length == 0) {
                    squares[i][j].addActionListener(scrabbleController);
                }
                // Add the button to the board panel
                this.add(squares[i][j]);
                // update board with new copied button
                squares[i][j].setText(squares[i][j].toString());

                // Check if the current square is the center square on the board
                if (i == ((BoardModel.SIZE - 1) / 2) && j == ((BoardModel.SIZE - 1) / 2))
                {
                    squares[i][j].setBackground(ScrabbleFrameView.CENTER_SQUARE_COLOR);
                }
            }
        }
    }

    /**
     * Updates the tiles placed on the board.
     *
     * @author Yehan De Silva
     * @author Amin Zeina
     * @version 1.1
     * @date November 11, 2022
     */
    @Override
    public void update() {
        squares = boardModel.getSquares();
        //Clear all components before adding new
        Component[] componentList = this.getComponents();
        for (Component component : componentList) {
            this.remove(component);
        }

        this.addSquares();
        this.revalidate();
        this.repaint();
    }

    /**
     * Displays message from model.
     * @param message Message to be shown.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 05, 2022
     */
    @Override
    public void getMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
