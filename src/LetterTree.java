import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
public class LetterTree {
    public static final File DEFAULT_DICTIONARY = new File(new File("").getAbsolutePath() + "/src/resources/default_dictionary.txt");
    public static class LetterTreeNode {
    public boolean is_word;
    public HashMap<Character,LetterTreeNode> children;
    public LetterTreeNode(boolean is_word) {
        this.is_word = is_word;
        this.children = new HashMap<>();
    }
    }
    public LetterTreeNode root;

    LetterTree(){
        this.root = new LetterTreeNode(false);
        LetterTreeNode current_node;

        Scanner scanner = null;
        try {
            scanner = new Scanner(DEFAULT_DICTIONARY);
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
            current_node.is_word = true;
        }


    }
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

    public boolean is_word(String word){
        LetterTreeNode word_node = lookup(word);
        if (word_node==null){
            return false;
        }
        return word_node.is_word;
    }


}


