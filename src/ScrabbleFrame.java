// Pathum Danthanarayana, 101181411

// Import libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.border.Border;

/**
 * The ScrabbleFrame class models the GUI (frame) of the
 * Scrabble game.
 *
 * @author Pathum Danthanarayana, 101181411
 * @version 1.0
 * @date November 8th, 2022
 */
public class ScrabbleFrame extends JFrame implements ActionListener
{
    /** Fields **/
    private JButton[][] squares;
    private ArrayList<String> playerNames;
    private ArrayList<PlayerCard> playerCards;
    private Container contentPane;
    private JPanel boardPanel;
    private JPanel playerPanel;

    /** Constants **/
    // Board size
    private static final int SIZE = 15;

    // Colours
    public static final Color boardColor = new Color(48,90,119);
    public static final Color accentColor = new Color(64, 187, 161);
    public static final Color squareBackgroundColor = new Color(36, 96, 120);
    public static final Color squareBorderColor = new Color(34, 178, 194);
    public static final Color playerCardColor = new Color(28, 62, 91);

    // Spacing dimensions
    public static final Dimension menuSpacing = new Dimension(0, 80);
    public static final Dimension buttonSpacing = new Dimension(10, 0);
    public static final Dimension mainMiddleSpacing = new Dimension(40, 0);
    public static final Dimension playerCardSpacing = new Dimension(0, 30);
    public static final Dimension currentTurnSpacing = new Dimension(0, 20);

    // JPanel dimensions
    public static final Dimension boardDimensions = new Dimension(650, 650);
    public static final Dimension playerPanelDimensions = new Dimension(400, 740);
    public static final Dimension buttonPanelDimensions = new Dimension(400, 100);
    public static final Dimension playerCardDimensions = new Dimension(400, 125);

    // Fonts
    public static final Font menuHeaderFont = new Font("Arial", Font.BOLD, 38);
    public static final Font menuTaglineFont = new Font("Arial", Font.PLAIN, 22);
    public static final Font currentTurnFont = new Font("Arial", Font.PLAIN, 22);
    public static final Font playerBodyFont = new Font("Arial", Font.BOLD, 22);
    public static final Font playerNameFont = new Font("Arial", Font.BOLD, 28);
    public static final Font buttonFont = new Font("Arial", Font.PLAIN, 18);

    /** Constructor **/
    public ScrabbleFrame()
    {
        // Configure JFrame
        super("Scrabble Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 350);
        this.setResizable(false);

        // Initialize ArrayList of player names
        this.playerNames = new ArrayList<>();

        // Configure ContentPane
        contentPane = this.getContentPane();
        contentPane.setBackground(boardColor);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // Setup the menu to play the game
        this.setupMenu();
        // Make the JFrame visible
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
     * @version 1.0
     */
    private void setupMenu()
    {
        // Set up main header for the menu
        JLabel mainHeader = new JLabel("Welcome to Scrabble!");
        mainHeader.setFont(ScrabbleFrame.menuHeaderFont);
        mainHeader.setForeground(Color.WHITE);
        mainHeader.setAlignmentX(CENTER_ALIGNMENT);
        mainHeader.setHorizontalAlignment(JLabel.CENTER);

        // Set up the tagline for the menu
        JLabel tagLine = new JLabel("Developed by Group 28");
        tagLine.setFont(ScrabbleFrame.menuTaglineFont);
        tagLine.setForeground(Color.WHITE);
        tagLine.setAlignmentX(CENTER_ALIGNMENT);
        tagLine.setHorizontalAlignment(JLabel.CENTER);

        // Set up the play button for the menu
        JButton playButton = new JButton("Let's play!");
        playButton.setBackground(accentColor);
        playButton.setFocusPainted(false);
        playButton.setForeground(Color.WHITE);
        playButton.setFont(buttonFont);
        playButton.setAlignmentX(CENTER_ALIGNMENT);
        playButton.addActionListener(this);

        contentPane.add(Box.createRigidArea(menuSpacing));
        contentPane.add(mainHeader);
        contentPane.add(Box.createRigidArea(currentTurnSpacing));
        contentPane.add(tagLine);
        contentPane.add(Box.createRigidArea(playerCardSpacing));
        contentPane.add(playButton);
    }

    /**
     * The startGame method starts the Scrabble game by setting up
     * the main game window (which includes the Scrabble board,
     * player cards, etc.).
     *
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     */
    private void startGame()
    {
        // Remove all elements from the ContentPane
        this.contentPane.removeAll();
        // Repaint the JFrame
        this.repaint();
        // Change the size of the JFrame
        this.setSize(1200, 750);
        // Apply a new layout to the ContentPane
        contentPane.setLayout(new FlowLayout());

        // JPanel #1: Scrabble Board
        boardPanel = new JPanel();
        boardPanel.setPreferredSize(boardDimensions);
        boardPanel.setBackground(boardColor);
        boardPanel.setLayout(new GridLayout(SIZE, SIZE));
        squares = new JButton[SIZE][SIZE];
        // Add all the squares to the JPanel
        this.addSquares();

        // JPanel #2: Player Panel
        playerPanel = new JPanel();
        playerPanel.setPreferredSize(playerPanelDimensions);
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBackground(boardColor);

        playerPanel.add(Box.createRigidArea(currentTurnSpacing));

        // Configure current turn label (initially Player 1)
        JLabel currentTurn = new JLabel("Current turn:   " + this.playerNames.get(0));
        currentTurn.setFont(currentTurnFont);
        currentTurn.setAlignmentX(CENTER_ALIGNMENT);
        currentTurn.setHorizontalAlignment(JLabel.CENTER);
        currentTurn.setForeground(Color.WHITE);
        playerPanel.add(currentTurn);
        playerPanel.add(Box.createRigidArea(currentTurnSpacing));

        // Add the player cards to the JPanel
        this.addPlayerCards(this.playerNames);
        // Add the buttons to the JPanel
        this.addButtons(new String[]{"Skip", "Help", "Quit"});

        //ScrabbleModel model = new ScrabbleModel();
        //model.addScrabbleView(this);

        // Add all the JPanels to ContentPane
        contentPane.add(boardPanel);
        // Add spacing in between board panel and player panel
        contentPane.add(Box.createRigidArea(mainMiddleSpacing));
        contentPane.add(playerPanel);
    }

    /**
     * The addPlayerCards method adds player cards (which contains
     * the player's name, their score, and their rack) to the JPanel
     * that will hold all the player cards. The method will create and add
     * player cards according to the provided ArrayList of player names.
     *
     * @param playerNames - An ArrayList containing all the player names
     *
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     */
    private void addPlayerCards(ArrayList<String> playerNames)
    {
        // Traverse through each player name
        for (String playerName : playerNames)
        {
            // Create a player card using the player's name
            PlayerCard playerCard = new PlayerCard(playerName);
            // Keep a reference to the player card
            this.playerCards.add(playerCard);
            // Add the player card to the JPanel
            playerPanel.add(playerCard);
            // Add spacing beneath the player card
            playerPanel.add(Box.createRigidArea(playerCardSpacing));
        }
    }

    /**
     * The addButtons method adds important function buttons
     * (e.g. Skip, Help, Quit, etc.) to the player panel in
     * the Scrabble game. The method creates a button for each
     * String in the specified array of Strings.
     *
     * @param buttonNames - An array of Strings containing the names of the buttons
     *
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     */
    private void addButtons(String[] buttonNames)
    {
        // Create and configure JPanel to store the buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setMaximumSize(buttonPanelDimensions);
        buttonsPanel.setBackground(boardColor);

        // Traverse through each button name
        for (String buttonName : buttonNames)
        {
            // Create and configure a new JButton
            JButton button = new JButton(buttonName);
            button.setBackground(accentColor);
            button.setFocusPainted(false);
            button.setForeground(Color.WHITE);
            button.setFont(buttonFont);

            // Add the button to the JPanel
            buttonsPanel.add(button);
            // Add spacing to the right of the button
            buttonsPanel.add(Box.createRigidArea(buttonSpacing));
        }
        // Add the button panel to the player panel
        playerPanel.add(buttonsPanel);
    }

    /**
     * The addSquares method creates squares for a 15x15
     * Scrabble board. Each square is a JButton and is added to a
     * JPanel (board) that is configured to have a 15x15 grid layout.
     *
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     */
    private void addSquares()
    {
        // Traverse through each row and column
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                // Create and configure a new JButton
                JButton button = new JButton(" ");
                button.setBackground(squareBackgroundColor);
                button.setFont(new Font("Arial", Font.BOLD, 18));
                button.setFocusPainted(false);
                // Create and add a border to the button
                Border border = BorderFactory.createLineBorder(squareBorderColor);
                button.setBorder(border);
                button.setForeground(Color.WHITE);

                // Keep a reference to the button
                squares[i][j] = button;
                // button.addActionListener();
                // Add the button to the board panel
                boardPanel.add(button);
            }
        }
    }

    /**
     * The actionPerformed method is invoked when an action occurs.
     *
     * @param e - The event to be processed
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Check if the 'Let's play!' button was clicked
        if (e.getActionCommand().equals("Let's play!"))
        {
            // Get the number of players from the user
            String numPlayersStr = JOptionPane.showInputDialog(this,"Enter number of players:");
            // Convert String into integer
            int numPlayers = Integer.parseInt(numPlayersStr);

            // Check if the user has specified a valid range
            if (numPlayers > 4 || numPlayers < 2)
            {
                // If not, notify the user to try entering a valid number of players.
                JOptionPane.showMessageDialog(this,"Number of players must be between 2 and 4. Please try again.","Warning",JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                // Otherwise, prompt the user to enter a player name for each player
                for (int i = 0; i < numPlayers; i++)
                {
                    String message = "Enter Player " + (i + 1) + " name:";
                    String playerName = JOptionPane.showInputDialog(this, message);

                    // Check if the user has entered an empty player name
                    if (playerName.isEmpty())
                    {
                        // If so, assign a default player name to the player
                        playerName = "Player " + (i + 1);
                    }
                    // Convert the player name to uppercase and add it to the ArrayList of player names
                    this.playerNames.add(playerName.toUpperCase());
                }
                // Safety check: Only start game if the correct number of players has been recorded/processed
                if (numPlayers == this.playerNames.size())
                {
                    // Start the Scrabble game
                    this.startGame();
                }
            }
        }
    }

    /** Main method **/
    public static void main(String[] args)
    {
        ScrabbleFrame frame = new ScrabbleFrame();
    }
}
