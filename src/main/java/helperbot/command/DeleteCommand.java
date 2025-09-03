package helperbot.command;

import helperbot.storage.Storage;
import helperbot.task.Task;
import helperbot.task.TaskList;
import helperbot.ui.Ui;

/**
 * Represents a command which delete the ith <code>Task</code> in the <code>TaskList</code>.
 */
public class DeleteCommand extends Command {

    private final String[] message;

    /**
     * Generate a <code>DeleteCommand</code>
     * @param message the input from user.
     */
    public DeleteCommand(String[] message) {
        this.message = message;
    }

    @Override
    public String execute(TaskList tasks, Storage storage, Ui ui) {
        int index = 0;
        try {
            index = Integer.parseInt(this.message[1]) - 1;
            Task task = tasks.remove(index);
            return ui.showDelete(task, tasks.size(), index);
        } catch (IndexOutOfBoundsException e) {
            if (this.message.length == 1) {
                // the message length < 2, index is not provided
                return ui.showError("Invalid Argument: Please enter the index of the HelperBot task after "
                        + this.message[0] + ".");
            } else {
                // index >= tasks.size(), helperbot.task is not found
                return ui.showError("Invalid Argument: Task " + (index + 1) + " is not found.");
            }
        } catch (NumberFormatException e) {
            // the second input cannot be parsed as an integer
            return ui.showError("Invalid Argument: " + this.message[1] + " cannot be parsed as an integer.");
        }
    }
}
