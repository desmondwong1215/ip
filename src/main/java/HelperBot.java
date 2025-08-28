import java.io.FileNotFoundException;
import java.util.Scanner;

public class HelperBot {

    private final TaskList tasks;
    private final Ui ui;
    private final Storage storage;

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

    /// initialize the chat
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
                 // catch an invalid command
                 this.ui.showError(e.toString());
            }
        }
    }

    public static void main(String[] args) {
        new HelperBot("data/HelperBot.txt").chat();
    }
}
