import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    /**
     * Tests the tile class
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.0
     * @date November 11, 2022
     */
    Tile tileA;
    Tile tileB;
    Tile tileG;
    Tile tileH;
    Tile tileZ;


    @BeforeEach
    void setUp(){
        tileB = new Tile('B',5);
        tileA = new Tile('A',2);
        tileZ = new Tile('Z',0);
        tileH = new Tile('H',22);
        tileG = new Tile('G',8);
    }

    @Test
    void testGetLetter() {
        assertEquals('B',tileB.getLetter());
        assertEquals('A',tileA.getLetter());
        assertEquals('Z',tileZ.getLetter());
        assertEquals('H',tileH.getLetter());
        assertEquals('G',tileG.getLetter());
    }

    @Test
    void testGetValue() {
        assertEquals(5,tileB.getValue());
        assertEquals(2,tileA.getValue());
        assertEquals(0,tileZ.getValue());
        assertEquals(22,tileH.getValue());
        assertEquals(8,tileG.getValue());
    }

    @Test
    void testToString() {
        assertEquals("<html>B<sub>5</sub></html>",tileB.toString());
        assertEquals("<html>A<sub>2</sub></html>",tileA.toString());
        assertEquals("<html>Z<sub>0</sub></html>",tileZ.toString());
        assertEquals("<html>H<sub>22</sub></html>",tileH.toString());
        assertEquals("<html>G<sub>8</sub></html>",tileG.toString());
    }

}
