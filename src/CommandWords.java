import java.util.HashMap;

/**
 *
 * The class holds an enumeration of all command words known to the game.
 * Possible commands are all listed in CommandWord - Enum.
 *
 * @author  Michael Kyrollos
 * @date October 22, 2022
 */

public class CommandWords
{
    private final HashMap<String, CommandWord> possibleCommands;

    public CommandWords()
    {
        possibleCommands = new HashMap<>();
        for(CommandWord command : CommandWord.values()) {
            if(command != CommandWord.INVALID) {
                possibleCommands.put(command.toString(), command);
            }
        }
    }

    /**
     *
     * Print all valid commands.
     *
     * @author Michael Kyrollos, ID: 101183521
     */
    public void showAll()
    {
        for(String command : possibleCommands.keySet()) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }

    /**
     * Look for CommandWord associated with a command word.
     *
     * @author Michael Kyrollos, ID: 101183521
     *
     * @param commandWord The word to find.
     * @return The CommandWord that is related to commandWord (param), or INVALID
     *  if it is not a valid command word.
     *
     *
     */
    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord command = possibleCommands.get(commandWord);
        if(command != null) {
            return command;
        }
        else {
            return CommandWord.INVALID;
        }
    }

}
