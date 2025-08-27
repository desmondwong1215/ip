import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HelperBot {

    private static final String LINE = "____________________________________________________________";
    private static final String NAME = "HelperBot";
    private static final String SRC = "data/HelperBot.txt";

    private final ArrayList<Task> tasks = new ArrayList<>();

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
        writeToFile();
        this.print("Bye. Hope to see you again soon!");
    }

    /// print the task one by one
    private void printTasks() {
        if (this.tasks.isEmpty()) {
            this.print("You do not have any task.");
            return;
        }

        StringBuilder tasks = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < this.tasks.size(); i++) {
            tasks. append("\n").append(i + 1).append(". ").append(this.tasks.get(i));
        }
        this.print(tasks.toString());
    }

    /// change the task status
    private void changeTaskStatus(String[] message, boolean mark) {
        String outcome = "";
        int index = 0;

        try {
            index = Integer.parseInt(message[1]) - 1;
            if (mark) {
                // mark the task as done
                this.tasks.get(index).markAsDone();
                outcome = "Nice! I have marked task " + (index + 1) + " as done!\n\t" + this.tasks.get(index);
            } else {
                // mark the task as not done
                this.tasks.get(index).markAsNotDone();
                outcome = "Nice! I have marked task " + (index  + 1) + " as not done yet!\n\t" + this.tasks.get(index);
            }
        } catch (IndexOutOfBoundsException e) {
            if (message.length == 1) {
                // the message length < 2, index is not provided
                outcome = "Invalid Argument: Please enter the index of the task after " + message[0] + ".";
            } else {
                // index >= tasks.size(), task is not found
                outcome = "Invalid Argument: Task " + (index + 1) + " is not found";
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
                outcome = "Invalid Argument: Task " + (index + 1) + " is not found";
            }
        } catch (NumberFormatException e) {
            // the second input cannot be parsed as an integer
            outcome = "Invalid Argument: " + message[1] + " cannot be parsed as an integer.";
        } finally {
            // print the outcome
            this.print(outcome);
        }
    }

    /// load data
    private boolean loadData() {
        boolean isSuccess = true;
        try {
            File file = new File(HelperBot.SRC);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                this.tasks.add(Task.of(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            isSuccess = false;
            this.print(HelperBot.SRC + " is not found.");
            exit();
        } catch (HelperBotFileException e) {
            isSuccess = false;
            this.print(e.toString());
            exit();
        }

        return isSuccess;
    }

    ///  write the data back to the file
    private void writeToFile() {
        try {
            FileWriter fw = new FileWriter(HelperBot.SRC);
            for (Task task: this.tasks) {
                fw.write(task.toStrInFile() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            this.print("Error: Unable to write task to the file");
        }
    }

    /// initialize the chat
    public void chat() {

        if (!loadData()) {
            // the loading of data is unsuccessful
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
                         this.printTasks();
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
