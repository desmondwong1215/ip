package helperbot.command;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Response;

import java.util.Arrays;
import java.util.Collections;

/**
 * Represents a command which delete the ith <code>Task</code> in the <code>TaskList</code>.
 */
public class DeleteCommand extends Command {

    private final String[] splitMessages;

    /**
     * Generates a <code>DeleteCommand</code>
     * @param splitMessages the input from user.
     */
    public DeleteCommand(String[] splitMessages) {
        ///  There should be at least one word in the splitMessages
        assert splitMessages.length != 0 : "The splitMessages is empty";

        this.splitMessages = splitMessages;
    }

    @Override
    public String execute(TaskList tasks, Storage storage, Response response) {
        int i = 1;
        int length = this.splitMessages.length;
        Integer[] indices = new Integer[length - 1];
        String[] removedTasks = new String[length - 1];
        try {
            while (i < length) {
                indices[i - 1] = Integer.parseInt(this.splitMessages[1]) - 1;
                i++;
            }
            Arrays.sort(indices, Collections.reverseOrder());
            i = 0;
            for (Integer index: indices) {
                removedTasks[i] = tasks.remove(index).toString();
                i++;
            }
            return response.getDeleteCommandResponse(removedTasks, tasks.size(),
                    Arrays.copyOfRange(this.splitMessages, 1, length));
        } catch (IndexOutOfBoundsException e) {
            if (this.splitMessages.length == 1) {
                return response.getErrorMessage("Invalid Argument: Please enter the index of the HelperBot task after "
                        + this.splitMessages[0] + ".");
            } else {
                return response.getErrorMessage("Invalid Argument: Task " + (indices[i] + 1) + " is not found.");
            }
        } catch (NumberFormatException e) {
            return response.getErrorMessage("Invalid Argument: " + this.splitMessages[i] + " cannot be parsed as an integer.");
        }
    }
}
