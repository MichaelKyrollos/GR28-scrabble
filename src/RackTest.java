import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RackTest {

    Rack rack;
    @BeforeEach
    void setUp() {
        rack = new Rack();
    }


    @Test
    void testRackInitiallyEmpty(){
        assertEquals(0,rack.getTiles().size());
    }


    @Test
    void testAddValidTilesUntilFull() {
        assertTrue(rack.addTile());
        assertEquals(1,rack.getTiles().size());
        assertTrue(rack.addTile());
        assertEquals(2,rack.getTiles().size());
        assertTrue(rack.addTile());
        assertEquals(3,rack.getTiles().size());
        assertTrue(rack.addTile());
        assertEquals(4,rack.getTiles().size());
        assertTrue(rack.addTile());
        assertEquals(5,rack.getTiles().size());
        assertTrue(rack.addTile());
        assertEquals(6,rack.getTiles().size());
        assertTrue(rack.addTile());
        assertEquals(7,rack.getTiles().size());
        assertFalse(rack.addTile());
//        try overfilling rack
        assertEquals(7,rack.getTiles().size());
    }

    @Test
    void removeValidTilesFromRack() {
//      Fill rack again  using fillRack(), this will not affect ability
//      to isolate failing tests as fillRack() is tested independently.
        rack.fillRack();
        assertEquals(7,rack.getTiles().size());

        Tile tempTile;
//      testing that removal of tiles, one by one
        for (int i=0; i < 7; i++) {
            tempTile = rack.getTiles().get(0);
            assertEquals(tempTile,rack.removeTile(tempTile.getLetter()));
//       testing that size of tiles (arrayList) is reduced each time
            assertEquals(6-i, rack.getTiles().size());
            assertNotNull(rack.getTiles());
        }
    }

    @Test
    void removeNonExistentTilesFromNonEmptyRack() {
        Tile tileB = new Tile('B',5);
        Tile tileZ = new Tile('Z',0);
        rack.getTiles().add(tileB);
        rack.getTiles().add(tileZ);
        assertNull(rack.removeTile('A'));
        assertNull(rack.removeTile('K'));
        }

    @Test
    void removeInvalidTilesFromNonEmptyRack() {
        rack.fillRack();
        assertNull(rack.removeTile('5'));
        assertNull(rack.removeTile('b'));
        assertNull(rack.removeTile('.'));
    }

    @Test
    void removeInvalidTileTypeFromRack() {
        Tile tileB = new Tile('B',5);
        Tile tileZ = new Tile('Z',0);
        rack.getTiles().add(tileB);
        rack.getTiles().add(tileZ);
    }

    @Test
    void removeTilesFromEmptyRack() {
        assertEquals(0, rack.getTiles().size());
        assertNull(rack.removeTile('A'));
        assertNull(rack.removeTile('B'));
        assertEquals(0, rack.getTiles().size());
    }

    @Test
    void fillRack() {
        assertEquals(0, rack.getTiles().size());
        rack.fillRack();
        Tile tempTile = new Tile('A',3);
        assertEquals(7, rack.getTiles().size());
        for (int i=0; i < 7; i++) {
//            tempTile = rack.getTiles().get(0);
//       testing that size of tiles (arrayList) is reduced each time
            assertEquals(6-i, rack.getTiles().size());
            assertNotNull(rack.getTiles());
        }

    }

    @Test
    void testToString() {
    }

    @Test
    void getTiles() {
    }
}