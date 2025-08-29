package helperbot.command;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Ui;

/**
 * Represent a command which show all the <code>Task</code> in the <code>TaskList</code>.
 */
public class ListCommand extends Command {

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) {
        ui.printTaskList(false, tasks.toString());
    }
}
