import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;
/**
 * LetterTree is used as the dictionary for the AI.
 * A separate dictionary is used for the AI as it will aid with computation speed, testing, debugging, modularity.
 * A text file containing a list of words can be used to create the dictionary. This dictionary uses the tree-node
 * structure specifically implemented for the use-cases of the AI. This prevents access issues to ScrabbleDictionary
 * which was previously an issue with the AI.
 * Part of the suite of classes reserved solely for the AI: AIBoard.java, AI.java, AIPlayer.java, LetterTree.java
 * (SEE DOCUMENTATION FOR MORE INFORMATION)
 *
 * @author Michael Kyrollos
 * @version 1.0
 * @date December 4th, 2022
 */
public class LetterTree implements Serializable{

    /**
     * The default dictionary file to be used.
     */
    public static final File AI_DICTIONARY = new File(new File("").getAbsolutePath() + "/src/resources/default_dictionary.txt");
    public static class LetterTreeNode implements Serializable{
        public boolean isWord;
        public HashMap<Character,LetterTreeNode> children;

        /**
         * Constructs a new LetterTreeNode storing whether it is a word or not.
         * @param isWord Boolean representation of the letters stored indicating if it forms a word.
         *
         * @author Michael Kyrollos, 101183521
         * @version 1.0
         * @date November 24th 2022
         */
        public LetterTreeNode(boolean isWord) {
            this.isWord = isWord;
            this.children = new HashMap<>();
        }
    }
    /**
     * Beginning of the tree
     */
    public LetterTreeNode root;
    /**
     * Constructs a new LetterTree. Consisting of all nodes.
     *
     * @author Michael Kyrollos, 101183521
     * @version 1.0
     * @date November 24th 2022
     */
        LetterTree(){
            // Scanning for each next line
            this.root = new LetterTreeNode(false);
            LetterTreeNode current_node;

            Scanner scanner = null;
            try {
                scanner = new Scanner(AI_DICTIONARY);
            }
            catch (Exception e) {
                System.out.println("Error: " + e);
                System.exit(1);
            }

            //For each word, add it to the dictionary structure
            while(scanner.hasNext()) {
                char[] word = scanner.next().trim().toLowerCase().toCharArray();
                current_node = root;
                for (char c : word) {
                    Character key = new Character(c);
                    if (!current_node.children.containsKey(key)) {
                        LetterTreeNode newNode = new LetterTreeNode(false);
                        current_node.children.put(key, newNode);
                    }
                    current_node = current_node.children.get(key);
                }
                current_node.isWord = true;
            }
        }

        /**
         * Checks if word is currently in the dictionary.
         * @param word String of the word that is to be looked up in the dictionary.
         * @return The LetterTreeNode that holds the current word.
         *
         * @author Michael Kyrollos, 101183521
         * @version 1.2
         * @date November 24th 2022
         */
        public LetterTreeNode lookup(String word) {
            LetterTreeNode current_node;
            current_node = root;
            for (char c : word.toCharArray()) {
                Character key = new Character(c);
                if (!current_node.children.containsKey(key)) {
                    return null;
                }
                current_node = current_node.children.get(key);
            }
            return current_node;
        }

        /**
         * Checks if word is currently in the dictionary.
         * @param word String of the word that is to be looked up in the dictionary.
         * @return True if the word is valid according to the txt, false otherwise.
         *
         * @author Michael Kyrollos, 101183521
         * @version 1.2
         * @date November 24th 2022
         */
        public boolean isWord(String word){
            LetterTreeNode word_node = lookup(word);
            if (word_node==null){
                return false;
            }
            return word_node.isWord;
        }

}
