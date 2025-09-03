package helperbot.ui;

import helperbot.task.Task;

/**
 * Represents the interface of <b>HelperBot</b>.
 */
public class Ui {

    private static final String LINE = "____________________________________________________________";
    private static final String NAME = "HelperBot";

    /**
     * Greets the user.
     * @return The message.
     */
    public String greet() {
        return this.print("Hello! I'm "
                + Ui.NAME
                + ".\nWhat can I do for you?"
        );
    }

    /**
     * Prints the information of the marked <code>Task</code>.
     * @param index The index of the <code>Task</code>.
     * @param task <code>Task</code>.
     * @return The message.
     */
    public String showMarked(int index, Task task) {
        return this.print("Nice! I have marked HelperBot task " + (index + 1) + " as done!\n\t" + task);
    }

    /**
     * Prints the information of the unmarked <code>Task</code>.
     * @param index The index of the <code>Task</code>.
     * @param task <code>Task</code>.
     * @return The message.
     */
    public String showUnmarked(int index, Task task) {
        return this.print("Nice! I have marked HelperBot task " + (index + 1) + " as not done yet!\n\t" + task);
    }

    /**
     * Shows error message.
     * @param message Error message.
     * @return The message.
     */
    public String showError(String message) {
        return this.print("Error!\n" + message);
    }

    /**
     * Shows the outcome of addition
     * @param task <code>Task</code> added.
     * @param size The size of <code>TaskList</code> after addition.
     * @return The message.
     */
    public String showAdd(Task task, int size) {
        return this.print("Got it. I've added this HelperBot task:\n\t"
                + task
                + "\nYou now have "
                + size
                + " tasks in the list.");
    }

    /**
     * Shows the outcome of deletion
     * @param task <code>Task</code> deleted.
     * @param size The size of <code>TaskList</code> after deletion.
     * @param index The index of <code>Task</code>.
     * @return The message.
     */
    public String showDelete(Task task, int size, int index) {
        return this.print("Nice! I have removed HelperBot task "
                + (index + 1)
                + "!\n\t"
                + task
                + "\nYou now have "
                + size
                + " tasks in the list.");
    }

    /**
     * Prints <code>TaskList</code>.
     * @param isMatched <code>true</code> if HelperBot is matching <code>Task</code>.
     * @param taskList String representation of <code>TaskList</code>.
     */
    public String printTaskList(boolean isMatched, String taskList) {
        if (taskList.isEmpty()) {
            return printEmptyTaskList(isMatched);
        }
        return this.print("Here are the " + (isMatched ? "matching " : "") + "tasks in your list:" + taskList);
    }

    /**
     * Exits the program.
     * @return The message.
     */
    public String exit() {
        return this.print("Bye. Hope to see you again soon!");
    }

    /**
     * Error occurs when exiting the program.
     * @param message Error message.
     * @return The message.
     */
    public String exitWithError(String message) {
        return this.print(message + "\nBye. Hope to see you again soon!");
    }


    /**
     * Error occurs when exiting the program.
     * @param isMatched True if empty task list after matching.
     * @return The message.
     */
    private String printEmptyTaskList(boolean isMatched) {
        return this.print("You do not have any " + (isMatched ? "matching " : "") + "HelperBot task.");
    }

    /**
     * Print and return the message
     * @param message Output to the user.
     * @return The message.
     */
    private String print(String message) {
        System.out.println(Ui.LINE);
        System.out.println(message);
        System.out.println(Ui.LINE);
        return message;
    }
}
