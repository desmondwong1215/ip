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

    public void print(String message) {
        System.out.println(Ui.LINE);
        System.out.println(message);
        System.out.println(Ui.LINE);
    }

    public void showMarked(int index, Task task) {
        this.print("Nice! I have marked helperbot.task " + (index + 1) + " as done!\n\t" + task);
    }

    public void showUnmarked(int index, Task task) {
        this.print("Nice! I have marked helperbot.task " + (index + 1) + " as not done yet!\n\t" + task);
    }

    public void showError(String message) {
        this.print("Error!\n" + message);
    }

    public void exit() {
        this.print("Bye. Hope to see you again soon!");
    }

    public void exitWithError(String message) {
        this.print(message + "\nBye. Hope to see you again soon!");
    }
}
