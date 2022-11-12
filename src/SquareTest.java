import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {
    Square square1;
    Square square2;
    Tile tile1;
    Tile tile2;
    Tile tile3;


    @BeforeEach
    void setUp() {
        square1 = new Square();
        tile1 = new Tile('A',6);
        tile2 = new Tile('G',4);
        tile3 = new Tile('P',1);
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
    void testToString() {
        assertEquals("A",tile1.toString());
        assertEquals("G",tile2.toString());
        assertEquals("P",tile3.toString());
    }

    @Test
    void testPlaceTileOnSquare() {
        square1.placeTile(tile1);
        assertEquals(tile1, square1.getTile());

        square2.placeTile(tile2);
        assertEquals(tile2, square2.getTile());
    }


    @Test
    void testSquareDeepCopyConstructorRealTile() {
        square1.placeTile(tile1);
        square1.getTile();
        square2 = new Square(square1);
        Square square3 = new Square(square2);
        assertEquals(square1.getTile(), square2.getTile());
        assertEquals(square2.getTile(), square3.getTile());
        assertEquals(square1.getTile(), square3.getTile());
    }

}