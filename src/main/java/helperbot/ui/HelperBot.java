package helperbot.ui;

import java.io.FileNotFoundException;
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
    private final Ui ui;
    private final Storage storage;

    /**
     * Generate the <b>HelperBot</b>
     * @param filePath the file path to the storage file
     */
    public HelperBot(String filePath) {
        TaskList tasks1;
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            tasks1 = new TaskList(this.storage.load());
        } catch (FileNotFoundException e) {
            this.ui.showError(filePath + " is not found.");
            tasks1 = new TaskList();
        } catch (HelperBotFileException e) {
            this.ui.showError(e.toString());
            tasks1 = new TaskList();
        }
        this.tasks = tasks1;
    }

    /**
     * Initializes the chat.
     */
    public void chat() {
        // greet the user
        this.ui.greet();

        Scanner scanner = new Scanner(System.in);

        // chat with the user, exit when user enter 'bye'
        while (true) {
            try {
                System.out.println();
                String message = scanner.nextLine();
                Command command = Parser.parse(message);
                command.execute(this.tasks, this.storage, this.ui);
                if (command.isExit()) {
                    break;
                }
            } catch (HelperBotCommandException e) {
                // catch an invalid HelperBot.command
                this.ui.showError(e.toString());
            }
        }
    }

    public static void main(String[] args) {
        new HelperBot("./helperbot/storage/data/HelperBot.txt").chat();
    }
}
