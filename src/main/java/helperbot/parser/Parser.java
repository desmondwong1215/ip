package helperbot.parser;


import helperbot.command.AddCommand;
import helperbot.command.CheckCommand;
import helperbot.command.Command;
import helperbot.command.DeleteCommand;
import helperbot.command.ExitCommand;
import helperbot.command.FindCommand;
import helperbot.command.ListCommand;
import helperbot.command.MarkCommand;
import helperbot.command.UpdateCommand;
import helperbot.exception.HelperBotCommandException;

/**
 * Represents a class that parse all the command from user's input to corresponding <code>Command</code>.
 */
public class Parser {

    /**
     * Parses the input from user to a <code>Command</code>.
     * @param message The input from user.
     * @return A <code>Command</code> containing the message.
     * @throws HelperBotCommandException The first word of the input is not a valid command.
     */
    @SuppressWarnings("checkstyle:Indentation")
    public static Command parse(String message) throws HelperBotCommandException {
        String[] splitMessages = message.split(" ");
        String command = splitMessages[0].toLowerCase();

        return switch (command) {
        case "bye" -> new ExitCommand();
        case "check" -> new CheckCommand(splitMessages);
        case "delete" -> new DeleteCommand(splitMessages);
        case "deadline", "event", "todo" -> new AddCommand(command, message);
        case "find" -> new FindCommand(message);
        case "list" -> new ListCommand();
        case "mark" -> new MarkCommand(splitMessages, true);
        case "unmark" -> new MarkCommand(splitMessages, false);
        case "update" -> new UpdateCommand(splitMessages);
        // invalid HelperBot Command
        default -> throw new HelperBotCommandException(splitMessages[0] + " is not found.");
        };
    }
}
