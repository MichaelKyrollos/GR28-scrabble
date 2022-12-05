import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import static java.lang.Character.isAlphabetic;
import static java.lang.Character.toUpperCase;

/**
 * The ScrabbleController implements the Controller in the MVC design pattern. It listens to user-input performed on
 * the view, and communicates it to the model.
 *
 * @author Yehan De Silva
 * @author Amin Zeina, 101186297
 * @author Pathum Danthanarayana, 101181411
 * @version 3.1
 * @date November 14, 2022
 */
public class ScrabbleController implements ActionListener {

    /** Corresponding model **/
    private ScrabbleGameModel scrabbleModel;
    /** ScrabbleFrame to modify visual elements only**/
    private ScrabbleFrameView scrabbleFrame;

    private Tile selectedTile;
    private boolean isPlaying;
    private boolean isRedrawing;

    private AI aiPlayer;
    private ArrayList<Tile> tilesPlaced;
    private ArrayList<Tile> tilesToRedraw;
    private ArrayList<Square> squaresInWord;

    /**
     * Constructs a ScrabbleController object.
     * @param model Corresponding model.
     * @param frame Corresponding frame.
     */
    public ScrabbleController(ScrabbleGameModel model, ScrabbleFrameView frame) {
        this.scrabbleModel = model;
        this.scrabbleFrame = frame;
        this.selectedTile = null;
        this.isPlaying = false;
        this.isRedrawing = false;
        tilesPlaced = new ArrayList<>();
        tilesToRedraw = new ArrayList<>();
        squaresInWord = new ArrayList<>();
        addControllerToGameTiles();
    }

    /**
     * Method is invoked whenever a user performs input that the Controller is listening to.
     * @param e the event to be processed.
     *
     * @author Yehan De Silva
     * @author Amin Zeina 101186297
     * @author Pathum Danthanarayana, 101181411
     * @version 3.1
     * @date November 17, 2022
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //If tile is selected
        if (e.getSource() instanceof Tile) {
            Tile tile = (Tile) e.getSource();
            //Tile is to be played
            if (isPlaying) {
                playTileSelected(tile);
            }
            //Tile is to be redrawn
            else if (isRedrawing) {
                redrawTileSelected(tile);
            }
        }
        //Square is to be played on
        if (e.getSource() instanceof Square) {
            Square square = (Square) e.getSource();
            if (isPlaying) {
                playSquareSelected(square);
            }
        }

        //Game start button clicked
        if (e.getActionCommand().equals("Let's play!")) {
            this.playScrabbleGame();
        }
        //Play button clicked
        else if (e.getActionCommand().equals("Play")) {
            this.playSelected();
        }
        //Redraw button clicked
        else if (e.getActionCommand().equals("Redraw")) {
            this.redrawSelected();
        }
        //Skip button clicked
        else if (e.getActionCommand().equals("Skip")) {
            scrabbleModel.endTurn();
        }
        //Submit button clicked
        else if (e.getActionCommand().equals("Submit")) {
            //Submit clicked while playing
            if (isPlaying) {
                submitPlaySelected();
            }
            //Submit clicked while redrawing
            else if (isRedrawing) {
                submitRedrawSelected();
            }
        }
        // Load game selected
        else if (e.getActionCommand().equals("Load Game..."))
        {
            this.loadScrabbleGame();
        }
        // Save game selected
        else if (e.getActionCommand().equals("Save Game..."))
        {
            this.saveScrabbleGame();
        }
        //Quit game selected
        else if (e.getActionCommand().equals("Quit Game")) {
            scrabbleModel.endGame();
        }
        //Undo selected
        else if (e.getActionCommand().equals("Undo")) {
            undoSelected();
        }
        //Redo selected
        else if (e.getActionCommand().equals("Redo")) {
            redoSelected();
        }
    }

    /**
     * Resets the controller and redoes the last move undid.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 03, 2022
     */
    private void redoSelected() {
        resetController();
        scrabbleModel.redoTurn();
    }

    /**
     * Resets the controller and undoes the last move made by the players.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 02, 2022
     */
    private void undoSelected() {
        resetController();
        scrabbleModel.undoTurn();
    }

    /**
     * Resets the controller.
     *
     * @author Yehan De Silva
     * @version 4.0
     * @date December 03, 2022
     */
    private void resetController() {
        if (this.selectedTile != null) {
            selectedTile.setBackground(Color.WHITE);
        }
        this.resetRedrawnTilesColour();
        squaresInWord.clear();
        tilesPlaced.clear();
        tilesToRedraw.clear();
        this.isPlaying = false;
        this.isRedrawing = false;
        this.scrabbleFrame.getRedrawButton().setEnabled(true);
        this.scrabbleFrame.getRedrawButton().setText("Redraw");
        this.scrabbleFrame.getRedrawButton().setBackground(ScrabbleFrameView.ACCENT_COLOR);
        this.scrabbleFrame.getPlayButton().setEnabled(true);
        this.scrabbleFrame.getPlayButton().setText("Play");
        this.scrabbleFrame.getPlayButton().setBackground(ScrabbleFrameView.ACCENT_COLOR);
        this.scrabbleFrame.getSkipButton().setEnabled(true);
    }

    /**
     * Plays the currently selected tile if there is one in the given square.
     * @param square Square the selected tile is to be placed on
     *
     * @author Yehan De Silva
     * @version 3.0
     * @date November 17, 2022
     */
    private void playSquareSelected(Square square) {
        if (square.getTile() == null && selectedTile != null) {
            // square is empty, so place the tile
            if (selectedTile instanceof BlankTile) {
                // blank tile has been selected, so set the letter to what the user wants
                BlankTile blankTile = (BlankTile) selectedTile;
                boolean isCharLetter = false;
                while (!isCharLetter) {
                    String tileLetter = JOptionPane.showInputDialog(scrabbleFrame, "Enter the letter " +
                            "you'd like to use for this blank tile: ");
                    if (tileLetter.length() == 1 && isAlphabetic(tileLetter.charAt(0))) {
                        blankTile.setLetter(toUpperCase(tileLetter.charAt(0)));
                        isCharLetter = true;
                    } else {
                        JOptionPane.showMessageDialog(scrabbleFrame, "That letter is invalid, try again");
                    }
                }
            }
            selectedTile.setBackground(Color.WHITE);
            scrabbleModel.getCurrentPlayer().playTile(square, selectedTile);
            squaresInWord.add(square);
            tilesPlaced.add(selectedTile);
            selectedTile = null;
            square.setEnabled(false);
        } else if (square.getTile() != null && selectedTile != null) {
            // Change colour of tile to show it is unselected
            selectedTile.setBackground(Color.WHITE);
            // square not empty -> unselect tile and prompt user
            selectedTile = null;
            JOptionPane.showMessageDialog(null, "That square is full. Tile unselected.");
        } else if (square.getTile() != null && selectedTile == null){
            // square not empty but no tile selected -> add existing clicked square to word
            squaresInWord.add(square);
            square.setEnabled(false);
        }
        // otherwise, square empty and no tile selected, do nothing
    }

    /**
     * Adds the given tile to the list of tiles to be redrawn or removes it if it already exists.
     * @param tile Tile to be added/removed from the redrawn tiles list.
     *
     * @author Yehan De Silva
     * @version 3.0
     * @date November 17, 2022
     */
    private void redrawTileSelected(Tile tile) {
        //Add tile to list of tiles to redraw
        if(!(tilesToRedraw.contains(tile))) {
            // Change background colour of the selected tile to show it has been selected
            tile.setBackground(ScrabbleFrameView.SELECTED_TILE_COLOR);
            tilesToRedraw.add(tile);
        }
        //Remove tile if it is already in the list of tiles to redraw
        else {
            tilesToRedraw.remove(tile);
            tile.setBackground(Color.WHITE);
        }
    }

    /**
     * Sets the given tile as the selected tile.
     * @param tile Tile to be set as the selected tile.
     *
     * @author Yehan De Silva
     * @version 3.0
     * @date November 17, 2022
     */
    private void playTileSelected(Tile tile) {
        // Check if a tile is already selected
        if (selectedTile != null)
        {
            // If so, unselect the currently selected tile
            selectedTile.setBackground(Color.WHITE);
        }
        selectedTile = tile;

        // Change background colour of the selected tile to show it has been selected
        selectedTile.setBackground(ScrabbleFrameView.SELECTED_TILE_COLOR);
    }

    /**
     * Redraws the list of the tiles to be redrawn.
     *
     * @author Yehan De Silva
     * @version 3.0
     * @date November 17, 2022
     */
    private void submitRedrawSelected() {
        //Redrawing is submitted, so change button views
        isRedrawing = false;
        scrabbleFrame.getPlayButton().setEnabled(true);
        scrabbleFrame.getSkipButton().setEnabled(true);
        scrabbleFrame.getRedrawButton().setText("Redraw");
        scrabbleFrame.getRedrawButton().setBackground(ScrabbleFrameView.ACCENT_COLOR);
        //Only perform redraw method in model if at least one tile is selected to redraw
        if (!tilesToRedraw.isEmpty()) {
            this.resetRedrawnTilesColour();
            scrabbleModel.redraw(tilesToRedraw);
            tilesToRedraw.clear();
        }
    }

    /**
     * Attempts to play the user created word on the board.
     *
     * @author Yehan De Silva
     * @version 3.0
     * @date November 17, 2022
     */
    private void submitPlaySelected() {
        //Playing is submitted, so change button views
        isPlaying = false;
        scrabbleFrame.getRedrawButton().setEnabled(true);
        scrabbleFrame.getSkipButton().setEnabled(true);
        scrabbleFrame.getPlayButton().setText("Play");
        scrabbleFrame.getPlayButton().setBackground(ScrabbleFrameView.ACCENT_COLOR);
        if (scrabbleModel.getCurrentPlayer()instanceof AIPlayer) {
            PlayWordEvent wordEvent = ((AIPlayer) scrabbleModel.getCurrentPlayer()).makeMove();
            squaresInWord = wordEvent.getSquaresInWord();
            tilesPlaced = wordEvent.getTilesPlaced();
            for(int i=tilesPlaced.size()-1;i>0;i--){
                scrabbleModel.getCurrentPlayer().playTile(squaresInWord.get(i), tilesPlaced.get(i));
            }
        }
        scrabbleModel.playWord(new PlayWordEvent(scrabbleModel, squaresInWord, tilesPlaced));
        for (Square square : squaresInWord) {
            square.setEnabled(true);
        }
        squaresInWord.clear();
        tilesPlaced.clear();
        tilesToRedraw.clear();
    }

    /**
     * Updates the controller state and game views to the play status.
     *
     * @author Yehan De Silva
     * @version 3.0
     * @date November 17, 2022
     */
    private void playSelected() {
        //Update state and views if play is selected
        isPlaying = true;
        scrabbleFrame.getRedrawButton().setEnabled(false);
        scrabbleFrame.getSkipButton().setEnabled(false);
        scrabbleModel.getGameBoard().copyBoardSquares();
        scrabbleFrame.getPlayButton().setText("Submit");
        scrabbleFrame.getPlayButton().setBackground(ScrabbleFrameView.SELECTED_BUTTON_COLOR);
    }

    /**
     * Updates the controller state and game views to the redraw status.
     *
     * @author Yehan De Silva
     * @version 3.0
     * @date November 17, 2022
     */
    private void redrawSelected() {
        //Update state and views if redraw is selected
        this.isRedrawing = true;
        scrabbleFrame.getPlayButton().setEnabled(false);
        scrabbleFrame.getSkipButton().setEnabled(false);
        scrabbleFrame.getRedrawButton().setText("Submit");
        scrabbleFrame.getRedrawButton().setBackground(ScrabbleFrameView.SELECTED_BUTTON_COLOR);
    }

    /**
     * The playScrabbleGame method attempts to configure all the player
     * and AI player information, and starts the Scrabble game.
     *
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     * @date November 19, 2022
     */
    private void playScrabbleGame()
    {
        // Configure player information for regular (human) players
        int numPlayers = configurePlayerInformation();

        // Check for errors in configuring regular player information
        if (numPlayers != -1)
        {
            // Configure AI player information
            int numAIPlayers = configureAIPlayerInformation(numPlayers);
            // Check for any errors in configuring AI player information
            if (numAIPlayers != -1)
            {
                // If no errors, start the game
                scrabbleFrame.startGame();
                scrabbleModel.pushStatusToUndoStack();
                scrabbleFrame.update();
            }
        }
    }

    /**
     * Configures starting player information once a Scrabble game is started.
     * @return the number of players configured, or return -1 if error in configuring players
     *
     * @author Yehan De Silva
     * @author Pathum Danthanarayana, 101181411
     * @version 1.1
     * @date November 11, 2022
     */
    private int configurePlayerInformation() {
        int numberPlayers = 0;
        boolean validNumberPlayers = false;

        //Keep looping till valid number of players (Between 2-4) is chosen by user
        while (!validNumberPlayers) {
            String numPlayersStr = JOptionPane.showInputDialog(scrabbleFrame, "Please enter the number of players (Between 0 and 4):");
            // Convert String into integer
            try {
                //Return if user cancels the input prompt
                if (numPlayersStr == null) {
                    return -1;
                }
                numberPlayers = Integer.parseInt(numPlayersStr);
            } catch (NumberFormatException e) {
                continue;
            }
            //Stop looping once a valid integer is given
            if (numberPlayers <= ScrabbleGameModel.MAX_PLAYERS && numberPlayers >= 0) {
                validNumberPlayers = true;
            }
        }

        // Prompt the user to enter a player name for each player
        for (int i = 0; i < numberPlayers; i++)
        {
            String message = "Enter Player " + (i + 1) + " name:";
            String playerName = JOptionPane.showInputDialog(scrabbleFrame, message);

            // Check if the user has entered an empty player name
            if (playerName == null)
            {
                // If so, assign a default player name to the player
                playerName = "Player " + (i + 1);
            }
            scrabbleModel.addPlayer(playerName.toUpperCase());
        }
        return numberPlayers;
    }

    /**
     * The configureAIPlayerInformation method configures the AI player
     * information.
     * @return the number of AI players configured, and return -1 if error in configuring AI players
     *
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     * @date November 19th, 2022
     */
    private int configureAIPlayerInformation(int numRegularPlayers)
    {
        // Check if the number of regular players is less than 4
        if (numRegularPlayers < 4)
        {
            boolean validNumAIPlayers = false;
            int numAIPlayers = 0;

            // Calculate maximum number of accepted AI players
            int maxAIPlayers = ScrabbleGameModel.MAX_PLAYERS - numRegularPlayers;
            // Calculate minimum number of accepted AI players
            int minAIPlayers = 0;
            if (numRegularPlayers <= ScrabbleGameModel.MIN_PLAYERS)
            {
                minAIPlayers = ScrabbleGameModel.MIN_PLAYERS - numRegularPlayers;
            }

            // Perform loop until a valid number of AI players has been specified
            while (!validNumAIPlayers) {
                String numAIPlayersStr = JOptionPane.showInputDialog(scrabbleFrame, String.format("Please enter the number of AI players (Between %d and %d):", minAIPlayers, maxAIPlayers));
                // Convert String into integer
                try {
                    //Return if user cancels the input prompt
                    if (numAIPlayersStr == null) {
                        return -1;
                    }
                    numAIPlayers = Integer.parseInt(numAIPlayersStr);
                } catch (NumberFormatException e) {
                    continue;
                }

                // Stop looping until a valid number of AI players is specified
                if (numAIPlayers <= maxAIPlayers && numAIPlayers >= minAIPlayers) {
                    validNumAIPlayers = true;
                }
            }
            // Add the AI players to the game
            for (int i = 0; i < numAIPlayers; i++) {
                scrabbleModel.addAI("BOT " + (i + 1));
            }
            return numAIPlayers;
        }
        // Return default value of 0 if no AI players were configured
        return 0;
    }

    /**
     * The resetRedrawnTilesColour method resets the background colour of the tiles to
     * be redrawn to white, so that when the same tiles are drawn from the tile bag
     * by a different player, they will not have the selected background color.
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     * @date November 13, 2022
     */
    private void resetRedrawnTilesColour()
    {
        // Traverse through each tile in the tiles to redraw
        for (Tile tile : tilesToRedraw)
        {
            // Set the tile's background colour back to white
            tile.setBackground(Color.WHITE);
        }
    }

    /**
     * Adds this ScrabbleController as a listener to all game tiles.
     *
     * @author Yehan De Silva
     * @version 1.0
     * @date November 13, 2022
     */
    private void addControllerToGameTiles() {
        for (Tile tile : ScrabbleGameModel.GAME_TILE_BAG.getTiles()) {
            tile.addActionListener(this);
        }
    }

    /**
     * The loadScrabbleGame method loads a Scrabble game from a save
     * file at the specified path.
     *
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     * @date December 5th, 2022
     */
    private void loadScrabbleGame()
    {
        // Prompt the user to select a save file to load the Scrabble game
        JFileChooser fileChooser = new JFileChooser();
        // Set file extension filters for the file chooser (only .txt and .ser)
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt and .ser files", "txt", "ser");
        fileChooser.setFileFilter(filter);

        // Store the result of choosing the save file
        int fileSelectionResult = fileChooser.showOpenDialog(null);

        // Check if the user selected a save file to load (and didn't click cancel or close out of the file chooser)
        if (fileSelectionResult == JFileChooser.APPROVE_OPTION)
        {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println("Load file path: " + filePath);
            // Check if the user is clicking Load Game before the starting a Scrabble game or after
            if (!scrabbleFrame.isScrabbleGameStarted())
            {
                // If not, start the Scrabble game first
                scrabbleFrame.startGame();
            }
            if (this.scrabbleModel.loadScrabbleGame(filePath))
            {
                // Traverse through each of the Player models
                for (PlayerModel playerModel : scrabbleModel.getPlayers())
                {
                    // Traverse through each of the tiles in the player's rack
                    for (Tile tile : playerModel.getRack().getTiles())
                    {
                        // Add the tile's action listener to this controller
                        tile.addActionListener(this);
                    }
                }
                // Add this controller as the action listener for all tiles in the tile game bag
                this.addControllerToGameTiles();
                // Notify the user that the Scrabble game was successfully loaded in
                JOptionPane.showMessageDialog(scrabbleFrame, "Scrabble game successfully loaded.");
            }
        }
    }

    /**
     * The saveScrabbleGame method saves the Scrabble game to a save file
     * at the specified file path.
     *
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     * @date December 5th, 2022
     */
    private void saveScrabbleGame()
    {
        // Prompt the user to select a save file to load the Scrabble game
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // Set file extension filters for the file chooser (only .txt and .ser)
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt and .ser files", "txt", "ser");
        fileChooser.setFileFilter(filter);
        // Store the result of choosing a directory and entering file name
        int directorySelectionResult = fileChooser.showSaveDialog(null);

        // Check if the user selected a directory and entered a valid file name
        if (directorySelectionResult == JFileChooser.APPROVE_OPTION)
        {
            String exportFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            // Check if the file name ends with '.txt' or '.ser'
            if (exportFilePath.endsWith(".txt") || exportFilePath.endsWith(".ser"))
            {
                // If so, proceed with saving the game to the specified path
                System.out.println("Export file path: " + exportFilePath);
                if (this.scrabbleModel.saveScrabbleGame(exportFilePath))
                {
                    JOptionPane.showMessageDialog(scrabbleFrame, "Scrabble game successfully saved.");
                }
            }
            else
            {
                // If not, notify the user to add a valid file extension to the file path
                String message = "Please specify a file name at the end of the Folder Path with a valid file extension (.txt or .ser), and try again.\n" + "Example folder path: C:\\Users\\SomeUser\\Desktop\\scrabbleGame.ser";
                JOptionPane.showMessageDialog(scrabbleFrame, message);
            }
        }
    }

    /**
     * Gets a custom board from the user in the form of an XML file, and returns that file, or null if no file is
     * selected
     *
     * @return The XML file containing a custom board layout, or null the default board should be used
     *
     * @author Amin Zeina, 101186297
     * @version 4.0
     * @date December 1, 2022
     */
    public File getCustomBoard() {

        int confirm = JOptionPane.showConfirmDialog(null,
                "Do you want to load a custom XML board for this game?");
        if (confirm == JOptionPane.YES_OPTION) {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showDialog(null, "Load Custom Board XML");
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                return fc.getSelectedFile();
            }
        }
        return null;
    }
}
