import java.util.ArrayList;
import java.util.Scanner;

public class HelperBot {

    private static final String LINE = "____________________________________________________________";
    private static final String NAME = "HelperBot";

    private final ArrayList<Task> tasks = new ArrayList<>();

    private void print(String message) {
        System.out.println(HelperBot.LINE);
        System.out.println(message);
        System.out.println(HelperBot.LINE);
    }

    ///  Greet our user
    private void greet(String name) {
        this.print("Hello! I'm "
            + name
            + ".\nWhat can I do for you?"
        );
    }

    ///  exit the whole program
    private void exit() {
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

    ///  add a to-do
    private void addToDo(String message) {
        ToDo task = new ToDo(message.substring(5).trim());
        this.tasks.add(task);
        String outcome = "Got it. I've added this task:\n\t"
                + task
                + "\nYou now have "
                + this.tasks.size()
                + " tasks in the list.";
        this.print(outcome);
    }

    /// add a deadline
    private void addDeadline(String message) {

        String outcome = "";

        try {
            int byIndex = message.indexOf("/by ");
            if (byIndex == -1) {
                throw new HelperBotArgumentException("Please enter the deadline after /by");
            }
            Deadline task = new Deadline(message.substring(9, byIndex).trim(),
                    message.substring(byIndex + 4).trim());
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

    ///  add an event
    private void addEvent(String message) {

        String outcome = "";

        try {
            Event task = getEvent(message);
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

    private static Event getEvent(String message) {
        int fromIndex = message.indexOf("/from ");
        int toIndex = message.indexOf("/to ");
        if (fromIndex == -1 || toIndex == -1) {
            throw new HelperBotArgumentException("Please enter start date and end data after " +
                    "/from and /to respectively");
        } else if (fromIndex > toIndex) {
            throw new HelperBotArgumentException("Please enter /from before entering /to");
        }
        return new Event(message.substring(6, fromIndex).trim(),
                message.substring(fromIndex + 6, toIndex).trim(),
                message.substring(toIndex + 4).trim());
    }

    public void chat() {
        // greet the user
        this.greet(HelperBot.NAME);

        Scanner scanner = new Scanner(System.in);

        // chat with the user, exit when user enter 'bye'
        label:
        while (true) {
             try {
                System.out.println();
                String message = scanner.nextLine();
                String[] splitMessage = message.split(" ");
                String command = splitMessage[0].toLowerCase();

                 switch (command) {
                     case "bye":
                         // user want to exit
                         this.exit();
                         break label;
                     case "list":
                         // print all the tasks
                         this.printTasks();
                         break;
                     case "mark":
                         // mark the task as done
                         this.changeTaskStatus(splitMessage, true);
                         break;
                     case "unmark":
                         // mark the task as not done
                         this.changeTaskStatus(splitMessage, false);
                         break;
                     case "todo":
                         // add a to-do
                         this.addToDo(message);
                         break;
                     case "deadline":
                         // add a deadline
                         this.addDeadline(message);
                         break;
                     case "event":
                         // add a command
                         this.addEvent(message);
                         break;
                     default:
                         // invalid command
                         throw new HelperBotCommandException(command + " is not found.");
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
