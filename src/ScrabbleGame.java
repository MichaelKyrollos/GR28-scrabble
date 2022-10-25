import java.util.*;

/**
 * Milestone 1 of the SYSC 3110 Project.
 * A text-based playable version of the game "Scrabble", where players play the game through the console using the keyboard.
 * This scrabble game is compatible for 2-4 players.
 *
 * @author Michael Kyrollos, 101183521
 * @author Yehan De Silva
 * @version 1.1
 * @date October 25, 2022
 */
public class ScrabbleGame {
    /**
     * Parser used to parse information from player.
     */
    private Parser parser;

    /**
     * Board the game is being played on.
     */
    private Board gameBoard;

    /**
     * Players of the scrabble game.
     */
    private List<Player> players;

    /**
     * The tile bag used to store all the tiles for this Scrabble game.
     */
    public static final TileBag GAME_TILE_BAG = new TileBag();

    /**
     * The dictionary used to validate words for this Scrabble game.
     */
    public static final ScrabbleDictionary SCRABBLE_DICTIONARY= new ScrabbleDictionary();

    /**
     * Creates a new scrabble game.
     * @author Michael Kyrollos, 101183521
     * @author Yehan De Silva
     * @version 1.1
     * @date October 25, 2022
     */
    public ScrabbleGame() {
        parser = new Parser();
        gameBoard = new Board();
        players = new ArrayList<>();
        initializePlayers();
    }

    /**
     * Initializes the list of players playing this scrabble game at the start of the game.
     */
    private void initializePlayers() {
        String playerName;
        int numberPlayers = 0;
        Scanner in = new Scanner(System.in);
        boolean validNumberPlayers = false;

        //Keep looping till valid number of players (Between 2-4) is chosen by user
        while (!validNumberPlayers) {
            try {
                System.out.println("Please enter the number of players (Between 2-4): ");
                numberPlayers = in.nextInt();
            }
            catch (Exception e) {
                in.next();
                continue;
            }
            //Stop looping once a valid integer is given
            if (numberPlayers <= 4 && numberPlayers >= 2) {validNumberPlayers = true;}
        }

        //Loop to get name of each player and add them to the game
        for (int i = 1; i <= numberPlayers; i++) {
            System.out.println("Please enter the name of Player " + i + ":");
            playerName = in.next();
            players.add(new Player(playerName, gameBoard));
        }
    }

    /**
     * Starts the Scrabble game.
     * @author Michael Kyrollos, 101183521
     * @author Yehan De Silva
     * @version 1.1
     * @date October 25, 2022
     */
    public void play()
    {

        boolean finished = false;
        System.out.println("Welcome to Scrabble!");

        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you. Good bye.");
    }


    /**
     * Process the given command
     *
     * @author Michael Kyrollos, 101183521
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     *
     */
    private boolean processCommand(Command command)
    {
        boolean quitting = false;

        CommandWord commandWord = command.getFirstWord();

        switch (commandWord) {
            case INVALID:
                System.out.println("Enter a valid command");
                break;

            case HELP:
                this.help();
                break;

            case START:
                this.startGame();
                break;

            case PLAY:
                this.playWord(command);

                break;

            case QUIT:
                quitting = true;
                break;
        }
        return quitting;
    }

    public void playWord(Command command) {
//        Do not remove, for testing purposes.
//        ArrayList<Tile> tilesToPlay = new ArrayList<>();
//        tilesToPlay.add(GAME_TILE_BAG.dealTile());
//        tilesToPlay.add(GAME_TILE_BAG.dealTile());
//        tilesToPlay.add(GAME_TILE_BAG.dealTile());
//        tilesToPlay.add(GAME_TILE_BAG.dealTile());
//        tilesToPlay.add(GAME_TILE_BAG.dealTile());
//        gameBoard.placeWord(command,tilesToPlay);

        //check in player first - check for tiles: playWord
        // playWord();
        //
    }

    public Boolean validateWord(Command command, ArrayList<Tile> tilesToPlay) {
//        call dictionary validate & board validate
        return true;
    }

    /**
     * Print out help information. list of commands and intro
     *
     * @author - Michael Kyrollos, 101183521
     */
    public void help()
    {
        System.out.println("You need help");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    public void startGame(){
        Board board = new Board();
        System.out.println(board);
    }

    public static void main(String[] args) {
        /*
        ScrabbleGame newGame = new ScrabbleGame();
        newGame.play();
         */
    }

}