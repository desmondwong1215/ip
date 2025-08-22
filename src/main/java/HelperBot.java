import java.util.ArrayList;
import java.util.Scanner;

public class HelperBot {

    public static final String LINE = "____________________________________________________________";
    public static final String NAME = "HelperBot";

    private final ArrayList<Task> tasks = new ArrayList<>();

    public void print(String message) {
        System.out.println(HelperBot.LINE);
        System.out.println(message);
        System.out.println(HelperBot.LINE);
    }

    ///  Greet our user
    public void greet(String name) {
        this.print("Hello! I'm "
            + name
            + ".\nWhat can I do for you?"
        );
    }

    ///  add the task to tasks
    public void addTask(String message) {
        String outcome = "";

        try {
            Task task;
            if (message.toLowerCase().startsWith("todo ")) {
                // create to-do
                task = new ToDo(message.substring(5));

            } else if (message.toLowerCase().startsWith("deadline ")) {
                // create deadline
                int byIndex = message.indexOf("/by ");
                if (byIndex == -1) {
                    throw new IllegalArgumentException();
                }
                task = new Deadline(message.substring(9, byIndex),
                        message.substring(byIndex + 4));

            } else if (message.toLowerCase().startsWith("event ")) {
                // create event
                int fromIndex = message.indexOf("/from ");
                int toIndex = message.indexOf("/to ");
                if (fromIndex == -1 || toIndex == -1) {
                    throw new IllegalArgumentException();
                }
                task = new Event(message.substring(6, fromIndex),
                        message.substring(fromIndex + 6, toIndex),
                        message.substring(toIndex + 4));

            } else {
                task = new Task(message);
            }

            this.tasks.add(task);
            outcome = "Got it. I've added this task:\n\t"
                + task
                + "\nYou now have "
                + this.tasks.size()
                + " tasks in the list.";
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            // catch invalid input
            outcome = "Invalid command: Wrong format for task.";
        } finally {
            // print the outcome
            this.print(outcome);
        }
    }

    ///  exit the whole program
    public void exit() {
        this.print("Bye. Hope to see you again soon!");
    }

    /// print the task one by one
    public void printTasks() {

        if (this.tasks.isEmpty()) {
            this.print("You do not have any task.");
            return;
        }

        String tasks = "Here are the tasks in your list:";
        for (int i = 0; i < this.tasks.size(); i++) {
            tasks = tasks.concat("\n" + (i + 1) + ". " + this.tasks.get(i));
        }
        this.print(tasks);
    }

    /// change the task status
    public void changeTaskStatus(String message) {
        String[] splitMessages = message.split(" ");
        String outcome = "";
        int index = 0;

        try {

            index = Integer.parseInt(splitMessages[1]) - 1;
            if (splitMessages[0].equals("mark")) {
                // mark the task as done
                this.tasks.get(index).markAsDone();
                outcome = "Nice! I have marked this task as done!\n\t" + this.tasks.get(index);

            } else {
                // mark the task as not done
                this.tasks.get(index).markAsNotDone();
                outcome = "Nice! I have marked this task as not done yet!\n\t" + this.tasks.get(index);

            }
        } catch (IndexOutOfBoundsException e) {
            if (splitMessages.length == 1) {
                // the message length < 2, index is not provided
                outcome = "Invalid command: Please enter the index of the task after " + splitMessages[0] + ".";

            } else {
                // index >= tasks.size(), task is not found
                outcome = "Invalid command: Task " + (index + 1) + " is not found";

            }
        } catch (NumberFormatException e) {
            // the second input cannot be parsed as an integer
            outcome = "Invalid command: " + splitMessages[1] + " cannot be parsed as an integer.";

        } finally {
            // print the outcome
            this.print(outcome);

        }
    }

    public void chat() {
        // greet the user
        this.greet(HelperBot.NAME);

        Scanner scanner = new Scanner(System.in);

        // chat with the user, exit when user enter 'bye'
        while (true) {
            System.out.println();
            String message = scanner.nextLine();

            if (message.equalsIgnoreCase("bye")) {
                // user want to exit
                this.exit();
                break;

            } else if (message.equalsIgnoreCase("list")) {
                // print all the tasks
                this.printTasks();

            } else if (message.toLowerCase().startsWith("mark") ||
                    message.toLowerCase().startsWith("unmark")) {
                // change the task status
                this.changeTaskStatus(message.toLowerCase());

            } else {
                // repeat the message entered by user
                this.addTask(message);

            }
        }
    }

    public static void main(String[] args) {
        HelperBot bot = new HelperBot();
        bot.chat();
    }
}
