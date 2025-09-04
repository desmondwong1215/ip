package helperbot.command;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Ui;

/**
 * Represents a command that change the status of a <code>Task</code>.
 * <p>
 * A <code>Task</code> can be marked as <i>Done</i> and <i>Not Done</i>.
 */
public class MarkCommand extends Command {

    private final String[] splitMessages;
    private final boolean isMarked;

    /**
     * Generates a <code>MarkCommand</code>
     * @param message the input from user.
     * @param isMarked true if the <code>Task</code> is done, else false.
     */
    public MarkCommand(String[] message, boolean isMarked) {
        this.splitMessages = message;
        this.isMarked = isMarked;
    }

    @Override
    public String execute(TaskList tasks, Storage storage, Ui ui) {
        int index = 0;

        try {
            index = Integer.parseInt(this.splitMessages[1]) - 1;
            if (this.isMarked) {
                // mark the HelperBot task as done
                tasks.mark(index);
                return ui.showOutputOfMarkCommand(index, tasks.get(index));
            } else {
                // mark the HelperBot task as not done
                tasks.unmark(index);
                return ui.showOutputOfUnmarkCommand(index, tasks.get(index));
            }
        } catch (IndexOutOfBoundsException e) {
            if (this.splitMessages.length == 1) {
                // the message length < 2, index is not provided
                return ui.showErrorMessage("Invalid Argument: Please enter the index of the HelperBot task after "
                        + this.splitMessages[0] + ".");
            } else {
                // index >= tasks.size(), HelperBot task is not found
                return ui.showErrorMessage("Invalid Argument: Task " + (index + 1) + " is not found.");
            }
        } catch (NumberFormatException e) {
            // the second input cannot be parsed as an integer
            return ui.showErrorMessage("Invalid Argument: " + this.splitMessages[1] + " cannot be parsed as an integer.");
        }
    }
}
