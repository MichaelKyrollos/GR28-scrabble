import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The ScrabbleFrameView class models the GUI (frame) of the
 * Scrabble game.
 *
 * @author Pathum Danthanarayana, 101181411
 * @author Yehan De Silva
 * @version 1.1
 * @date November 11th, 2022
 */
public class ScrabbleFrameView extends JFrame implements ScrabbleView {

    /** Fields **/
    private ArrayList<PlayerCardView> playerCards;
    private Container contentPane;
    private JPanel boardPanel;
    private JPanel playerPanel;
    private JLabel currentTurn;
    private ScrabbleGameModel scrabbleModel;
    private ScrabbleController scrabbleController;

    /** Constants **/
    // Colours
    public static final Color BOARD_COLOR = new Color(48,90,119);
    public static final Color ACCENT_COLOR = new Color(64, 187, 161);
    public static final Color SQUARE_BACKGROUND_COLOR = new Color(36, 96, 120);
    public static final Color SQUARE_BORDER_COLOR = new Color(34, 178, 194);
    public static final Color PLAYER_CARD_COLOR = new Color(28, 62, 91);

    // Spacing dimensions
    public static final Dimension MENU_SPACING = new Dimension(0, 80);
    public static final Dimension BUTTON_SPACING = new Dimension(10, 0);
    public static final Dimension MAIN_MIDDLE_SPACING = new Dimension(40, 0);
    public static final Dimension PLAYER_CARD_SPACING = new Dimension(0, 30);
    public static final Dimension CURRENT_TURN_SPACING = new Dimension(0, 20);

    // JPanel dimensions
    public static final Dimension BOARD_DIMENSIONS = new Dimension(650, 650);
    public static final Dimension PLAYER_PANEL_DIMENSIONS = new Dimension(400, 740);
    public static final Dimension BUTTON_PANEL_DIMENSIONS = new Dimension(400, 100);
    public static final Dimension PLAYER_CARD_DIMENSIONS = new Dimension(400, 125);

    // Fonts
    public static final Font MENU_HEADER_FONT = new Font("Arial", Font.BOLD, 38);
    public static final Font MENU_TAGLINE_FONT = new Font("Arial", Font.PLAIN, 22);
    public static final Font CURRENT_TURN_FONT = new Font("Arial", Font.PLAIN, 22);
    public static final Font PLAYER_BODY_FONT = new Font("Arial", Font.BOLD, 22);
    public static final Font PLAYER_NAME_FONT = new Font("Arial", Font.BOLD, 28);
    public static final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 18);

    /**
     * Constructs a Scrabble Frame.
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 1.1
     * @date November 11, 2022
     */
    public ScrabbleFrameView()
    {
        // Configure JFrame
        super("Scrabble Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 350);
        this.setResizable(false);

        // Configure ContentPane
        contentPane = this.getContentPane();
        contentPane.setBackground(BOARD_COLOR);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        scrabbleModel = new ScrabbleGameModel();
        scrabbleController = new ScrabbleController(scrabbleModel, this);
        currentTurn = new JLabel();

        // Setup the menu to play the game
        this.setupMenu();
        this.setVisible(true);
    }

    /**
     * The setupMenu method sets up the initial menu of the
     * Scrabble game.
     * This menu is what the user initially interacts with,
     * where they specify the number of players playing the Scrabble
     * game, and the name of each player.
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 1.1
     * @date November 11, 2022
     */
    private void setupMenu()
    {
        // Set up main header for the menu
        JLabel mainHeader = new JLabel("Welcome to Scrabble!");
        mainHeader.setFont(ScrabbleFrameView.MENU_HEADER_FONT);
        mainHeader.setForeground(Color.WHITE);
        mainHeader.setAlignmentX(CENTER_ALIGNMENT);
        mainHeader.setHorizontalAlignment(JLabel.CENTER);

        // Set up the tagline for the menu
        JLabel tagLine = new JLabel("Developed by Group 28");
        tagLine.setFont(ScrabbleFrameView.MENU_TAGLINE_FONT);
        tagLine.setForeground(Color.WHITE);
        tagLine.setAlignmentX(CENTER_ALIGNMENT);
        tagLine.setHorizontalAlignment(JLabel.CENTER);

        // Set up the play button for the menu
        JButton playButton = new JButton("Let's play!");
        playButton.setBackground(ACCENT_COLOR);
        playButton.setFocusPainted(false);
        playButton.setForeground(Color.WHITE);
        playButton.setFont(BUTTON_FONT);
        playButton.setAlignmentX(CENTER_ALIGNMENT);
        playButton.addActionListener(scrabbleController);

        contentPane.add(Box.createRigidArea(MENU_SPACING));
        contentPane.add(mainHeader);
        contentPane.add(Box.createRigidArea(CURRENT_TURN_SPACING));
        contentPane.add(tagLine);
        contentPane.add(Box.createRigidArea(PLAYER_CARD_SPACING));
        contentPane.add(playButton);
    }

    /**
     * The startGame method starts the Scrabble game by setting up
     * the main game window (which includes the Scrabble board,
     * player cards, etc.).
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 1.1
     * @date November 11, 2022
     */
    public void startGame()
    {
        // Initialize ArrayList of player cards
        this.playerCards = new ArrayList<>();

        // Remove all elements from the ContentPane
        this.contentPane.removeAll();
        // Repaint the JFrame
        this.repaint();
        // Change the size of the JFrame
        this.setSize(1200, 750);
        // Apply a new layout to the ContentPane
        contentPane.setLayout(new FlowLayout());

        // JPanel #1: Scrabble BoardModel
        boardPanel = new BoardPanelView(scrabbleModel.getGameBoard(), scrabbleController);

        // JPanel #2: PlayerModel Panel
        playerPanel = new JPanel();
        playerPanel.setPreferredSize(PLAYER_PANEL_DIMENSIONS);
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBackground(BOARD_COLOR);
        playerPanel.add(Box.createRigidArea(CURRENT_TURN_SPACING));

        // Configure current turn label (initially Player 1)
        currentTurn.setFont(CURRENT_TURN_FONT);
        currentTurn.setAlignmentX(CENTER_ALIGNMENT);
        currentTurn.setHorizontalAlignment(JLabel.CENTER);
        currentTurn.setForeground(Color.WHITE);
        playerPanel.add(currentTurn);
        playerPanel.add(Box.createRigidArea(CURRENT_TURN_SPACING));

        // Add the player cards to the JPanel
        this.addPlayerCards();
        // Add the buttons to the JPanel
        this.addButtons(new String[]{"Play", "Redraw", "Skip"});

        // Add all the JPanels to ContentPane
        contentPane.add(boardPanel);
        // Add spacing in between board panel and player panel
        contentPane.add(Box.createRigidArea(MAIN_MIDDLE_SPACING));
        contentPane.add(playerPanel);
    }

    /**
     * The addPlayerCards method adds player cards (which contains
     * the player's name, their score, and their rack) to the JPanel
     * that will hold all the player cards.
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 1.1
     */
    private void addPlayerCards()
    {
        for (PlayerModel player : scrabbleModel.getPlayers()) {
            PlayerCardView playerCard = new PlayerCardView(player, scrabbleController);
            // Keep a reference to the player card
            this.playerCards.add(playerCard);
            playerPanel.add(playerCard);
            playerPanel.add(Box.createRigidArea(PLAYER_CARD_SPACING));
        }
    }

    /**
     * The addButtons method adds important function buttons
     * (e.g. Play, Redraw, Skip, etc.) to the player panel in
     * the Scrabble game. The method creates a button for each
     * String in the specified array of Strings.
     *
     * @param buttonNames - An array of Strings containing the names of the buttons
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 1.1
     */
    private void addButtons(String[] buttonNames)
    {
        // Create and configure JPanel to store the buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setMaximumSize(BUTTON_PANEL_DIMENSIONS);
        buttonsPanel.setBackground(BOARD_COLOR);

        for (String buttonName : buttonNames) {
            // Create and configure a new JButton
            JButton button = new JButton(buttonName);
            button.setBackground(ACCENT_COLOR);
            button.setFocusPainted(false);
            button.setForeground(Color.WHITE);
            button.setFont(BUTTON_FONT);
            button.addActionListener(scrabbleController);

            buttonsPanel.add(button);
            buttonsPanel.add(Box.createRigidArea(BUTTON_SPACING));
        }
        // Add the button panel to the player panel
        playerPanel.add(buttonsPanel);
    }

    /**
     * Updates the current turn text with the name of the current player.
     *
     * @author Yehan De Silva
     * @version 1.1
     * @date November 11, 2022
     */
    @Override
    public void update() {
        currentTurn.setText("Current turn:   " + scrabbleModel.getCurrentPlayer());
    }

    /** Main method **/
    public static void main(String[] args)
    {
        new ScrabbleFrameView();
    }
}
