import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SquareTest {
    /**
     *@author Michael Kyrollos, 101183521
     * @version 1.0
     * @date November 11, 2022
     */
    Square square1;
    Square square2;
    Square square3;
    Tile tile1;
    Tile tile2;
    Tile tile3;


    @BeforeEach
    void setUp() {
        square1 = new Square(2, 2);
        square2 = new Square(10, 13);
        square3 = new Square(12, 0);
        tile1 = new Tile('A', 6);
        tile2 = new Tile('G', 4);
        tile3 = new Tile('P', 1);
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
        square1.getTile();
        square2 = new Square(square1);
        Square square3 = new Square(square2);
        assertEquals(square1.getTile(), square2.getTile());
        assertEquals(square2.getTile(), square3.getTile());
        assertEquals(square1.getTile(), square3.getTile());
    }

    @Test
    void testGetYCoordinate() {
        assertEquals(2, square1.getyCoord());
        assertEquals(13, square2.getyCoord());
        assertEquals(0, square3.getyCoord());

    }

    @Test
    void testGetXCoordinate() {
        assertEquals(2, square1.getxCoord());
        assertEquals(10, square2.getxCoord());
        assertEquals(12, square3.getxCoord());
    }

}
