import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the ScrabbleGameModel.
 *
 * @author Michael Kyrollos, 101183521
 * @version 1.0
 * @date November 11, 2022
 */
class ScrabbleGameModelTest {

    private ScrabbleGameModel newScrabbleGameModel;
    private ArrayList<Tile> tiles;
    private TileBag TEST_TILE_BAG;

    @BeforeEach
    void setUp() {
        newScrabbleGameModel = new ScrabbleGameModel();
        newScrabbleGameModel.createGameBoard(null);
        ScrabbleGameModel.GAME_TILE_BAG.fillBag();
        TEST_TILE_BAG = new TileBag();
        tiles = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        newScrabbleGameModel = null;
        TEST_TILE_BAG = null;
        tiles = null;
    }

    private PlayWordEvent createWordEvent(String word, int level, int start_of_word, boolean vertical) {
        ArrayList<Square> squaresForWord = new ArrayList<>();
        int end_of_word = word.length() + start_of_word;
        for (int i = start_of_word; i < end_of_word; i++) {
            if (vertical) {
                squaresForWord.add(newScrabbleGameModel.getGameBoard().getSquares()[i][level]);
                newScrabbleGameModel.getGameBoard().getSquares()[i][level].placeSquare(tiles.get(i - start_of_word));

            } else {
                squaresForWord.add(newScrabbleGameModel.getGameBoard().getSquares()[level][i]);
//                if second is changing, then it's horizontal
                newScrabbleGameModel.getGameBoard().getSquares()[level][i].placeSquare(tiles.get(i - start_of_word));
            }
        }
        return new PlayWordEvent(newScrabbleGameModel, squaresForWord, tiles);
    }


    @Test
    void testAddValidNumberOfPlayers() {
        assertTrue(newScrabbleGameModel.addPlayer("player1"));
        assertTrue(newScrabbleGameModel.addPlayer("player2"));
        assertTrue(newScrabbleGameModel.addPlayer("player3"));
        assertTrue(newScrabbleGameModel.addPlayer(""));
        assertNotNull(newScrabbleGameModel.getPlayers().get(0));
        assertNotNull(newScrabbleGameModel.getPlayers().get(1));
        assertNotNull(newScrabbleGameModel.getPlayers().get(2));
        assertNotNull(newScrabbleGameModel.getPlayers().get(3));
        assertEquals("player1", newScrabbleGameModel.getPlayers().get(0).getName());
        assertEquals("player2", newScrabbleGameModel.getPlayers().get(1).getName());
        assertEquals("player3", newScrabbleGameModel.getPlayers().get(2).getName());
        assertEquals("", newScrabbleGameModel.getPlayers().get(3).getName());
    }

    /*
     * Tests the playWord() within the ScrabbleGameModel.
     * Needs to check points given to player, the validation of the dictionary, the change of turns
     * and the change of players after each turn
     * This is also testing getCurrentTurn(), getCurrentPlayer()
     */
    @Test
    void testValidPlayWordWith2Players() {
        assertTrue(newScrabbleGameModel.addPlayer("player1"));
        assertTrue(newScrabbleGameModel.addPlayer("player2"));
//        Play 1 by player 1
        tiles.add(TEST_TILE_BAG.takeTile('D'));
        tiles.add(TEST_TILE_BAG.takeTile('R'));
        tiles.add(TEST_TILE_BAG.takeTile('I'));
        tiles.add(TEST_TILE_BAG.takeTile('V'));
        tiles.add(TEST_TILE_BAG.takeTile('E'));

        assertEquals(newScrabbleGameModel.getPlayers().get(0),newScrabbleGameModel.getCurrentPlayer());
        assertTrue(newScrabbleGameModel.playWord(createWordEvent("DRIVE", 7, 7, false)));
        assertEquals(20,newScrabbleGameModel.getPlayers().get(0).getScore());
        assertEquals(newScrabbleGameModel.getPlayers().get(1),newScrabbleGameModel.getCurrentPlayer());
        tiles.clear();

//        Play 1 by player 2
        tiles.add(TEST_TILE_BAG.takeTile('T'));
        tiles.add(TEST_TILE_BAG.takeTile('E'));
        tiles.add(TEST_TILE_BAG.takeTile('A'));

        assertEquals(newScrabbleGameModel.getPlayers().get(1),newScrabbleGameModel.getCurrentPlayer());
        assertTrue(newScrabbleGameModel.playWord(createWordEvent("TEA", 11, 6, true)));
        assertEquals(12,newScrabbleGameModel.getPlayers().get(1).getScore());
        assertEquals(newScrabbleGameModel.getPlayers().get(0),newScrabbleGameModel.getCurrentPlayer());
        tiles.clear();

//        Play 2 by player 1
        tiles.add(TEST_TILE_BAG.takeTile('A'));
        tiles.add(TEST_TILE_BAG.takeTile('P'));
        tiles.add(TEST_TILE_BAG.takeTile('P'));

        assertEquals(newScrabbleGameModel.getPlayers().get(0),newScrabbleGameModel.getCurrentPlayer());
        assertTrue(newScrabbleGameModel.playWord(createWordEvent("APP", 8, 11, false)));
        assertEquals(12,newScrabbleGameModel.getPlayers().get(1).getScore());
        assertEquals(newScrabbleGameModel.getPlayers().get(1),newScrabbleGameModel.getCurrentPlayer());
    }

    @Test
    void testEndGameScore() {
        assertTrue(newScrabbleGameModel.addPlayer("player1"));
        assertTrue(newScrabbleGameModel.addPlayer("player2"));
//        Play 1 by player 1
        tiles.add(TEST_TILE_BAG.takeTile('D'));
        tiles.add(TEST_TILE_BAG.takeTile('R'));
        tiles.add(TEST_TILE_BAG.takeTile('I'));
        tiles.add(TEST_TILE_BAG.takeTile('V'));
        tiles.add(TEST_TILE_BAG.takeTile('E'));

        assertEquals(newScrabbleGameModel.getPlayers().get(0),newScrabbleGameModel.getCurrentPlayer());
        assertTrue(newScrabbleGameModel.playWord(createWordEvent("DRIVE", 7, 7, false)));
        assertEquals(20,newScrabbleGameModel.getPlayers().get(0).getScore());
        assertEquals(newScrabbleGameModel.getPlayers().get(1),newScrabbleGameModel.getCurrentPlayer());
        tiles.clear();

//        Play 1 by player 2
        tiles.add(TEST_TILE_BAG.takeTile('T'));
        tiles.add(TEST_TILE_BAG.takeTile('E'));
        tiles.add(TEST_TILE_BAG.takeTile('A'));

        assertEquals(newScrabbleGameModel.getPlayers().get(1),newScrabbleGameModel.getCurrentPlayer());
        assertTrue(newScrabbleGameModel.playWord(createWordEvent("TEA", 11, 6, true)));
        assertEquals(12,newScrabbleGameModel.getPlayers().get(1).getScore());
        assertEquals(newScrabbleGameModel.getPlayers().get(0),newScrabbleGameModel.getCurrentPlayer());
        tiles.clear();

//        Play 2 by player 1
        tiles.add(TEST_TILE_BAG.takeTile('A'));
        tiles.add(TEST_TILE_BAG.takeTile('P'));
        tiles.add(TEST_TILE_BAG.takeTile('P'));

        assertEquals(newScrabbleGameModel.getPlayers().get(0),newScrabbleGameModel.getCurrentPlayer());
        assertTrue(newScrabbleGameModel.playWord(createWordEvent("APP", 8, 11, false)));
        assertEquals(12,newScrabbleGameModel.getPlayers().get(1).getScore());
        assertEquals(newScrabbleGameModel.getPlayers().get(1),newScrabbleGameModel.getCurrentPlayer());
        assertEquals("player1", newScrabbleGameModel.determineWinners().get(0).getName());

    }

    /*
     * Tests the playWord() within the ScrabbleGameModel.
     * Using a word that does not exist in the dictionary.
     * This is also testing getCurrentTurn(), getCurrentPlayer()
     */
    @Test
    void testInValidDictPlayWord() {
        assertTrue(newScrabbleGameModel.addPlayer("player1"));
        assertTrue(newScrabbleGameModel.addPlayer("player2"));
//        Play 1 by player 1
        tiles.add(TEST_TILE_BAG.takeTile('D'));
        tiles.add(TEST_TILE_BAG.takeTile('R'));
        tiles.add(TEST_TILE_BAG.takeTile('I'));

        assertEquals(newScrabbleGameModel.getPlayers().get(0),newScrabbleGameModel.getCurrentPlayer());
        assertFalse(newScrabbleGameModel.playWord(createWordEvent("DRI", 7, 7, false)));
        assertEquals(0,newScrabbleGameModel.getPlayers().get(0).getScore());
    }

    /*
     * Tests the playWord() within the ScrabbleGameModel.
     * Using a word that does not exist in the dictionary.
     * This is also testing getCurrentTurn(), getCurrentPlayer()
     */
    @Test
    void testInvalidPlacementWordAndEndTurn() {
        assertTrue(newScrabbleGameModel.addPlayer("player1"));
        assertTrue(newScrabbleGameModel.addPlayer("player2"));
//        Play 1 by player 1
        tiles.add(TEST_TILE_BAG.takeTile('Q'));
        tiles.add(TEST_TILE_BAG.takeTile('U'));
        tiles.add(TEST_TILE_BAG.takeTile('E'));
        tiles.add(TEST_TILE_BAG.takeTile('E'));
        tiles.add(TEST_TILE_BAG.takeTile('N'));

//        On the wrong beginning tile
        assertEquals(newScrabbleGameModel.getPlayers().get(0),newScrabbleGameModel.getCurrentPlayer());
        assertFalse(newScrabbleGameModel.playWord(createWordEvent("QUEEN", 8, 7, false)));
        assertEquals(0,newScrabbleGameModel.getPlayers().get(0).getScore());
        assertEquals(newScrabbleGameModel.getPlayers().get(0),newScrabbleGameModel.getCurrentPlayer());
    }
}