package helperbot.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import helperbot.command.Command;
import helperbot.exception.HelperBotCommandException;
import helperbot.exception.HelperBotFileException;
import helperbot.parser.Parser;
import helperbot.storage.Storage;
import helperbot.task.TaskList;

/**
 * Represents <b>HelperBot</b>.
 */
public class HelperBot {

    private final TaskList tasks;
    private final Response response;
    private final Storage storage;

    /**
     * Generates the <b>HelperBot</b>
     * @param filePath the file path to the storage file
     */
    public HelperBot(String filePath) {
        TaskList tasks1;
        this.response = new Response();
        this.storage = new Storage(filePath);
        try {
            tasks1 = new TaskList(this.storage.load());
        } catch (FileNotFoundException e) {
            this.response.getErrorMessage(filePath + " is not found.");
            tasks1 = new TaskList();
        } catch (HelperBotFileException e) {
            this.response.getErrorMessage(e.toString());
            tasks1 = new TaskList();
        }
        this.tasks = tasks1;
    }

    /**
     * Initializes the chat.
     */
    public void chat() {
        // greet the user
        this.response.getGreetMessage();

        Scanner scanner = new Scanner(System.in);

        // chat with the user, exit when user enter 'bye'
        while (true) {
            try {
                System.out.println();
                String message = scanner.nextLine();
                Command command = Parser.parse(message);
                command.execute(this.tasks, this.storage, this.response);
                if (command.isExit()) {
                    break;
                }
            } catch (HelperBotCommandException e) {
                // catch an invalid HelperBot.command
                this.response.getErrorMessage(e.toString());
            }
        }

        scanner.close();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parse(input);
            return command.execute(this.tasks, this.storage, this.response);
        } catch (HelperBotCommandException e) {
            // catch an invalid HelperBot.command
            return this.response.getErrorMessage(e.toString());
        }
    }

    /**
     * Greets the user
     */
    public String greet() {
        return this.response.getGreetMessage();
    }

    /**
     * save current <code>TaskList</code> to the HelperBot.txt
     */
    public void saveToFile() {
        try {
            this.storage.write(this.tasks);
            response.getExitMessage();
        } catch (IOException e) {
            response.getExitErrorMessage(e.toString());
        }
    }

    public static void main(String[] args) {
        new HelperBot("./helperbot/storage/data/HelperBot.txt").chat();
    }
}
