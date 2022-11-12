import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

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
        assertEquals("B",tileB.toString());
        assertEquals("A",tileA.toString());
        assertEquals("Z",tileZ.toString());
        assertEquals("H",tileH.toString());
        assertEquals("G",tileG.toString());
    }
}