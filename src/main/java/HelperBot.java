import java.util.ArrayList;
import java.util.Scanner;

public class HelperBot {

    public static final String LINE = "____________________________________________________________";
    public static final String NAME = "HelperBot";

    private final ArrayList<Task> tasks = new ArrayList<>();

    ///  Greet our user
    public void greet(String name) {
        System.out.println(HelperBot.LINE);
        System.out.printf("Hello! I'm %s.\n", name);
        System.out.println("What can I do for you?");
        System.out.println(HelperBot.LINE);
    }

    ///  add the task to tasks
    public void add(String message) {

        this.tasks.add(new Task(message));

        System.out.println(HelperBot.LINE);
        System.out.println("added: " + message);
        System.out.println(HelperBot.LINE);
    }

    ///  exit the whole program
    public void exit() {
        System.out.println(HelperBot.LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(HelperBot.LINE);
    }

    /// print the task one by one
    public void printTasks() {
        System.out.println(HelperBot.LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < this.tasks.size(); i++) {
            System.out.println((i + 1) + ". " + this.tasks.get(i));
        }
        System.out.println(HelperBot.LINE);
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
            System.out.println(HelperBot.LINE);
            System.out.println(outcome);
            System.out.println(HelperBot.LINE);

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
                this.add(message);

            }
        }
    }

    public static void main(String[] args) {
        HelperBot bot = new HelperBot();
        bot.chat();
    }
}
