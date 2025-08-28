import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

    ///  exit the whole program
    private void exit() {
        try {
            this.storage.write(this.tasks);
            this.ui.exit();
        } catch (IOException e) {
            this.ui.exitWithError("Error: Unable to write task to the file.\n");
        }
    }

    /// change the task status
    private void changeTaskStatus(String[] message, boolean mark) {
        int index = 0;

        try {
            index = Integer.parseInt(message[1]) - 1;
            if (mark) {
                // mark the task as done
                this.tasks.mark(index);
                this.ui.showMarked(index, this.tasks.get(index));
            } else {
                // mark the task as not done
                this.tasks.unmark(index);
                this.ui.showUnmarked(index, this.tasks.get(index));
            }
        } catch (IndexOutOfBoundsException e) {
            if (message.length == 1) {
                // the message length < 2, index is not provided
                this.ui.showError("Invalid Argument: Please enter the index of the task after " + message[0] + ".");
            } else {
                // index >= tasks.size(), task is not found
                this.ui.showError("Invalid Argument: Task " + (index + 1) + " is not found.");
            }
        } catch (NumberFormatException e) {
            // the second input cannot be parsed as an integer
            this.ui.showError("Invalid Argument: " + message[1] + " cannot be parsed as an integer.");
        }
    }

    /// add a task
    private void addTask(String message, Command command) {
        try {
            Task task = switch (command) {
                case TODO -> ToDo.fromInput(message);
                case DEADLINE -> Deadline.fromInput(message);
                // command is Event
                default -> Event.fromInput(message);
            };
            this.tasks.add(task);
            String outcome = "Got it. I've added this task:\n\t"
                    + task
                    + "\nYou now have "
                    + this.tasks.size()
                    + " tasks in the list.";
            this.ui.print(outcome);
        } catch (HelperBotArgumentException e) {
            // /by is not entered correctly
            this.ui.showError(e.toString());
        }
    }

    /// delete a task
    private void delete(String[] message) {
        int index = 0;
        try {
            index = Integer.parseInt(message[1]) - 1;
            Task task = this.tasks.remove(index);
            String outcome = "Nice! I have removed task "
                    + (index  + 1)
                    + "!\n\t"
                    + task
                    + "\nYou now have "
                    + this.tasks.size()
                    + " tasks in the list.";
            this.ui.print(outcome);
        } catch (IndexOutOfBoundsException e) {
            if (message.length == 1) {
                // the message length < 2, index is not provided
                this.ui.showError("Invalid Argument: Please enter the index of the task after " + message[0] + ".");
            } else {
                // index >= tasks.size(), task is not found
                this.ui.showError("Invalid Argument: Task " + (index + 1) + " is not found.");
            }
        } catch (NumberFormatException e) {
            // the second input cannot be parsed as an integer
            this.ui.showError("Invalid Argument: " + message[1] + " cannot be parsed as an integer.");
        }
    }

    /// find task due on date
    private void findTasks(String[] splitMessage) {
        try {
            LocalDate date = LocalDate.parse(splitMessage[1]);
            this.ui.print(this.tasks.getTaskOnDate(date).toString());
        } catch (DateTimeParseException e) {
            this.ui.showError("Invalid Argument: Please enter the date in YYYY-MM-DD.");
        } catch (IndexOutOfBoundsException e) {
            this.ui.showError("Invalid Argument: Date is missing.");
        }
    }

    /// initialize the chat
    public void chat() {
        // greet the user
        this.ui.greet();

        Scanner scanner = new Scanner(System.in);

        // chat with the user, exit when user enter 'bye'
        label:
        while (true) {
             try {
                System.out.println();
                String message = scanner.nextLine();
                String[] splitMessage = message.split(" ");
                Command command = Command.getCommand(splitMessage[0]);

                 switch (command) {
                     case BYE:
                         // user want to exit
                         this.exit();
                         break label;
                     case LIST:
                         // print all the tasks
                         this.ui.print(this.tasks.toString());
                         break;
                     case MARK:
                         // mark the task as done
                         this.changeTaskStatus(splitMessage, true);
                         break;
                     case UNMARK:
                         // mark the task as not done
                         this.changeTaskStatus(splitMessage, false);
                         break;
                     case TODO:
                     case DEADLINE:
                     case EVENT:
                         // add a deadline
                         this.addTask(message, command);
                         break;
                     case DELETE:
                         // delete the task
                         this.delete(splitMessage);
                         break;
                     case FIND:
                         this.findTasks(splitMessage);
                         break;
                     default:
                         // invalid command
                         throw new HelperBotCommandException(splitMessage[0] + " is not found.");
                 }
            } catch (HelperBotCommandException e) {
                 // catch an invalid command
                 this.ui.showError(e.toString());
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "data/HelperBot.txt";
        HelperBot bot = new HelperBot(filePath);
        bot.chat();
    }
}
