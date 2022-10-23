import java.io.File;
import java.util.Scanner;

/*class DictionaryNode {

    public static final int MAX_LETTER_COUNT = 26;
    private DictionaryNode[] letter;
    private boolean terminal;

    public DictionaryNode() {
        letter = new DictionaryNode[MAX_LETTER_COUNT];
        terminal = false;
    }
}*/

public class ScrabbleDictionary {

    public static final File DEFAULT_DICTIONARY = new File("GR28-scrabble/src/resources/default_dictionary.txt");

    private Scanner scanner;
    //private DictionaryNode dictionary;
    
    public ScrabbleDictionary() {
        this(DEFAULT_DICTIONARY);
    }
    
    public ScrabbleDictionary(File dictionaryFilename) {
        openFile(dictionaryFilename);
        //dictionary = new DictionaryNode();
        //createDictionary();
    }

    private void openFile(File filename) {
        try {
            scanner = new Scanner(filename);
        }
        catch (Exception e) {
            System.out.println("Failed to open file " + filename);
            System.exit(1);
        }
    }

    private void createDictionary() {
        while(scanner.hasNext()) {
            System.out.println(scanner.next().trim().toLowerCase());
        }
    }


    public static void main(String[] args) {
        /*ScrabbleDictionary d = new ScrabbleDictionary();
        d.openFile(DEFAULT_DICTIONARY);
        System.out.println("Done!");
        d.createDictionary();*/

    }

}
