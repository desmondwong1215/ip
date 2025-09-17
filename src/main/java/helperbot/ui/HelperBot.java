package helperbot.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private static final String DATA_DIRECTORY = "data";
    private static final String FILE_NAME = "HelperBot.txt";
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
            System.err.println(this.response.getErrorMessage(filePath + " is not found."));
            tasks1 = new TaskList();
        } catch (HelperBotFileException e) {
            System.err.println(this.response.getErrorMessage(e.toString()));
            tasks1 = new TaskList();
        }
        this.tasks = tasks1;
    }

    /**
     * Create data/HelperBot.txt if it does not exist.
     * @throws IOException Error occurs when creating data/HelperBot.txt
     */
    public static void createFileIfNotExist() throws IOException {
        Path currentPath = Paths.get(".").toAbsolutePath();
        Path dataPath = currentPath.resolve(DATA_DIRECTORY);
        Path filePath = dataPath.resolve(FILE_NAME);

        File dataDir = dataPath.toFile();
        File file = filePath.toFile();

        if (!dataDir.exists()) {
            boolean success = dataDir.mkdir();
            if (!success) {
                throw new IOException("Failed to create the data directory: " + dataDir.getAbsolutePath());
            }
        }

        if (!file.exists()) {
            boolean success = file.createNewFile();
            if (!success) {
                throw new IOException("Failed to create the file: " + file.getAbsolutePath());
            }
        }
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
            HelperBot.createFileIfNotExist();
            this.storage.write(this.tasks);
        } catch (IOException e) {
            System.err.println(this.response.getExitErrorMessage(e.toString()));
        }
    }

    public static void main(String[] args) {
        new HelperBot("data/HelperBot.txt").chat();
    }
}
