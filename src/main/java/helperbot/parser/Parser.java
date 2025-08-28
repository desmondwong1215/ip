package helperbot.parser;

import helperbot.command.*;
import helperbot.exception.HelperBotCommandException;

public class Parser {

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
