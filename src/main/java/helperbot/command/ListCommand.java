package helperbot.command;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Response;

/**
 * Represents a command which show all the <code>Task</code> in the <code>TaskList</code>.
 */
public class ListCommand extends Command {

    @Override
    public String execute(TaskList tasks, Storage storage, Response response) {
        return response.getTaskListResponse(false, tasks.toString());
    }
}
