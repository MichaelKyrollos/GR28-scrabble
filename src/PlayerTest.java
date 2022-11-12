import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player1;
    private Player player2;
    private ArrayList<Player> players;

    @BeforeEach
    void setUp() {

        players = new ArrayList<>();
        ScrabbleGame newGame = new ScrabbleGame("test1");

        Board newBoard = new Board(newGame);
        player1 = new Player("player1",newBoard);
        player2 = new Player("player2",newBoard);

    }

    @Test
    void testInitialPlayerScoreZero() {
        assertEquals(0,player1.getScore());
        assertEquals(0,player2.getScore());
    }

    @Test
    void testPlayerScorePlaysInvalidWord(){
        assertFalse(player1.playWord("potato","6D"));
        assertEquals(0,player1.getScore());
        assertFalse(player2.playWord("potato","8D"));
        assertEquals(0,player1.getScore());
    }

    @Test
    void testPlayerNameInserted() {
        assertEquals("player1",player1.getName());
        assertEquals("player2",player2.getName());
    }

    @Test
    void testRackSizeInitial() {
        assertEquals(7,(player1.getRack().getTiles()).size());
    }

    @Test
    void testPlayWord() {

        ArrayList player1Rack =  player1.getRack().getTiles();
        String s ="";
//      Player should not be able to place a word because this tile does not
//        exist in the rack
        assertFalse(player1.playWord(" ","5D"));
//      Using string S to fill with the tiles from the rack
        for (Object t: player1Rack ) {
            s+=t;
        }
//      Player needs to start in the center, this line is a Board test
        assertFalse(player1.playWord(s,"5D"));
//      Placing in the center
        assertTrue(player1.playWord(s,"H8"));
//      Rack should be filled after playing
        assertEquals(7,(player1.getRack().getTiles()).size());
        s = "";
        ArrayList player2Rack =  player2.getRack().getTiles();
        for (Object t: player2Rack ) {
            s+=t;
        }
        assertTrue(player2.playWord(s,"A7"));
        assertEquals(7,(player2.getRack().getTiles()).size());

//      Ensure that score is not 0 after playing
        assertNotEquals(0,this.player1.getScore());
        assertNotEquals(0,this.player2.getScore());


    }
}
