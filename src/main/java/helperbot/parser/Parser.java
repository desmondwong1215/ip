package helperbot.parser;


import helperbot.command.AddCommand;
import helperbot.command.CheckCommand;
import helperbot.command.Command;
import helperbot.command.DeleteCommand;
import helperbot.command.ExitCommand;
import helperbot.command.ListCommand;
import helperbot.command.MarkCommand;
import helperbot.exception.HelperBotCommandException;

/**
 * Represent a class that parse all the command from user's input to corresponding <code>Command</code>.
 */
public class Parser {

    /**
     * Parse the input from user to a <code>Command</code>.
     * @param message The input from user.
     * @return A <code>Command</code> containing the message.
     * @throws HelperBotCommandException The first word of the input is not a valid command.
     */
    public static Command parse(String message) throws HelperBotCommandException {
        String[] splitMessage = message.split(" ");
        String command = splitMessage[0].toLowerCase();

         return switch (command) {
         case "bye" -> new ExitCommand();
         case "list" -> new ListCommand();
         case "mark" -> new MarkCommand(splitMessage, true);
         case "unmark" -> new MarkCommand(splitMessage, false);
         case "todo", "deadline", "event" -> new AddCommand(command, message);
         case "delete" -> new DeleteCommand(splitMessage);
         case "check" -> new CheckCommand(splitMessage);
         // invalid helperbot.command
         default -> throw new HelperBotCommandException(splitMessage[0] + " is not found.");
         };
    }
}
