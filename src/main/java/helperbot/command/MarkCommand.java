package helperbot.command;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Ui;

/**
 * Represent a command that change the status of a <code>Task</code>.
 * <p>
 * A <code>Task</code> can be marked as <i>Done</i> and <i>Not Done</i>.
 */
public class MarkCommand extends Command {

    private final String[] message;
    private final boolean mark;

    /**
     * Generate a <code>MarkCommand</code>
     * @param message the input from user.
     * @param mark true if the <code>Task</code> is done, else false.
     */
    public MarkCommand(String[] message, boolean mark) {
        this.message = message;
        this.mark = mark;
    }

    @Override
    public String execute(TaskList tasks, Storage storage, Ui ui) {
        int index = 0;

        try {
            index = Integer.parseInt(this.message[1]) - 1;
            if (this.mark) {
                // mark the helperbot.task as done
                tasks.mark(index);
                return ui.showMarked(index, tasks.get(index));
            } else {
                // mark the helperbot.task as not done
                tasks.unmark(index);
                return ui.showUnmarked(index, tasks.get(index));
            }
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
