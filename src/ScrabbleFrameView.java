// Import libraries
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import com.formdev.flatlaf.FlatIntelliJLaf;

/**
 * The ScrabbleFrameView class models the GUI (frame) of the
 * Scrabble game.
 *
 * @author Pathum Danthanarayana, 101181411
 * @author Yehan De Silva, 101185388
 * @version 1.2
 * @date November 13, 2022
 */
public class ScrabbleFrameView extends JFrame implements ScrabbleView {

    /** Fields **/
    private ArrayList<PlayerCardView> playerCards;
    private final Container contentPane;
    private JPanel boardPanel;
    private JPanel boardContainerPanel;
    private JPanel playerPanel;
    private JPanel buttonsPanel;
    private final JLabel currentTurn;
    private JPanel horizontalLabelPanel;
    private JPanel verticalLabelPanel;
    private final JButton playButton;
    private final JButton redrawButton;
    private final JButton skipButton;
    private final JMenuItem quitMenuItem;
    private final JMenuItem undoItem;
    private final JMenuItem redoItem;
    private final JMenuItem loadGame;
    private final JMenuItem saveGame;
    private final ScrabbleGameModel scrabbleModel;
    private final ScrabbleController scrabbleController;
    private boolean scrabbleGameStarted;

    /** Constants **/
    // Colours
    public static final Color BOARD_COLOR = new Color(48,90,119);
    public static final Color ACCENT_COLOR = new Color(64, 187, 161);
    public static final Color SQUARE_BACKGROUND_COLOR = new Color(36, 96, 120);
    public static final Color DOUBLE_LETTER_SQUARE_BACKGROUND_COLOR = new Color(93, 195, 235);
    public static final Color TRIPLE_LETTER_SQUARE_BACKGROUND_COLOR = new Color(113, 210, 113);
    public static final Color DOUBLE_WORD_SQUARE_BACKGROUND_COLOR = new Color(255,  120,  120);
    public static final Color TRIPLE_WORD_SQUARE_BACKGROUND_COLOR = new Color(243, 170, 68);
    public static final Color PLAYER_CARD_COLOR = new Color(28, 62, 91);
    public static final Color SELECTED_TILE_COLOR = new Color(223, 223, 223);
    public static final Color CENTER_SQUARE_COLOR = PLAYER_CARD_COLOR;
    public static final Color SELECTED_BUTTON_COLOR = new Color(202, 91, 89);

    // Spacing dimensions
    public static final Dimension MENU_SPACING = new Dimension(0, 80);
    public static final Dimension BUTTON_SPACING = new Dimension(10, 0);
    public static final Dimension MAIN_MIDDLE_SPACING = new Dimension(30, 0);
    public static final Dimension PLAYER_CARD_SPACING = new Dimension(0, 30);
    public static final Dimension CURRENT_TURN_SPACING = new Dimension(0, 20);

    // JPanel dimensions
    public static final Dimension BOARD_DIMENSIONS = new Dimension(650, 650);
    public static final Dimension HORIZONTAL_BOARD_LABEL_DIMENSIONS = new Dimension(650, 26);
    public static final Dimension VERTICAL_BOARD_LABEL_DIMENSIONS = new Dimension(26, 650);
    public static final Dimension PLAYER_PANEL_DIMENSIONS = new Dimension(500, 740);
    public static final Dimension BUTTON_PANEL_DIMENSIONS = new Dimension(400, 100);
    public static final Dimension PLAYER_CARD_DIMENSIONS = new Dimension(500, 125);

    /**
     * Constructs a Scrabble Frame.
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva, 101185388
     * @author Amin Zeina, 101186297
     * @version 1.4
     * @date November 13, 2022
     */
    public ScrabbleFrameView()
    {
        // Configure JFrame
        super("Scrabble Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 350);
        this.setResizable(false);

        // Set the UI theme for the Scrabble game
        this.setScrabbleGameUI();

        // Set icon for frame
        ImageIcon frameIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/frame_icon.jpg")));
        this.setIconImage(frameIcon.getImage());

        // Configure ContentPane
        contentPane = this.getContentPane();
        contentPane.setBackground(BOARD_COLOR);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));


        scrabbleModel = new ScrabbleGameModel();
        scrabbleController = new ScrabbleController(scrabbleModel, this);
        File customBoard = scrabbleController.getCustomBoard();
        scrabbleModel.createGameBoard(customBoard);
        scrabbleModel.addScrabbleView(this);

        currentTurn = new JLabel();

        playButton = new JButton("Play");
        redrawButton = new JButton("Redraw");
        skipButton = new JButton("Skip");

        // Set up the fonts for the Scrabble game
        this.setupFonts();

        // Configure Menu
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu menu = new JMenu("Quit");
        menuBar.add(menu);
        quitMenuItem = new JMenuItem("Quit Game");
        menu.add(quitMenuItem);
        quitMenuItem.addActionListener(scrabbleController);
        quitMenuItem.setEnabled(false);

        JMenu move = new JMenu("Move");
        menuBar.add(move);
        undoItem = new JMenuItem("Undo");
        redoItem = new JMenuItem(("Redo"));
        move.add(undoItem);
        move.add(redoItem);
        undoItem.addActionListener(scrabbleController);
        redoItem.addActionListener(scrabbleController);
        redoItem.setEnabled(false);
        undoItem.setEnabled(false);

        JMenu loadSaveMenu = new JMenu("Load/Save");
        menuBar.add(loadSaveMenu);
        loadGame = new JMenuItem("Load Game...");
        loadGame.addActionListener(scrabbleController);
        loadGame.setEnabled(true);
        saveGame = new JMenuItem("Save Game...");
        saveGame.addActionListener(scrabbleController);
        saveGame.setEnabled(false);
        loadSaveMenu.add(loadGame);
        loadSaveMenu.add(saveGame);

        // Set up the menu to play the game
        this.setupMenu();
        // Set the game started flag to false
        this.scrabbleGameStarted = false;
        this.setVisible(true);
    }

    /**
     * Returns the frame's play button.
     * @return frame's play button.
     * @author Yehan De Silva, 101185388
     * @version 1.0
     * @date November 13, 2022
     */
    public JButton getPlayButton() {return this.playButton;}

    /**
     * Returns the frame's redraw button.
     * @return frame's redraw button.
     * @author Yehan De Silva, 101185388
     * @version 1.0
     * @date November 13, 2022
     */
    public JButton getRedrawButton() {return this.redrawButton;}


    /**
     * Returns the frame's skip button.
     * @return frame's skip button.
     * @author Yehan De Silva, 101185388
     * @version 1.0
     * @date November 17, 2022
     */
    public JButton getSkipButton() {return this.skipButton;}

    /**
     * The isScrabbleGameStarted method returns the boolean flag
     * for indicating whether the Scrabble game has started or not
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     * @date December 5th, 2022
     *
     * @return true if the Scrabble game has started, and false otherwise
     */
    public boolean isScrabbleGameStarted()
    {
        return this.scrabbleGameStarted;
    }

    /**
     * The setScrabbleGameUI method sets the UI for the Scrabble game.
     * The method uses the custom FlatLaf library to set the 'Flat IntelliJ Laf'
     * theme, which provides a cleaner looking design for JOptionPanes and buttons.
     *
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     * @date December 6th, 2022
     */
    private void setScrabbleGameUI()
    {
        try
        {
            // Set the Flat Laf IntelliJ UI for the Scrabble game
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        }
        catch(Exception e)
        {
            // If error in setting FlatLaf UI, the Scrabble UI will use the default Java look and feel
            System.out.println( "Error in setting the UI for the Scrabble game: " + e);
        }
    }

    /**
     * The setupFonts method sets up and registers all the necessary fonts
     * for the Scrabble game. The two main fonts that are used for
     * the Scrabble game are the following:
     * Manrope Bold, Manrope Regular.
     *
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     * @date November 30th, 2022
     */
    private void setupFonts()
    {
        // Retrieve local graphics environment
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try
        {
            // Attempt to register the following fonts: Manrope Bold, Manrope Regular
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("resources/Manrope-Regular.ttf"))));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("resources/Manrope-Bold.ttf"))));
        }
        catch (FontFormatException | IOException e)
        {
            System.out.println("Error in creating custom fonts in setupFonts method: " + e);
        }
    }

    /**
     * The setupMenu method sets up the initial menu of the
     * Scrabble game.
     * This menu is what the user initially interacts with,
     * where they specify the number of players playing the Scrabble
     * game, and the name of each player.
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva, 101185388
     * @version 1.1
     * @date November 11, 2022
     */
    private void setupMenu()
    {
        // Set up main header for the menu
        JLabel mainHeader = new JLabel("Welcome to Scrabble!");
        mainHeader.setFont(new Font("Manrope", Font.BOLD, 38));
        mainHeader.setForeground(Color.WHITE);
        mainHeader.setAlignmentX(CENTER_ALIGNMENT);
        mainHeader.setHorizontalAlignment(JLabel.CENTER);

        // Set up the tagline for the menu
        JLabel tagLine = new JLabel("Developed by Group 28");
        tagLine.setFont(new Font("Manrope", Font.PLAIN, 22));
        tagLine.setForeground(Color.WHITE);
        tagLine.setAlignmentX(CENTER_ALIGNMENT);
        tagLine.setHorizontalAlignment(JLabel.CENTER);

        // Set up the play button for the menu
        JButton playButton = new JButton("Let's play!");
        playButton.setBackground(ACCENT_COLOR);
        playButton.setFocusPainted(false);
        playButton.setForeground(Color.WHITE);
        playButton.setFont(new Font("Manrope", Font.BOLD, 18));
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
     * @author Yehan De Silva, 101185388
     * @author Amin Zeina, 101186297
     * @version 1.3
     * @date November 13, 2022
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
        this.setSize(1250, 800);
        // Apply a new layout to the ContentPane
        contentPane.setLayout(new FlowLayout());

        // JPanel #1a: Scrabble BoardModel
        boardPanel = new BoardPanelView(scrabbleModel.getGameBoard(), scrabbleController);

        // Add the horizontal and vertical labels to the Scrabble board
        horizontalLabelPanel = new JPanel();
        verticalLabelPanel = new JPanel();
        this.addScrabbleBoardLabels();

        buttonsPanel = new JPanel();

        // JPanel #2: PlayerModel Panel
        playerPanel = new JPanel();
        playerPanel.setPreferredSize(PLAYER_PANEL_DIMENSIONS);
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBackground(BOARD_COLOR);
        playerPanel.add(Box.createRigidArea(CURRENT_TURN_SPACING));

        // Configure current turn label (initially Player 1)
        currentTurn.setFont(new Font("Manrope", Font.PLAIN, 22));
        currentTurn.setAlignmentX(CENTER_ALIGNMENT);
        currentTurn.setHorizontalAlignment(JLabel.CENTER);
        currentTurn.setForeground(Color.WHITE);
        playerPanel.add(currentTurn);
        playerPanel.add(Box.createRigidArea(CURRENT_TURN_SPACING));

        // Add the player cards to the JPanel
        this.addPlayerCards();
        // Add the buttons to the JPanel
        JButton[] buttonsToAdd = new JButton[]{playButton, redrawButton, skipButton};
        this.addButtons(buttonsToAdd);

        // Add all the JPanels to ContentPane
        contentPane.add(boardContainerPanel);
        // Add spacing in between board panel and player panel
        contentPane.add(Box.createRigidArea(MAIN_MIDDLE_SPACING));
        contentPane.add(playerPanel);

        // setup first turn of game by enabling/disabling certain buttons
        scrabbleModel.setupFirstTurn();

        // enable quit menu now that the game is running.
        quitMenuItem.setEnabled(true);
        // Enable load and save game
        loadGame.setEnabled(true);
        saveGame.setEnabled(true);

        // Set the game started flag to true
        this.scrabbleGameStarted = true;
    }

    /**
     * The addScrabbleBoardLabels method adds the horizontal and vertical
     * labels to the Scrabble board.
     *
     * @author Pathum Danthanarayana, 101181411
     * @version 1.1
     * @date November 22nd, 2022
     */
    private void addScrabbleBoardLabels()
    {
        // Initialize font for labels
        Font labelFont = new Font("Manrope", Font.PLAIN, 14);
        // Define constant spacing for the horizontal numbers
        Dimension SINGLE_DIGIT_INITIAL_SPACING = new Dimension(34, 0);
        Dimension SINGLE_DIGIT_SPACING = new Dimension(25, 0);
        Dimension DOUBLE_DIGIT_SPACING = new Dimension(20, 0);
        // Define constant spacing for the vertical letters
        Dimension LETTER_INITIAL_SPACING = new Dimension(0, 14);
        Dimension LETTER_SPACING = new Dimension(0, 23);

        // JPanel #1b: Horizontal labelling for scrabble board
        horizontalLabelPanel.setBackground(BOARD_COLOR);
        horizontalLabelPanel.setPreferredSize(HORIZONTAL_BOARD_LABEL_DIMENSIONS);
        horizontalLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Traverse through numbers 1 through 15
        for (int i = 1; i < BoardModel.SIZE + 1; i++)
        {
            // Create a JLabel for the number
            JLabel numLabel = new JLabel(String.valueOf(i));
            numLabel.setFont(labelFont);
            numLabel.setForeground(Color.WHITE);

            // Check if the number is 1
            if (i == 1)
            {
                // If so, add the initial spacing to the panel before adding 1
                horizontalLabelPanel.add(Box.createRigidArea(SINGLE_DIGIT_INITIAL_SPACING));
            }
            // Check if the number is a single digit
            else if (i > 10)
            {
                // If so, add the single digit spacing to the panel before adding the number
                horizontalLabelPanel.add(Box.createRigidArea(DOUBLE_DIGIT_SPACING));
            }
            else
            {
                // Otherwise, add the double-digit spacing to the panel before adding the number
                horizontalLabelPanel.add(Box.createRigidArea(SINGLE_DIGIT_SPACING));
            }
            // Add the JLabel to the panel
            horizontalLabelPanel.add(numLabel);
        }

        // JPanel #1c: Vertical labelling for scrabble board
        verticalLabelPanel.setBackground(BOARD_COLOR);
        verticalLabelPanel.setLayout(new BoxLayout(verticalLabelPanel, BoxLayout.PAGE_AXIS));
        verticalLabelPanel.setPreferredSize(VERTICAL_BOARD_LABEL_DIMENSIONS);
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};

        // Traverse through letters from A to O
        for (String letter : letters)
        {
            // Create a JLabel for the letter
            JLabel letterLabel = new JLabel(letter);
            letterLabel.setFont(labelFont);
            letterLabel.setForeground(Color.WHITE);

            // Check if the letter equals 'A'
            if (letter.equals("A"))
            {
                // If so, add the initial spacing to the panel before adding 'A'
                verticalLabelPanel.add(Box.createRigidArea(LETTER_INITIAL_SPACING));
            }
            else
            {
                // Otherwise, add the regular spacing to the panel before adding the letter
                verticalLabelPanel.add(Box.createRigidArea(LETTER_SPACING));
            }
            // Add the JLabel to the panel
            verticalLabelPanel.add(letterLabel);
        }

        // Container JPanel for holding JPanel #1a, #1b, and #1c (board panel, horizontal label panel, and vertical label panel)
        boardContainerPanel = new JPanel();
        boardContainerPanel.setLayout(new BorderLayout());
        boardContainerPanel.add(boardPanel, BorderLayout.CENTER);
        boardContainerPanel.add(horizontalLabelPanel, BorderLayout.NORTH);
        boardContainerPanel.add(verticalLabelPanel, BorderLayout.WEST);
    }

    /**
     * The addPlayerCards method adds player cards (which contains
     * the player's name, their score, and their rack) to the JPanel
     * that will hold all the player cards.
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva, 101185388
     * @version 1.1
     */
    private void addPlayerCards()
    {
        for (PlayerModel player : scrabbleModel.getPlayers()) {
            PlayerCardView playerCard = new PlayerCardView(player);
            // Keep a reference to the player card
            this.playerCards.add(playerCard);
            playerPanel.add(playerCard);
            playerPanel.add(Box.createRigidArea(PLAYER_CARD_SPACING));
            player.updateScrabbleViews();
        }
    }

    /**
     * The addButtons method adds the given buttons to the player panel in
     * the Scrabble game.
     *
     * @param buttons - An array of Buttons to be added
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva, 101185388
     * @version 1.2
     * @date November 13, 2022
     */
    private void addButtons(JButton[] buttons)
    {
        // Create and configure JPanel to store the buttons
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setMaximumSize(BUTTON_PANEL_DIMENSIONS);
        buttonsPanel.setBackground(BOARD_COLOR);

        for (JButton button : buttons) {
            // Create and configure a new JButton
            button.setBackground(ACCENT_COLOR);
            button.setFocusPainted(false);
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Manrope", Font.BOLD, 18));
            button.addActionListener(scrabbleController);

            buttonsPanel.add(button);
            buttonsPanel.add(Box.createRigidArea(BUTTON_SPACING));
        }
        // Add the button panel to the player panel
        playerPanel.add(buttonsPanel);
    }

    /**
     * Updates the scrabble frame view with any updates that happened in the model.
     *
     * @author Yehan De Silva, 101185388
     * @version 4.0
     * @date December 02, 2022
     */
    @Override
    public void update() {

        //Update current turn string with current player
        currentTurn.setText("Current turn:   " + scrabbleModel.getCurrentPlayer().getName());

        //Enable/disable the undo item if there are turns to undo
        this.undoItem.setEnabled(scrabbleModel.getUndoStack().size() > 1);

        //Enable/disable the redo item if there are turns to redo
        this.redoItem.setEnabled(!scrabbleModel.getRedoStack().isEmpty());

        if (scrabbleModel.getGameStatusChanged()) {
            //Updating board
            for (Component component : this.boardContainerPanel.getComponents()) {
                this.boardContainerPanel.remove(component);
            }
            boardPanel = new BoardPanelView(scrabbleModel.getGameBoard(), scrabbleController);
            boardContainerPanel.add(boardPanel, BorderLayout.CENTER);
            boardContainerPanel.add(horizontalLabelPanel, BorderLayout.NORTH);
            boardContainerPanel.add(verticalLabelPanel, BorderLayout.WEST);
            BoardPanelView bpv = (BoardPanelView) this.boardPanel;
            bpv.update();

            //Updating players
            for (Component component : this.playerPanel.getComponents()) {
                this.playerPanel.remove(component);
            }
            playerPanel.add(Box.createRigidArea(CURRENT_TURN_SPACING));
            playerPanel.add(currentTurn);
            playerPanel.add(Box.createRigidArea(CURRENT_TURN_SPACING));
            this.playerCards.clear();
            this.addPlayerCards();
            playerPanel.add(buttonsPanel);
        }

        this.revalidate();
        this.repaint();
    }

    /**
     * Displays message from model.
     * @param message Message to be shown.
     *
     * @author Yehan De Silva, 101185388
     * @version 4.0
     * @date December 05, 2022
     */
    @Override
    public void getMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /** Main method **/
    public static void main(String[] args)
    {
        new ScrabbleFrameView();
    }
}
