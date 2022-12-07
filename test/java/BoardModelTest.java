import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * This class is used to test the BoardModel class of the Scrabble Game.
 *
 * @author Michael Kyrollos, 101183521
 * @version 4.0
 * @date December 07, 2022
 */
class BoardModelTest {
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
    }

    @AfterEach
    void tearDown() {
        newGame = null;
        newBoardModel = null;
        tiles = null;
        TEST_TILE_BAG = null;
    }

    private PlayWordEvent createWordEvent(String word, int level, int start_of_word, boolean vertical) {
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


    /*
     * Word must be placed at the center of the board initially.
     * Ensure the program doesn't let the user start in the wrong place.
     */
    @Test
    void testPlaceWordInitiallyInvalid() {
        tiles.add(new Tile('A', 1));
        tiles.add(new Tile('B', 3));
        tiles.add(new Tile('C', 3));
        assertEquals(-1, newBoardModel.placeWord(createWordEvent("ABC", 6, 6, true)));
    }


    /*
     * Ensure the board can be initialized properly when tiles are placed in the center initially
     */
    @Test
    void testPlaceInitiallyValid() {
        tiles.add(TEST_TILE_BAG.takeTile('P'));
        tiles.add(TEST_TILE_BAG.takeTile('A'));
        tiles.add(TEST_TILE_BAG.takeTile('C'));
        tiles.add(TEST_TILE_BAG.takeTile('K'));
        assertEquals(24, newBoardModel.placeWord(createWordEvent("PACK", 7, 7, true)));
    }

    /*
     * A horizontal cross between words, a simple 2 word legal play
     */
    @Test
    void testPlaceTwoWordsHorizontalCross() {
        tiles.add(TEST_TILE_BAG.takeTile('A'));
        tiles.add(TEST_TILE_BAG.takeTile('B'));
        tiles.add(TEST_TILE_BAG.takeTile('C'));

        assertEquals(14, newBoardModel.placeWord(createWordEvent("ABC", 7, 7, false)));

        tiles.add(TEST_TILE_BAG.takeTile('B'));
        tiles.add(TEST_TILE_BAG.takeTile('B'));
        tiles.add(TEST_TILE_BAG.takeTile('C'));

        assertEquals(18, newBoardModel.placeWord(createWordEvent("BBC", 8, 6, true)));
    }

    /*
     * Testing the connection of multiple letter words with multiple meeting points.
     * Ensure score keeping is accurate
     */
    @Test
    void testPlaceComplexWordWithHorizontalAndVerticalPoints() {
        tiles.add(TEST_TILE_BAG.takeTile('A'));
        tiles.add(TEST_TILE_BAG.takeTile('P'));
        tiles.add(TEST_TILE_BAG.takeTile('P'));

        assertEquals(14, newBoardModel.placeWord(createWordEvent("APP", 7, 7, false)));

        tiles.clear();

        tiles.add(TEST_TILE_BAG.takeTile('P'));
        tiles.add(TEST_TILE_BAG.takeTile('R'));
        tiles.add(TEST_TILE_BAG.takeTile('I'));
        tiles.add(TEST_TILE_BAG.takeTile('N'));
        tiles.add(TEST_TILE_BAG.takeTile('T'));

        assertEquals(15, newBoardModel.placeWord(createWordEvent("PRINT", 8, 7, true)));
        tiles.clear();
        //noinspection ConstantConditions
        assertEquals(0, tiles.size());

        tiles.add(TEST_TILE_BAG.takeTile('O'));
        tiles.add(TEST_TILE_BAG.takeTile('U'));
        tiles.add(TEST_TILE_BAG.takeTile('T'));

        assertEquals(8, newBoardModel.placeWord(createWordEvent("OUT", 7, 10, true)));
    }

    /*
     * Test returned result when word is placed vertically but extends out of the board
     */
    @Test
    void testPlaceWordOutOfBoundsVertical() {
//        For now doing this will throw an exception, the player is not able to click past the border within the
//        UI but this test ensures that the board is not extended when words pass the board length

        tiles.add(TEST_TILE_BAG.takeTile('P'));
        tiles.add(TEST_TILE_BAG.takeTile('L'));
        tiles.add(TEST_TILE_BAG.takeTile('A'));
        tiles.add(TEST_TILE_BAG.takeTile('Y'));

        assertEquals(18, newBoardModel.placeWord(createWordEvent("PLAY", 7, 7, true)));
        tiles.clear();
        //noinspection ConstantConditions
        assertEquals(0, tiles.size());

        tiles.add(TEST_TILE_BAG.takeTile('A'));
        tiles.add(TEST_TILE_BAG.takeTile('B'));
        tiles.add(TEST_TILE_BAG.takeTile('B'));
        tiles.add(TEST_TILE_BAG.takeTile('R'));
        tiles.add(TEST_TILE_BAG.takeTile('E'));
        tiles.add(TEST_TILE_BAG.takeTile('V'));
        tiles.add(TEST_TILE_BAG.takeTile('I'));
        tiles.add(TEST_TILE_BAG.takeTile('A'));
        tiles.add(TEST_TILE_BAG.takeTile('T'));
        tiles.add(TEST_TILE_BAG.takeTile('I'));
        tiles.add(TEST_TILE_BAG.takeTile('O'));
        tiles.add(TEST_TILE_BAG.takeTile('N'));

        boolean thrown = false;
        try {
            assertEquals(-1, newBoardModel.placeWord(createWordEvent("ABBREVIATION", 9, 7, true)));
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;

        }
        assertTrue(thrown);
    }

    /*
     * Test returned result when word is placed horizontally but extends out of the board
     */
    @Test
    void testPlaceWordOutOfBoundsHorizontal() {
//        For now doing this will throw an exception, the player is not able to click past the border within the
//        UI but this test ensures that the board is not extended when words pass the board length

        tiles.add(TEST_TILE_BAG.takeTile('E'));
        tiles.add(TEST_TILE_BAG.takeTile('A'));
        tiles.add(TEST_TILE_BAG.takeTile('R'));
        tiles.add(TEST_TILE_BAG.takeTile('T'));
        tiles.add(TEST_TILE_BAG.takeTile('H'));
        tiles.add(TEST_TILE_BAG.takeTile('I'));
        tiles.add(TEST_TILE_BAG.takeTile('N'));
        tiles.add(TEST_TILE_BAG.takeTile('E'));
        tiles.add(TEST_TILE_BAG.takeTile('S'));
        tiles.add(TEST_TILE_BAG.takeTile('S'));

        boolean thrown = false;
        try {
            assertEquals(9, newBoardModel.placeWord(createWordEvent("EARTHINESS", 7, 7, false)));
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

     /*
       * Test when two words overlap but the meeting point does not form a word
     */
    @Test
    void testPlaceInvalidSecondWordOverTileVertical() {

        tiles.add(TEST_TILE_BAG.takeTile('E'));
        tiles.add(TEST_TILE_BAG.takeTile('N'));
        tiles.add(TEST_TILE_BAG.takeTile('D'));

        assertEquals(8, newBoardModel.placeWord(createWordEvent("END", 7, 7, true)));
        tiles.clear();
//        doing this in case tiles did not clear
        //noinspection ConstantConditions
        assertEquals(0, tiles.size());

        tiles.add(TEST_TILE_BAG.takeTile('H'));
        tiles.add(TEST_TILE_BAG.takeTile('O'));
        tiles.add(TEST_TILE_BAG.takeTile('M'));
        tiles.add(TEST_TILE_BAG.takeTile('E'));

        assertEquals(-1, newBoardModel.placeWord(createWordEvent("HOME", 7, 5, true)));
    }

    /*
     * Test when two words overlap but the meeting point does not form a word, horizontal
     */
    @Test
    void testPlaceInvalidSecondWordOverAnotherTileHorizontal() {

        tiles.add(TEST_TILE_BAG.takeTile('F'));
        tiles.add(TEST_TILE_BAG.takeTile('A'));
        tiles.add(TEST_TILE_BAG.takeTile('M'));
        tiles.add(TEST_TILE_BAG.takeTile('E'));

        assertEquals(18, newBoardModel.placeWord(createWordEvent("FAME", 7, 7, true)));
        tiles.clear();
//        doing this in case tiles did not clear
        //noinspection ConstantConditions
        assertEquals(0, tiles.size());


        tiles.add(TEST_TILE_BAG.takeTile('L'));
        tiles.add(TEST_TILE_BAG.takeTile('E'));
        tiles.add(TEST_TILE_BAG.takeTile('A'));
        tiles.add(TEST_TILE_BAG.takeTile('V'));
        tiles.add(TEST_TILE_BAG.takeTile('E'));

        assertEquals(27, newBoardModel.placeWord(createWordEvent("LEAVE", 8, 5, true)));
        tiles.clear();
//        doing this in case tiles did not clear
        //noinspection ConstantConditions
        assertEquals(0, tiles.size());

        tiles.add(TEST_TILE_BAG.takeTile('K'));
        tiles.add(TEST_TILE_BAG.takeTile('N'));
        tiles.add(TEST_TILE_BAG.takeTile('E'));
        tiles.add(TEST_TILE_BAG.takeTile('L'));
        tiles.add(TEST_TILE_BAG.takeTile('T'));
        assertEquals(-1, newBoardModel.placeWord(createWordEvent("KNELT", 9, 6, false)));
    }

    /*
     * Test when the copying squares method, this is important when reverting the boards status
     * If this passes, then the board method doesn't need to be tested.
     */
    @Test
    void testCopySquare() {
        assertNull(newBoardModel.getCopiedSquares());
        newBoardModel.copyBoardSquares();
        assertNotNull(newBoardModel.getCopiedSquares());
        for (int i = 0; i < newBoardModel.getSquares().length; i++) {
            for (int j = 0; j < newBoardModel.getSquares()[i].length; j++) {
                assertEquals(newBoardModel.getSquares()[i][j].getXCoord(), newBoardModel.getCopiedSquares()[i][j].getXCoord());
                assertEquals(newBoardModel.getSquares()[i][j].getYCoord(), newBoardModel.getCopiedSquares()[i][j].getYCoord());
                assertEquals(newBoardModel.getSquares()[i][j].getTile(), newBoardModel.getCopiedSquares()[i][j].getTile());
            }
        }
    }

}
