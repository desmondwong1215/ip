package helperbot.parser;

import helperbot.command.*;
import helperbot.exception.HelperBotCommandException;

public class Parser {

    public static Command parse(String message) throws HelperBotCommandException {
        String[] splitMessage = message.split(" ");
        String command = splitMessage[0].toLowerCase();

         return switch (command) {
             case "bye" -> new ExitCommand();
             case "check" -> new CheckCommand(splitMessage);
             case "delete" -> new DeleteCommand(splitMessage);
             case "find" -> new FindCommand(message);
             case "list" -> new ListCommand();
             case "mark" -> new MarkCommand(splitMessage, true);
             case "todo", "deadline", "event" -> new AddCommand(command, message);
             case "unmark" -> new MarkCommand(splitMessage, false);
             // invalid helperbot.command
             default -> throw new HelperBotCommandException(splitMessage[0] + " is not found.");
         };
    }
}
