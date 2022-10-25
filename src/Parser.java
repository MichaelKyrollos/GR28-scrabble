import java.util.Scanner;

/**
 * Parser will continuously read for commands
 *
 * @author Michael Kyrollos, 101183521
 * @version 1.0
 * @date October 23, 2022
 */

public class Parser
{
    private CommandWords commands;  // holds all valid command words

    private Scanner reader;

    public Parser()
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand()
    {
        String inputLine;
        String word1 = null;
        String word2 = null;
        String word3 = null;

        System.out.print("> ");

        inputLine = reader.nextLine();
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();
                if (tokenizer.hasNext()) {
                    word3 = tokenizer.next();
                }
            }
        }

        return new Command(commands.getCommandWord(word1), word2, word3);
    }

    /**
     * Print out a list of valid command words.
     */
    public void showCommands()
    {
        commands.showAll();
    }
}
