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
        this.splitMessages = splitMessages;
        this.isMarked = isMarked;
    }

    @Override
    public String execute(TaskList tasks, Storage storage, Response response) {
        int i = 1;
        int length = this.splitMessages.length;
        Integer[] indices = new Integer[length - 1];
        String[] markedTasks = new String[length - 1];
        try {
            while (i < length) {
                indices[i - 1] = Integer.parseInt(this.splitMessages[i]) - 1;
                i++;
            }
            i = 0;
            if (this.isMarked) {
                // mark the HelperBot task as done
                for (Integer index: indices) {
                    tasks.mark(index);
                    markedTasks[i] = tasks.get(index).toString();
                    i++;
                }
                return response.getMarkCommandResponse(Arrays.copyOfRange(this.splitMessages, 1, length),
                        markedTasks);
            } else {
                // mark the HelperBot task as not done
                // mark the HelperBot task as done
                for (Integer index: indices) {
                    tasks.unmark(index);
                    markedTasks[i] = tasks.get(index).toString();
                    i++;
                }
                return response.getUnmarkCommandResponse(Arrays.copyOfRange(this.splitMessages, 1, length),
                        markedTasks);
            }
        } catch (IndexOutOfBoundsException e) {
            if (this.splitMessages.length == 1) {
                // the message length < 2, index is not provided
                return response.getErrorMessage("Invalid Argument: Please enter the index of the HelperBot task after "
                        + this.splitMessages[0] + ".");
            } else {
                // index >= tasks.size(), HelperBot task is not found
                return response.getErrorMessage("Invalid Argument: Task " + (indices[i] + 1) + " is not found.");
            }
        } catch (NumberFormatException e) {
            // the second input cannot be parsed as an integer
            return response.getErrorMessage("Invalid Argument: " + this.splitMessages[1] + " cannot be parsed as an integer.");
        }
    }
}
