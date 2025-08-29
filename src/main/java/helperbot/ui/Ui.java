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
     * Print a message to the user.
     * @param message The message fromm <b>HelperBot</b> to user.
     */
    public void print(String message) {
        System.out.println(Ui.LINE);
        System.out.println(message);
        System.out.println(Ui.LINE);
    }

    /**
     * Print the information of the marked <code>Task</code>.
     * @param index The index of the <code>Task</code>.
     * @param task <code>Task</code>.
     */
    public void showMarked(int index, Task task) {
        this.print("Nice! I have marked helperbot.task " + (index + 1) + " as done!\n\t" + task);
    }

    /**
     * Print the information of the unmarked <code>Task</code>.
     * @param index The index of the <code>Task</code>.
     * @param task <code>Task</code>.
     */
    public void showUnmarked(int index, Task task) {
        this.print("Nice! I have marked helperbot.task " + (index + 1) + " as not done yet!\n\t" + task);
    }

    /**
     * Show error message.
     * @param message Error message.
     */
    public void showError(String message) {
        this.print("Error!\n" + message);
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
}
