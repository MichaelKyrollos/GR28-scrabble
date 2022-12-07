import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the PlayerModel class of the Scrabble game.
 *
 * @author Michael Kyrollos, 101183521
 * @author Yehan De Silva, 101185388
 * @version 4.0
 * @date November 30, 2022
 */
class PlayerModelTest {

    private PlayerModel player1;
    private PlayerModel player2;

    private ScrabbleGameModel newGame;
    private BoardModel newBoardModel;
    private ArrayList<Tile> tiles;
    private TileBag TEST_TILE_BAG;

    @BeforeEach
    void setUp() {
        newGame = new ScrabbleGameModel();
        ScrabbleGameModel.GAME_TILE_BAG.fillBag();
        try {
            newBoardModel = new BoardModel(newGame);
        } catch (Exception e) {
            // will never occur unless the default XML is missing
        }
        tiles = new ArrayList<>();
        TEST_TILE_BAG = new TileBag();
        player1 = new PlayerModel("player1",newBoardModel);
        player2 = new PlayerModel("player2",newBoardModel);
    }

    @AfterEach
    void tearDown() {
        newGame = null;
        newBoardModel = null;
        tiles = null;
        TEST_TILE_BAG = null;
        player1 = null;
        player2 = null;
    }

    private PlayWordEvent createPlayWordEvent(String word, int level, int start_of_word, boolean vertical) {
        ArrayList<Square> squaresForWord = new ArrayList<>();
        int end_of_word = word.length() + start_of_word;
        for (int i = start_of_word; i < end_of_word; i++) {
            if (vertical) {

                squaresForWord.add(newBoardModel.getSquares()[i][level]);
                newBoardModel.getSquares()[i][level].placeSquare(tiles.get(i - start_of_word));

            } else {
                squaresForWord.add(newBoardModel.getSquares()[level][i]);
//                if second is changing, then it's horizontal
                newBoardModel.getSquares()[level][i].placeSquare(tiles.get(i - start_of_word));
            }
        }
        return new PlayWordEvent(newGame, squaresForWord, tiles);
    }

    @Test
    void testInitialPlayerScoreZero() {
        assertEquals(0,player1.getScore());
        assertEquals(0,player2.getScore());
    }

    @Test
    void testPlayerNameInserted() {
        assertEquals("player1",player1.getName());
        assertEquals("player2",player2.getName());
    }

    @Test
    void testRackSizeInitial() {
        assertEquals(7,player1.getRack().getTiles().size());
        assertEquals(7,player2.getRack().getTiles().size());

    }

    /*
     * Testing the refilling of a rack where all the tiles are replaced.
     *
     */
    @Test
    void testEntireRackRefilled() {
        TEST_TILE_BAG = new TileBag();

//      Create 2 lists, one original, one with the tiles to redraw
        ArrayList<Tile> redrawTiles = new ArrayList<>();
        ArrayList<Tile> originalTiles = new ArrayList<>();
//      Clone original list into both
        try {
            //noinspection unchecked
            redrawTiles = (ArrayList<Tile>) player1.getRack().getTiles().clone();
            //noinspection unchecked
            originalTiles = (ArrayList<Tile>) player1.getRack().getTiles().clone();
        }
        catch (ClassCastException e) {
            fail();
        }
//      ensure original rack is full and that other racks are equal
        assertTrue(originalTiles.containsAll(player1.getRack().getTiles()));
        assertTrue(redrawTiles.containsAll(player1.getRack().getTiles()));
        assertEquals(7,(player1.getRack().getTiles()).size());
        assertEquals(7,redrawTiles.size());
        player1.redraw(redrawTiles);
//      check that it is refilled and that the lists have been modified
        assertEquals(7,(player1.getRack().getTiles()).size());
        assertFalse(player1.getRack().getTiles().containsAll(originalTiles));
        assertFalse(originalTiles.containsAll(player1.getRack().getTiles()));
    }

    /*
     * Testing the refilling of a rack where only some tiles are replaced.
     *
     */
    @Test
    void testSomeOfRackRefilled() {
//      Create 2 lists, one original, one with the tiles to redraw
        ArrayList<Tile> redrawTiles = new ArrayList<>();
        ArrayList<Tile> originalTiles = new ArrayList<>();
//      Clone original list into both
        try {
            //noinspection unchecked
            originalTiles = (ArrayList<Tile>) player1.getRack().getTiles().clone();
        }
        catch (ClassCastException e) {
            fail();
        }
        redrawTiles.add(player1.getRack().getTiles().get(0));
        redrawTiles.add(player1.getRack().getTiles().get(3));
//      ensure original rack is full
        assertEquals(7,(player1.getRack().getTiles()).size());
        player1.redraw(redrawTiles);
//      check that it is refilled and that the lists have been modified
        assertEquals(7,(player1.getRack().getTiles()).size());
        assertFalse(player1.getRack().getTiles().containsAll(originalTiles));
        assertFalse(originalTiles.containsAll(player1.getRack().getTiles()));
    }

    /*
     * Testing when a word is placed in center horizontal from playWord
     */
    @Test
    void testPlayWordHorizontalInitialPlacement() {

        tiles.add(TEST_TILE_BAG.takeTile('A'));
        tiles.add(TEST_TILE_BAG.takeTile('B'));
        tiles.add(TEST_TILE_BAG.takeTile('C'));
        assertTrue(player1.playWord(createPlayWordEvent("ABC", 7, 7, false)));
    }

    /*
     * Testing when a word is placed in center vertical from playWord
     */
    @Test
    void testPlayWordVerticalInitialPlacement() {
        tiles.add(TEST_TILE_BAG.takeTile('A'));
        tiles.add(TEST_TILE_BAG.takeTile('B'));
        tiles.add(TEST_TILE_BAG.takeTile('C'));
        assertTrue(player1.playWord(createPlayWordEvent("ABC", 7, 7, true)));
    }

    /*
     * Testing an invalid play
     */
    @Test
    void testPlayWordInvalidPlacement() {
        tiles.add(TEST_TILE_BAG.takeTile('A'));
        tiles.add(TEST_TILE_BAG.takeTile('B'));
        tiles.add(TEST_TILE_BAG.takeTile('C'));
        assertFalse(player1.playWord(createPlayWordEvent("ABC", 8, 10, true)));
    }

    /*
     * Testing the return of a single's player's score and 1 play
     */
    @Test
    void test1GetPlayerScore() {
        assertEquals(0,player1.getScore());
        tiles.add(TEST_TILE_BAG.takeTile('A'));
        tiles.add(TEST_TILE_BAG.takeTile('B'));
        tiles.add(TEST_TILE_BAG.takeTile('C'));
        assertTrue(player1.playWord(createPlayWordEvent("ABC", 7, 7, true)));
        assertEquals(14,player1.getScore());
    }

    /*
     * Testing the return of several player's score and several plays
     */
    @Test
    void testSeveralPlayersGettingScore() {
        assertEquals(0,player1.getScore());
        assertEquals(0,player2.getScore());

        tiles.add(TEST_TILE_BAG.takeTile('A'));
        tiles.add(TEST_TILE_BAG.takeTile('P'));
        tiles.add(TEST_TILE_BAG.takeTile('P'));

       assertTrue(player1.playWord(createPlayWordEvent("APP", 7, 7, false)));
       assertEquals(14,player1.getScore());

        tiles.clear();

        tiles.add(TEST_TILE_BAG.takeTile('P'));
        tiles.add(TEST_TILE_BAG.takeTile('R'));
        tiles.add(TEST_TILE_BAG.takeTile('I'));
        tiles.add(TEST_TILE_BAG.takeTile('N'));
        tiles.add(TEST_TILE_BAG.takeTile('T'));

        assertTrue(player2.playWord(createPlayWordEvent("PRINT", 8, 7, true)));
        assertEquals(15,player2.getScore());

        tiles.clear();
        //noinspection ConstantConditions
        assertEquals(0, tiles.size());

        tiles.add(TEST_TILE_BAG.takeTile('O'));
        tiles.add(TEST_TILE_BAG.takeTile('U'));
        tiles.add(TEST_TILE_BAG.takeTile('T'));

        assertTrue(player1.playWord(createPlayWordEvent("OUT", 7, 10, true)));
        assertEquals(22,player1.getScore());
    }
}
