package helperbot.ui;

import helperbot.task.Task;

public class Ui {

    private static final String LINE = "____________________________________________________________";
    private static final String NAME = "HelperBot";

    public void greet() {
        this.print("Hello! I'm "
                + Ui.NAME
                + ".\nWhat can I do for you?"
        );
    }

    private void print(String message) {
        System.out.println(Ui.LINE);
        System.out.println(message);
        System.out.println(Ui.LINE);
    }

    public void showMarked(int index, Task task) {
        this.print("Nice! I have marked HelperBot task " + (index + 1) + " as done!\n\t" + task);
    }

    public void showUnmarked(int index, Task task) {
        this.print("Nice! I have marked HelperBot task " + (index + 1) + " as not done yet!\n\t" + task);
    }

    public void showError(String message) {
        this.print("Error!\n" + message);
    }

    public void showAdd(Task task, int size) {
        this.print("Got it. I've added this HelperBot task:\n\t"
                + task
                + "\nYou now have "
                + size
                + " tasks in the list.");
    }

    public void showDelete(Task task, int size, int index) {
        this.print("Nice! I have removed HelperBot task "
                + (index  + 1)
                + "!\n\t"
                + task
                + "\nYou now have "
                + size
                + " tasks in the list.");
    }

    private void printEmptyTaskList(boolean isMatched) {
        this.print("You do not have any " + (isMatched ? "matching " : "") + "HelperBot task.");
    }

    public void printTaskList(boolean isMatched, String taskList) {
        if (taskList.isEmpty()) {
            printEmptyTaskList(isMatched);
            return;
        }
        this.print("Here are the "  + (isMatched ? "matching " : "") + "tasks in your list:" + taskList);
    }

    public void exit() {
        this.print("Bye. Hope to see you again soon!");
    }

    public void exitWithError(String message) {
        this.print(message + "\nBye. Hope to see you again soon!");
    }
}
