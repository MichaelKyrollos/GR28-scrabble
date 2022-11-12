import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    ScrabbleGame newGame;
    Board newBoard;

    Tile tile1;
    Tile tile2;
    Tile tile3;
    Tile tile4;
    Tile tile5;
    Tile tile6;
    ArrayList<Tile> tiles;
    ArrayList<Tile> tiles1;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));

        ScrabbleGame newGame = new ScrabbleGame("test1");
        newBoard = new Board(newGame);
        tiles = new ArrayList<>();
        tile1 = new Tile('A',1);
        tile2 = new Tile('B',3);
        tile3 = new Tile('C',3);
        tile4 = new Tile('B',3);
        tile5 = new Tile('B',3);
        tile6 = new Tile('C',3);
//        tile7 = new Tile('Z',3);
//        tile8 = new Tile('',3);
//        tile9 = new Tile('C',3);

        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);


        tiles1 = new ArrayList<>();
        tiles1.add(tile4);
        tiles1.add(tile5);
        tiles1.add(tile6);

    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void testPlaceWordInitiallyInvalidConsoleOut() {
        assertEquals(-1,newBoard.placeWord("LBC","2D",tiles));
        Assert.assertEquals("Invalid placement: The first word placed must cover square H8 and be at least 2 letters long.", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testPlaceWordInitiallyValid() {
        assertEquals(7, newBoard.placeWord("ABC", "H8", tiles));
    }

    @Test
    void testPlaceWordInitiallyValid3Letters() {
        assertEquals(7, newBoard.placeWord("ABC", "H8", tiles));
    }

    @Test
    void testPlaceWordInitiallyValid4Letters() {
        tiles.add(tile6);
        assertEquals(10, newBoard.placeWord("ABCC", "H8", tiles));
        assertEquals(10, newBoard.placeWord("ABCC", "H8", tiles));
        assertEquals(10, newBoard.placeWord("ABCC", "H8", tiles));
    }

    @Test
    void testPlaceWordInitiallyValid7Letters() {
        tiles.add(tile4);
        tiles.add(tile5);
        tiles.add(tile6);
        assertEquals(16, newBoard.placeWord("ABCBBC", "H8", tiles));
    }


    @Test
    void testPlaceWordOutofBoundsHorziontal() {
        assertEquals(-1,newBoard.placeWord("LBC","15A",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","15B",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","15C",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","15D",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","15E",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","15F",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","15G",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","15H",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","15I",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","15J",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","15K",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","15L",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","15M",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","15N",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","15O",tiles));

    }

    @Test
    void testPlaceWordOutofBoundsHorziontalConsoleOutput() {
        assertEquals(-1,newBoard.placeWord("LBC","15D",tiles));
        Assert.assertEquals("Invalid placement: A letter has gone out of bounds.", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testPlaceWordOutofBoundsVerticalConsoleOutput() {
        assertEquals(-1,newBoard.placeWord("LBC","O5",tiles));
        Assert.assertEquals("Invalid placement: A letter has gone out of bounds.", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testPlaceWordOutofBoundsVertical() {
        assertEquals(-1,newBoard.placeWord("LBC","O1",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","O2",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","O3",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","O4",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","O5",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","O6",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","O7",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","O8",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","O9",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","O10",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","O11",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","O12",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","O13",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","O14",tiles));
        assertEquals(-1,newBoard.placeWord("LBC","O15",tiles));
    }

}