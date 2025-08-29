package helperbot.ui;

import helperbot.task.Task;

/**
 * Represent the interface of <b>HelperBot</b>.
 */
public class Ui {

    private static final String LINE = "____________________________________________________________";
    private static final String NAME = "HelperBot";

    /**
     * Greet the user.
     */
    public void greet() {
        this.print("Hello! I'm "
                + Ui.NAME
                + ".\nWhat can I do for you?"
        );
    }

    /**
     * Print the information of the marked <code>Task</code>.
     * @param index The index of the <code>Task</code>.
     * @param task <code>Task</code>.
     */
    public void showMarked(int index, Task task) {
        this.print("Nice! I have marked HelperBot task " + (index + 1) + " as done!\n\t" + task);
    }

    /**
     * Print the information of the unmarked <code>Task</code>.
     * @param index The index of the <code>Task</code>.
     * @param task <code>Task</code>.
     */
    public void showUnmarked(int index, Task task) {
        this.print("Nice! I have marked HelperBot task " + (index + 1) + " as not done yet!\n\t" + task);
    }

    /**
     * Show error message.
     * @param message Error message.
     */
    public void showError(String message) {
        this.print("Error!\n" + message);
    }

    /**
     * Show the outcome of addition
     * @param task <code>Task</code> added.
     * @param size The size of <code>TaskList</code> after addition.
     */
    public void showAdd(Task task, int size) {
        this.print("Got it. I've added this HelperBot task:\n\t"
                + task
                + "\nYou now have "
                + size
                + " tasks in the list.");
    }

    /**
     * Show the outcome of deletion
     * @param task <code>Task</code> deleted.
     * @param size The size of <code>TaskList</code> after deletion.
     * @param index The index of <code>Task</code>.
     */
    public void showDelete(Task task, int size, int index) {
        this.print("Nice! I have removed HelperBot task "
                + (index  + 1)
                + "!\n\t"
                + task
                + "\nYou now have "
                + size
                + " tasks in the list.");
    }

    /**
     * Print <code>TaskList</code>.
     * @param isMatched <code>true</code> if HelperBot is matching <code>Task</code>.
     * @param taskList String representation of <code>TaskList</code>.
     */
    public void printTaskList(boolean isMatched, String taskList) {
        if (taskList.isEmpty()) {
            printEmptyTaskList(isMatched);
            return;
        }
        this.print("Here are the "  + (isMatched ? "matching " : "") + "tasks in your list:" + taskList);
    }

    /**
     * Exit the program.
     */
    public void exit() {
        this.print("Bye. Hope to see you again soon!");
    }

    /**
     * Error occurs when exiting the program.
     * @param message Error message.
     */
    public void exitWithError(String message) {
        this.print(message + "\nBye. Hope to see you again soon!");
    }


    private void printEmptyTaskList(boolean isMatched) {
        this.print("You do not have any " + (isMatched ? "matching " : "") + "HelperBot task.");
    }

    private void print(String message) {
        System.out.println(Ui.LINE);
        System.out.println(message);
        System.out.println(Ui.LINE);
    }
}
