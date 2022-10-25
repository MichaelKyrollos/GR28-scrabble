import java.util.*;

public class ScrabbleGame {
    private Parser parser;

    private Board gameBoard;

    public static final TileBag GAME_TILE_BAG = new TileBag();

    public ScrabbleGame() {
        parser = new Parser();
        gameBoard = new Board();
    }

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
        ScrabbleGame newGame = new ScrabbleGame();
        newGame.play();
    }

}