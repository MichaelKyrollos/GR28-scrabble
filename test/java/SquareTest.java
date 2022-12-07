import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the Square class of the Scrabble game.
 *
 * @author Michael Kyrollos, 101183521
 * @version 1.0
 * @date November 11, 2022
 */
class SquareTest {

    private Square square1;
    private Square square2;
    private Square square3;
    private Tile tile1;
    private Tile tile2;
    private Tile tile3;

    @BeforeEach
    void setUp() {
        square1 = new Square(2, 2);
        square2 = new Square(10, 13);
        square3 = new Square(12, 0);
        tile1 = new Tile('A', 6);
        tile2 = new Tile('G', 4);
        tile3 = new Tile('P', 1);
    }

    @AfterEach
    void tearDown() {
        square1 = null;
        square2 = null;
        square3 = null;
        tile1 = null;
        tile2 = null;
        tile3 = null;
    }

    @Test
    void testSquareDeepCopyConstructorNullTile() {
        square2 = new Square(square1);
        Square square3 = new Square(square2);
        assertEquals(square1.getTile(), square2.getTile());
        assertEquals(square2.getTile(), square3.getTile());
        assertEquals(square1.getTile(), square3.getTile());
    }

    @Test
    void testPlaceTileOnSquare() {
        square1.placeSquare(tile1);
        assertEquals(tile1, square1.getTile());

        square2.placeSquare(tile2);
        assertEquals(tile2, square2.getTile());

        square3.placeSquare(tile3);
        assertEquals(tile3, square3.getTile());
    }

    @Test
    void testSquareDeepCopyConstructorRealTile() {
        square1.placeSquare(tile1);
        square2 = new Square(square1);
        Square square3 = new Square(square2);
        assertEquals(square1.getTile(), square2.getTile());
        assertEquals(square2.getTile(), square3.getTile());
        assertEquals(square1.getTile(), square3.getTile());
    }

    @Test
    void testGetYCoordinate() {
        assertEquals(2, square1.getYCoord());
        assertEquals(13, square2.getYCoord());
        assertEquals(0, square3.getYCoord());

    }

    @Test
    void testGetXCoordinate() {
        assertEquals(2, square1.getXCoord());
        assertEquals(10, square2.getXCoord());
        assertEquals(12, square3.getXCoord());
    }
}
