import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RackModelTest {

    /**
     *@author Michael Kyrollos, 101183521
     * @version 1.0
     * @date November 11, 2022
     */
    RackModel rack;
    @BeforeEach
    void setUp() {
        rack = new RackModel();
    }

    @AfterEach
    void tearDown() {rack = null;}


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
    void testRemoveValidTilesFromRack() {
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
    void testRemoveNonExistentTilesFromNonEmptyRack() {
        Tile tileB = new Tile('B',5);
        Tile tileZ = new Tile('Z',0);
        rack.getTiles().add(tileB);
        rack.getTiles().add(tileZ);
        assertNull(rack.removeTile('A'));
        assertNull(rack.removeTile('K'));
    }

    @Test
    void testRemoveInvalidTilesFromNonEmptyRack() {
        rack.fillRack();
        assertNull(rack.removeTile('5'));
        assertNull(rack.removeTile('b'));
        assertNull(rack.removeTile('.'));
    }

    @Test
    void testRemoveInvalidTileTypeFromRack() {
        Tile tileB = new Tile('B',5);
        Tile tileZ = new Tile('Z',0);
        rack.getTiles().add(tileB);
        rack.getTiles().add(tileZ);
    }

    @Test
    void testRemoveTileFromEmptyRack() {
        assertEquals(0, rack.getTiles().size());
        assertNull(rack.removeTile('A'));
        assertNull(rack.removeTile('B'));
        assertEquals(0, rack.getTiles().size());
    }

    @Test
    void testFillRack() {
        assertEquals(0, rack.getTiles().size());
        rack.fillRack();
        assertEquals(7, rack.getTiles().size());
        for (int i=0; i < 7; i++) {
            assertNotNull(rack.getTiles().get(i));
        }

    }

    @Test
    @SuppressWarnings("unchecked")
    void testRemoveListOfTilesForRedraw() {

        assertEquals(0, rack.getTiles().size());
        rack.fillRack();

        //Create 2 lists, one original, one with the tiles to redraw
        ArrayList<Tile> redrawTiles = new ArrayList<>();
        ArrayList<Tile> originalTiles = new ArrayList<>();

        //Clone original list into both
        try {
            redrawTiles = (ArrayList<Tile>) rack.getTiles().clone();
            originalTiles = (ArrayList<Tile>) rack.getTiles().clone();
        }
        catch(ClassCastException e) {
            fail("Failed to convert clone to array list of tiles");
        }

        //ensure original rack is full and that other racks are equal
        assertTrue(originalTiles.containsAll(rack.getTiles()));
        assertTrue(redrawTiles.containsAll(rack.getTiles()));
        assertEquals(7,(rack.getTiles()).size());
        assertEquals(7,redrawTiles.size());
        rack.removeTiles(redrawTiles);
        assertEquals(0,rack.getTiles().size());

        rack.fillRack();
     //check that it is refilled and that the lists have been modified
        assertEquals(7,(rack.getTiles().size()));
        assertFalse(rack.getTiles().containsAll(originalTiles));
        assertFalse(originalTiles.containsAll(rack.getTiles()));
    }

    @Test
    void testIsEmpty() {
        assertEquals(0, rack.getTiles().size());
        assertTrue(rack.isEmpty());
        rack.fillRack();
        assertFalse(rack.isEmpty());
    }


}
