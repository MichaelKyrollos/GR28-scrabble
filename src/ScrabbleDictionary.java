import java.io.File;
import java.util.Scanner;

/**
 * This class is used as the dictionary for the scrabble game.
 * It can be used to check if a word is a legal word that can be played.
 * A text file containing a list of words can be used to create the dictionary,
 * but this class assumes that all words in the text file are valid and only contain letters from the alphabet.
 * @author Yehan De Silva
 * @version 3.0
 * @date November 17, 2022
 */
public class ScrabbleDictionary {

    /**
     * This class represents the data structure used to store the dictionary.
     * @author Yehan De Silva
     * @version 3.0
     * @date October 25, 2022
     */
    static class DictionaryNode {

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
        final DictionaryNode[] children;
        /**
         * Specifies if the current node is the end of a word.
         */
        boolean terminal;

        /**
         * Constructor to create an instance of a DictionaryNode.
         * @author Yehan De Silva
         * @version 1.0
         * @date October 23, 2022
         */
        private DictionaryNode() {
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
            return (letter - FIRST_LETTER);
        }

        /**
         * Converts a given integer to its string representation.
         * Converts only if given a positive integer.
         * @param num Number to be converted.
         * @return String representation of the number, empty String if negative number given.
         * @author Yehan De Silva
         * @version 1.1
         * @date October 25, 2022
         */
        String intToChar(int num) {
            return (num < 0) ? "" : Character.toString(num + FIRST_LETTER);
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

            //Looping for each character in the word to be added
            for (char c : word) {
                charNumericalValue = charToInt(c);
                //If the current letter is not a child of the node, create a node for it
                if (curNode.children[charNumericalValue] == null) {
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

            //Looping for each character in the word to be checked
            for (char c : word) {
                charNumericalValue = charToInt(c);
                //If the current letter is not a child of the node, return false
                if (curNode.children[charNumericalValue] == null) {
                    return false;
                }
                curNode = curNode.children[charNumericalValue]; //Go to the next node corresponding to the next letter
            }
            //Return the terminal flag of the last node, which specifies end of word has been reached
            return curNode.terminal;
        }
        public DictionaryNode wordLookup(char[] word) {
            int charNumericalValue;
            DictionaryNode curNode = this; //Get reference to the root node of the dictionary

            //Looping for each character in the word to be checked
            for (char c : word) {
                charNumericalValue = charToInt(c);
                //If the current letter is not a child of the node, return false
                if (curNode.children[charNumericalValue] == null) {
                    return null;
                }
                curNode = curNode.children[charNumericalValue]; //Go to the next node corresponding to the next letter
            }
            //Return the terminal flag of the last node, which specifies end of word has been reached
            return curNode;
        }
        /**
         * Helper method that returns a string representation of the contents in the dictionary structure.
         * @author Yehan De Silva
         * @version 1.0
         * @date October 25, 2022
         * @param node Node whose contents will be returned as a string representation.
         * @param s Pre-fix that must be added before the current node.
         * @param index Index in the array the current node is stored in.
         * @return The string representation of contents in the dictionary structure.
         */
        private String toStringHelper(DictionaryNode node, String s, int index) {
            //Start with an empty string
            StringBuilder returnString = new StringBuilder();
            //Base case where the node is null, returns empty String
            if (node == null) {return "";}
            //If the current node is the end of the word, adds the word to the return string
            //Appends prefix + current letter + new line symbol
            if (node.terminal) {
                returnString.append(s).append(intToChar(index)).append("\n");}
            //Add pre-fix to all future recursion calls
            s += intToChar(index);
            //Loop through each child of the node, and recursively call the toStringHelper method
            for (int i = 0; i < node.children.length; i++) {
                returnString.append(toStringHelper(node.children[i], s, i));
            }
            return returnString.toString();
        }

        /**
         * Overrides the default toString method to return a string representation of the contents in
         * the dictionary structure.
         * @author Yehan De Silva
         * @version 1.0
         * @date October 25, 2022
         * @return The string representation of contents in the dictionary structure.
         */
        @Override
        public String toString() {
            //Default call to the helper method
            return toStringHelper(this, "", -1);
        }
    }

    /**
     * The default dictionary file to be used.
     */
    public static final File DEFAULT_DICTIONARY = new File(new File("").getAbsolutePath() + "/src/resources/default_dictionary.txt");

    /**
     * The second default dictionary file to be used.
     */
    public static final File DEFAULT_DICTIONARY_2 = new File(new File("").getAbsolutePath() + "/src/resources/default_dictionary2.txt");

    /**
     * The dictionary structure to hold the dictionary.
     */
    final DictionaryNode dictionary;

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
     * @version 1.1
     * @date November 09, 2022
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
     * Checks if a specified word is in the dictionary (Type-case is not checked).
     * @param word Word to check if it is in the dictionary.
     * @return True if the word is in the dictionary, false otherwise.
     * @author Yehan De Silva
     * @version 1.1
     * @date November 09, 2022
     */
    public boolean validateWord(String word) {
        return dictionary.isWordInDictionary(word.toLowerCase().toCharArray());
    }

    /**
     * Returns a string representation of the dictionary structure, a list of all dictionary words in alphabetic
     * order seperated by a new line.
     * @author Yehan De Silva
     * @version 1.0
     * @date October 25, 2022
     * @return String representation of the dictionary
     */
    @Override
    public String toString() {
        return this.dictionary.toString();
    }

}
