import javax.swing.*;
import javax.swing.border.Border;
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
    private BoardModel boardModel;
    /**
     * 2D array of buttons used to represent the squares of a board.
     */
    private JButton[][] squares;

    /**
     * Constructor that constructs a BoardPanelView.
     * @param boardModel Model this view is related to.
     *
     * @author Yehan De Silva
     * @version 1.0
     * @date November 11, 2022
     */
    public BoardPanelView(BoardModel boardModel) {
        this.boardModel = boardModel;

        this.setPreferredSize(ScrabbleFrameView.BOARD_DIMENSIONS);
        this.setBackground(ScrabbleFrameView.BOARD_COLOR);
        this.setLayout(new GridLayout(BoardModel.SIZE, BoardModel.SIZE));

        squares = new JButton[BoardModel.SIZE][BoardModel.SIZE];
        this.addSquares();
    }

    /**
     * The addSquares method creates squares for a 15x15
     * Scrabble board. Each square is a JButton and is added to a
     * JPanel (board) that is configured to have a 15x15 grid layout.
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 1.1
     * @date November 11, 2022
     */
    private void addSquares()
    {
        // Traverse through each row and column
        for (int i = 0; i < BoardModel.SIZE; i++)
        {
            for (int j = 0; j < BoardModel.SIZE; j++)
            {
                // Create and configure a new JButton
                JButton button = new JButton(" ");
                button.setBackground(ScrabbleFrameView.SQUARE_BACKGROUND_COLOR);
                button.setFont(new Font("Arial", Font.BOLD, 18));
                button.setFocusPainted(false);
                // Create and add a border to the button
                Border border = BorderFactory.createLineBorder(ScrabbleFrameView.SQUARE_BORDER_COLOR);
                button.setBorder(border);
                button.setForeground(Color.WHITE);

                // Keep a reference to the button
                squares[i][j] = button;
                // button.addActionListener();
                // Add the button to the board panel
                this.add(button);
            }
        }
    }

    /**
     * Updates the tiles placed on the board.
     *
     * @author Yehan De Silva
     * @version 1.0
     * @date November 11, 2022
     */
    @Override
    public void update() {
        Square[][] tileSquares = boardModel.getSquares();
        for (int i = 0; i < BoardModel.SIZE; i++) {
            for (int j = 0; j < BoardModel.SIZE; j++) {
                squares[i][j].setText(Character.toString(tileSquares[i][j].getTile().getLetter()));
            }
        }
    }
}
