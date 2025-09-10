package helperbot.command;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Response;

/**
 * Represents a command that find any <code>Task</code> whose description contains specific keyword.
 */
public class FindCommand extends Command {

    private final String message;

    /**
     * Generates a <code>FindCommand</code>
     * @param message the input from user.
     */
    public FindCommand(String message) {
        this.message = message;
    }

    @Override
    public String execute(TaskList tasks, Storage storage, Response response) {
        try {
            return response.getTaskListResponse(true,
                    tasks.match(this.message.substring(5).split(" ")).toString());
        } catch (IndexOutOfBoundsException e) {
            return response.getErrorMessage("Invalid Argument: String to be matched is missing.");
        }
    }
}
