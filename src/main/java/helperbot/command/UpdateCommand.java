package helperbot.command;

import java.util.Arrays;

import helperbot.exception.HelperBotArgumentException;
import helperbot.storage.Storage;
import helperbot.task.Task;
import helperbot.task.TaskList;
import helperbot.ui.Response;

/**
 * Represents a command that update the content of a <code>Task</code>.
 */
public class UpdateCommand extends Command {

    private final String[] splitMessages;

    public UpdateCommand(String[] splitMessages) {
        this.splitMessages = splitMessages;
    }

    @Override
    public String execute(TaskList tasks, Storage storage, Response response) {
        int taskIndex = -1;
        try {
            taskIndex = Integer.parseInt(splitMessages[1]);
            Task task = tasks.get(taskIndex - 1).update(
                    String.join(" ",
                            Arrays.copyOfRange(this.splitMessages, 2, this.splitMessages.length)).trim());
            tasks.set(taskIndex - 1, task);
            return response.getUpdateOutput(taskIndex, task);
        } catch (IndexOutOfBoundsException e) {
            return response.getErrorMessage("Invalid Argument: " + (this.splitMessages.length == 1
                    ? "Task index is not provided"
                    : "Task " + taskIndex + " is not found."));
        } catch (NumberFormatException e) {
            return response.getErrorMessage("Invalid Argument: " + this.splitMessages[1]
                    + " cannot be parsed as an integer.");
        } catch (HelperBotArgumentException e) {
            return response.getErrorMessage(e.toString());
        }
    }
}
