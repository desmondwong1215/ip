package helperbot.command;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Response;

import java.util.Arrays;

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
     * @param splitMessages the input from user.
     * @param isMarked true if the <code>Task</code> is done, else false.
     */
    public MarkCommand(String[] splitMessages, boolean isMarked) {
        ///  There should be at least one word in the splitMessages
        assert splitMessages.length != 0 : "The splitMessages is empty";

        this.splitMessages = splitMessages;
        this.isMarked = isMarked;
    }

    @Override
    public String execute(TaskList tasks, Storage storage, Response response) {
        ///  splitMessages[0] is the command, so the arguments start from index 1.
        int ptr = 1;
        int length = this.splitMessages.length;
        Integer[] indices = new Integer[length - 1];
        String[] markedTasks = new String[length - 1];
        try {
            while (ptr < length) {
                indices[ptr - 1] = Integer.parseInt(this.splitMessages[ptr]) - 1;
                ptr++;
            }
            ptr = 0;
            this.markTasks(tasks, indices, markedTasks);
            return response.getMarkCommandResponse(this.isMarked,
                    Arrays.copyOfRange(this.splitMessages, 1, length), markedTasks);
        } catch (IndexOutOfBoundsException e) {
            if (this.splitMessages.length == 1) {
                return response.getErrorMessage("Invalid Argument: Please enter the index of the HelperBot task after "
                        + this.splitMessages[0] + ".");
            } else {
                return response.getErrorMessage("Invalid Argument: Task " + (indices[ptr] + 1) + " is not found.");
            }
        } catch (NumberFormatException e) {
            return response.getErrorMessage("Invalid Argument: " + this.splitMessages[1]
                    + " cannot be parsed as an integer.");
        }
    }

    private void markTasks(TaskList tasks, Integer[] indices, String[] markedTasks) {
        int ptr = 0;
        for (Integer index: indices) {
            if (this.isMarked) {
                tasks.mark(index);
            } else {
                tasks.unmark(index);
            }
            markedTasks[ptr] = tasks.get(index).toString();
            ptr++;
        }
    }
}
