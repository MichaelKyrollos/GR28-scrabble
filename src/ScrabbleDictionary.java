import java.io.File;
import java.util.Scanner;

/**
 * This class is used as the dictionary for the scrabble game.
 * It can be used to check if a word is a legal word that can be played.
 * A text file containing a list of words can be used to create the dictionary,
 * but this class assumes that all words in the text file are valid and only contain letters from the alphabet.
 * @author Yehan De Silva
 * @version 1.0
 * @date October 23, 2022
 */
public class ScrabbleDictionary {

    /**
     * This class represents the data structure used to store the dictionary.
     * @author Yehan De Silva
     * @version 1.0
     * @date October 23, 2022
     */
    class DictionaryNode {

        /**
         *The max amount of different letters in the dictionary.
         */
        public static final int MAX_LETTER_COUNT = 26;
        /**
         * The first word of the dictionary alphabet.
         */
        public static final char FIRST_LETTER = 'a';
        /**
         * Contains the post-fixes of the current node.
         */
        private DictionaryNode[] children;
        /**
         * Specifies if the current node is the end of a word.
         */
        private boolean terminal;

        /**
         * Constructor to create an instance of a DictionaryNode.
         * @author Yehan De Silva
         * @version 1.0
         * @date October 23, 2022
         */
        public DictionaryNode() {
            children = new DictionaryNode[MAX_LETTER_COUNT];
            terminal = false;
        }

        /**
         * Converts a given character to its numerical representation.
         * @param letter Letter to be converted.
         * @return Numerical representation of the letter.
         * @author Yehan De Silva
         * @version 1.0
         * @date October 23, 2022
         */
        private int charToInt(char letter) {
            return (int) (letter - FIRST_LETTER);
        }

        /**
         * Adds a specified word to the dictionary structure.
         * @param word Word to be added to the dictionary.
         * @author Yehan De Silva
         * @version 1.0
         * @date October 23, 2022
         */
        private void addWord(char[] word) {
            int charNumericalValue;
            DictionaryNode curNode = this; //Get reference to the root node of the dictionary

            //Looping for each letter in the word to be added
            for(int i = 0; i < word.length; i++) {
                charNumericalValue = charToInt(word[i]);
                //If the current letter is not a child of the node, create a node for it
                if(curNode.children[charNumericalValue] == null) {
                    curNode.children[charNumericalValue] = new DictionaryNode();
                }
                curNode = curNode.children[charNumericalValue]; //Go to the next node corresponding to the next letter
            }
            curNode.terminal = true; //Specify end of word by setting terminal flag at the last node
        }

        /**
         * Check if a specified word is in the dictionary structure.
         * @param word Word to check if it is in the dictionary structure.
         * @return True if word is in the dictionary structure, false otherwise.
         * @author Yehan De Silva
         * @version 1.0
         * @date October 23, 2022
         */
        private boolean isWordInDictionary(char[] word) {
            int charNumericalValue;
            DictionaryNode curNode = this; //Get reference to the root node of the dictionary

            //Looping for each letter in the word to be checked
            for(int i = 0; i < word.length; i++) {
                charNumericalValue = charToInt(word[i]);
                //If the current letter is not a child of the node, return false
                if(curNode.children[charNumericalValue] == null) {
                    return false;
                }
                curNode = curNode.children[charNumericalValue]; //Go to the next node corresponding to the next letter
            }
            //Return the terminal flag of the last node, which specifies end of word has been reached
            return curNode.terminal;
        }
    }

    /**
     * The default dictionary file to be used.
     */
    public static final File DEFAULT_DICTIONARY = new File("GR28-scrabble/src/resources/default_dictionary.txt");

    /**
     * The dictionary structure to hold the dictionary.
     */
    private DictionaryNode dictionary;

    /**
     * Constructor to instantiate a default dictionary.
     * @author Yehan De Silva
     * @version 1.0
     * @date October 23, 2022
     */
    public ScrabbleDictionary() {
        this(DEFAULT_DICTIONARY);
    }

    /**
     * Constructor to instantiate a dictionary with the given file.
     * @param file File to be used to create the dictionary.
     * @author Yehan De Silva
     * @version 1.0
     * @date October 23, 2022
     */
    public ScrabbleDictionary(File file) {
        dictionary = new DictionaryNode();
        createDictionary(file);
    }

    /**
     * Creates the dictionary.
     * @param file Text file containing all valid words for the dictionary.
     * @author Yehan De Silva
     * @version 1.0
     * @date October 23, 2022
     */
    private void createDictionary(File file) {
        //Reads the text file
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
            System.exit(1);
        }

        //For each word, add it to the dictionary structure
        while(scanner.hasNext()) {
            dictionary.addWord(scanner.next().trim().toLowerCase().toCharArray());
        }
    }

    /**
     * Checks if a specified word is in the dictionary.
     * @param word Word to check if it is in the dictionary.
     * @return True if the word is in the dictionary, false otherwise.
     * @author Yehan De Silva
     * @version 1.0
     * @date October 23, 2022
     */
    public boolean validateWord(String word) {
        return dictionary.isWordInDictionary(word.toCharArray());
    }
}
