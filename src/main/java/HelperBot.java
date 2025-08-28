import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class HelperBot {

    private static final String LINE = "____________________________________________________________";
    private static final String NAME = "HelperBot";

    private final TaskList tasks;

    public HelperBot() {
        this.tasks = new TaskList();
    }

    /// print message to user
    private void print(String message) {
        System.out.println(HelperBot.LINE);
        System.out.println(message);
        System.out.println(HelperBot.LINE);
    }

    ///  Greet our user
    private void greet() {
        this.print("Hello! I'm "
            + HelperBot.NAME
            + ".\nWhat can I do for you?"
        );
    }

    ///  exit the whole program
    private void exit() {
        String errorMessage = Storage.writeToStorage(this.tasks);
        if (errorMessage.isEmpty()) {
            this.print("Bye. Hope to see you again soon!");
        } else {
            this.print(errorMessage + "\nBye. Hope to see you again soon!");
        }

    }

    /// change the task status
    private void changeTaskStatus(String[] message, boolean mark) {
        String outcome = "";
        int index = 0;

        try {
            index = Integer.parseInt(message[1]) - 1;
            if (mark) {
                // mark the task as done
                this.tasks.mark(index);
                outcome = "Nice! I have marked task " + (index + 1) + " as done!\n\t" + this.tasks.get(index);
            } else {
                // mark the task as not done
                this.tasks.unmark(index);
                outcome = "Nice! I have marked task " + (index  + 1) + " as not done yet!\n\t" + this.tasks.get(index);
            }
        } catch (IndexOutOfBoundsException e) {
            if (message.length == 1) {
                // the message length < 2, index is not provided
                outcome = "Invalid Argument: Please enter the index of the task after " + message[0] + ".";
            } else {
                // index >= tasks.size(), task is not found
                outcome = "Invalid Argument: Task " + (index + 1) + " is not found.";
            }
        } catch (NumberFormatException e) {
            // the second input cannot be parsed as an integer
            outcome = "Invalid Argument: " + message[1] + " cannot be parsed as an integer.";
        } finally {
            // print the outcome
            this.print(outcome);
        }
    }

    /// add a task
    private void addTask(String message, Command command) {
        String outcome = "";
        try {
            Task task = switch (command) {
                case TODO -> ToDo.fromInput(message);
                case DEADLINE -> Deadline.fromInput(message);
                // command is Event
                default -> Event.fromInput(message);
            };
            this.tasks.add(task);
            outcome = "Got it. I've added this task:\n\t"
                    + task
                    + "\nYou now have "
                    + this.tasks.size()
                    + " tasks in the list.";
        } catch (HelperBotArgumentException e) {
            // /by is not entered correctly
            outcome = e.toString();
        } finally {
            // print the outcome
            this.print(outcome);
        }
    }

    /// delete a task
    private void delete(String[] message) {
        String outcome = "";
        int index = 0;
        try {
            index = Integer.parseInt(message[1]) - 1;
            Task task = this.tasks.remove(index);
            outcome = "Nice! I have removed task "
                    + (index  + 1)
                    + "!\n\t"
                    + task
                    + "\nYou now have "
                    + this.tasks.size()
                    + " tasks in the list.";
        } catch (IndexOutOfBoundsException e) {
            if (message.length == 1) {
                // the message length < 2, index is not provided
                outcome = "Invalid Argument: Please enter the index of the task after " + message[0] + ".";
            } else {
                // index >= tasks.size(), task is not found
                outcome = "Invalid Argument: Task " + (index + 1) + " is not found.";
            }
        } catch (NumberFormatException e) {
            // the second input cannot be parsed as an integer
            outcome = "Invalid Argument: " + message[1] + " cannot be parsed as an integer.";
        } finally {
            // print the outcome
            this.print(outcome);
        }
    }

    /// find task due on date
    private void findTasks(String[] splitMessage) {
        try {
            LocalDate date = LocalDate.parse(splitMessage[1]);
            this.print(this.tasks.getTaskOnDate(date).toString());
        } catch (DateTimeParseException e) {
            this.print("Invalid Argument: Please enter the date in YYYY-MM-DD.");
        } catch (IndexOutOfBoundsException e) {
            this.print("Invalid Argument: Date is missing.");
        }
    }

    /// initialize the chat
    public void chat() {

        String errorMessage = Storage.loadFromStorage(this.tasks);
        if (!errorMessage.isEmpty()) {
            // the loading of data is unsuccessful
            this.print(errorMessage + "\nBye. Hope to see you again soon!");
            return;
        }

        // greet the user
        this.greet();

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
                         this.print(this.tasks.toString());
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
                 this.print(e.toString());
            }
        }
    }

    public static void main(String[] args) {
        HelperBot bot = new HelperBot();
        bot.chat();
    }
}
