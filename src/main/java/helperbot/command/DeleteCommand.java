package helperbot.command;

import java.util.Arrays;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Response;

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
        int ptr = 1;
        int length = this.splitMessages.length;
        String[] intStrs = Arrays.copyOfRange(this.splitMessages, 1, length);
        Integer[] indices = new Integer[length - 1];
        String[] removedTasks = new String[length - 1];
        try {
            indices = Arrays.stream(intStrs)
                    .map(Integer::parseInt)
                    .map(j -> j - 1)
                    .sorted((a, b) -> b - a)
                    .toList()
                    .toArray(new Integer[0]);
            ptr = length - 2;
            for (Integer index: indices) {
                removedTasks[ptr] = tasks.remove(index).toString();
                ptr--;
            }
            return response.getDeleteCommandResponse(removedTasks, tasks.size(), intStrs);
        } catch (IndexOutOfBoundsException e) {
            if (this.splitMessages.length == 1) {
                return response.getErrorMessage("Invalid Argument: Please enter the index of the HelperBot task after "
                        + this.splitMessages[0] + ".");
            } else {
                return response.getErrorMessage("Invalid Argument: Task " + (indices[ptr] + 1) + " is not found.");
            }
        } catch (NumberFormatException e) {
            return response.getErrorMessage("Invalid Argument: " + this.splitMessages[ptr]
                    + " cannot be parsed as an integer.");
        }
    }
}
