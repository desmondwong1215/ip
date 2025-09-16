package helperbot.ui;

import helperbot.task.Task;

/**
 * Represents the interface of <b>HelperBot</b>.
 */
public class Response {

    private static final String NAME = "HelperBot";

    /**
     * Greets the user.
     * @return The message.
     */
    public String getGreetMessage() {
        return "Hello! I'm "
                + Response.NAME
                + ".\nWhat can I do for you?";
    }

    /**
     * Prints the information of the marked <code>Task</code>.
     * @param isMarked True if it is a MarkCommand, false if it is a UnmarkCommand.
     * @param indices The index of the <code>Task</code>s.
     * @param markedTasks The string representation of <code>Task</code>.
     * @return The message.
     */
    public String getMarkCommandResponse(boolean isMarked, String[] indices, String[] markedTasks) {
        return "Nice! I have marked HelperBot task "
                + String.join(", ", indices)
                + (isMarked ? " as done!\n\t" : " as not done yet!\n\t")
                + String.join("\n\t", markedTasks);
    }

    /**
     * Shows error message.
     * @param message Error message.
     * @return The message.
     */
    public String getErrorMessage(String message) {
        return "Error!\n" + message;
    }

    /**
     * Shows the outcome of addition
     * @param task <code>Task</code> added.
     * @param size The size of <code>TaskList</code> after addition.
     * @return The message.
     */
    public String getAddCommandResponse(Task task, int size) {
        return "Got it. I've added this HelperBot task:\n\t"
                + task
                + "\nYou now have "
                + size
                + " tasks in the list.";
    }

    /**
     * Shows the outcome of deletion
     * @param removedTasks <code>Task</code> deleted.
     * @param size The size of <code>TaskList</code> after deletion.
     * @param indices The index of <code>Task</code>.
     * @return The message.
     */
    public String getDeleteCommandResponse(String[] removedTasks, int size, String[] indices) {
        return "Nice! I have removed HelperBot task "
                + String.join(", ", indices)
                + "!\n\t"
                + String.join("\n\t", removedTasks)
                + "\nYou now have "
                + size
                + " tasks in the list.";
    }

    /**
     * Prints <code>TaskList</code>.
     * @param isMatched <code>true</code> if HelperBot is matching <code>Task</code>.
     * @param taskList String representation of <code>TaskList</code>.
     * @return The message.
     */
    public String getTaskListResponse(boolean isMatched, String taskList) {
        if (taskList.isEmpty()) {
            return getEmptyTaskListResponse(isMatched);
        }
        return "Here are the " + (isMatched ? "matching " : "") + "tasks in your list:" + taskList;
    }

    /**
     * Exits the program.
     * @return The message.
     */
    public String getExitMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Prints error message if error occurs when exiting the program.
     * @param message Error message.
     * @return The message.
     */
    public String getExitErrorMessage(String message) {
        return message + "\nBye. Hope to see you again soon!";
    }

    /**
     * Prints the result of update command.
     * @param index The index of the task.
     * @param task The task updated.
     * @return The result of the command.
     */
    public String getUpdateOutput(int index, Task task) {
        return "I have updated HelperBot task "
                + index
                + "!\n\t"
                + task.toString();
    }

    /**
     * Prints empty task message when no existing task matches the condition.
     * @param isMatched True if the task list is empty after matching.
     * @return The message.
     */
    private String getEmptyTaskListResponse(boolean isMatched) {
        return "You do not have any " + (isMatched ? "matching " : "") + "HelperBot task.";
    }

}
