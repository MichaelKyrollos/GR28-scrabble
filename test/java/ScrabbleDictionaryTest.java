import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;

class ScrabbleDictionaryTest {

    /**
     * The favorite games dictionary file to be used.
     */
    public static final File FAV_GAME_DICTIONARY = new File(new File("").getAbsolutePath() + "/src/resources/favorite_games_test_dictionary.txt");

    /**
     * The default dictionary to be tested.
     */
    private ScrabbleDictionary defaultDictionary;
    private Scanner defaultDictionaryScanner;

    /**
     * The favorite games dictionary to be tested.
     */
    private ScrabbleDictionary favGameDictionary;
    private Scanner favGameDictionaryScanner;

    /**
     * Runs setUp method before each test case.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @BeforeEach
    public void setUp() {
        defaultDictionary = new ScrabbleDictionary();
        try {
            defaultDictionaryScanner = new Scanner(ScrabbleDictionary.DEFAULT_DICTIONARY);
        }
        catch (Exception e) {
            System.out.println("Test Error: " + e);
            System.exit(1);
        }

        favGameDictionary = new ScrabbleDictionary(FAV_GAME_DICTIONARY);
        try {
            favGameDictionaryScanner = new Scanner(FAV_GAME_DICTIONARY);
        }
        catch (Exception e) {
            System.out.println("Test Error: " + e);
            System.exit(1);
        }
    }

    /**
     * Runs tearDown method after each test case.
     * @author Yehan De Silva
     * @version 4.0
     * @date November 30, 2022
     */
    @AfterEach
    void tearDown() {
        defaultDictionary = null;
        defaultDictionaryScanner = null;
        favGameDictionary = null;
        favGameDictionaryScanner = null;
    }

    /**
     * Test if "a", the first word is valid in the default dictionary. Should return True.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @Test
    public void testAInDefaultDictionary() {
        assertTrue(defaultDictionary.validateWord("a"));
    }

    /**
     * Test if "A", a capital word is valid in the default dictionary. Should return True.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @Test
    public void testCapitalAInDefaultDictionary() {
        assertTrue(defaultDictionary.validateWord("A"));
    }

    /**
     * Test if "zus", the last word is valid in the default dictionary. Should return True.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @Test
    public void testZusInDefaultDictionary() {
        assertTrue(defaultDictionary.validateWord("zus"));
    }

    /**
     * Test if "abcde", a fake word is valid in the default dictionary. Should return False.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @Test
    public void testAbcdeNotInDefaultDictionary() {assertFalse(defaultDictionary.validateWord("abcde"));}

    /**
     * Test if all words in default dictionary are valid. Should return True.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @Test
    public void testAllWordsInDefaultDictionaryAreValid() {
        //For each word in dictionary text file, validate it
        while(defaultDictionaryScanner.hasNext()) {
            assertTrue(defaultDictionary.validateWord(defaultDictionaryScanner.next().trim()));
        }
    }

    /**
     * Test if "Backgammon" is in the favorite games dictionary. Should return True.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @Test
    public void testBackgammonInFavGameDictionary() {
        assertTrue(favGameDictionary.validateWord("Backgammon"));
    }

    /**
     * Test if "Battleship" is in the favorite games dictionary. Should return True.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @Test
    public void testBattleshipInFavGameDictionary() {
        assertTrue(favGameDictionary.validateWord("Battleship"));
    }

    /**
     * Test if "Checkers" is in the favorite games dictionary. Should return True.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @Test
    public void testCheckersInFavGameDictionary() {
        assertTrue(favGameDictionary.validateWord("Checkers"));
    }

    /**
     * Test if "Chess" is in the favorite games dictionary. Should return True.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @Test
    public void testChessInFavGameDictionary() {
        assertTrue(favGameDictionary.validateWord("Chess"));
    }

    /**
     * Test if "Clue" is in the favorite games dictionary. Should return True.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @Test
    public void testClueInFavGameDictionary() {
        assertTrue(favGameDictionary.validateWord("Clue"));
    }

    /**
     * Test if "Life" is in the favorite games dictionary. Should return True.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @Test
    public void testLifeInFavGameDictionary() {
        assertTrue(favGameDictionary.validateWord("Life"));
    }

    /**
     * Test if "Monopoly" is in the favorite games dictionary. Should return True.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @Test
    public void testMonopolyInFavGameDictionary() {
        assertTrue(favGameDictionary.validateWord("Monopoly"));
    }

    /**
     * Test if "Risk" is in the favorite games dictionary. Should return True.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @Test
    public void testRiskInFavGameDictionary() {
        assertTrue(favGameDictionary.validateWord("Risk"));
    }

    /**
     * Test if "Scrabble" is in the favorite games dictionary. Should return True.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @Test
    public void testScrabbleInFavGameDictionary() {
        assertTrue(favGameDictionary.validateWord("Scrabble"));
    }

    /**
     * Test if "Stratego" is in the favorite games dictionary. Should return True.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @Test
    public void testStrategoInFavGameDictionary() {
        assertTrue(favGameDictionary.validateWord("Stratego"));
    }

    /**
     * Test if "Twister" is in the favorite games dictionary. Should return False.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @Test
    public void testTwisterInFavGameDictionary() {
        assertFalse(favGameDictionary.validateWord("Twister"));
    }

    /**
     * Test if the favorite games dictionary prints in the expected format.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 09, 2022
     */
    @Test
    public void testFavGameDictionaryPrintsCorrectly() {
        StringBuilder expectedString = new StringBuilder();
        while(favGameDictionaryScanner.hasNext()) {
            expectedString.append(favGameDictionaryScanner.next().trim()).append("\n");
        }
        assertEquals(expectedString.toString(), favGameDictionary.toString());
    }
}