package helperbot.command;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Ui;

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
    public String execute(TaskList tasks, Storage storage, Ui ui) {
        try {
            return ui.printTaskList(true, tasks.match(this.message.substring(5)).toString());
        } catch (IndexOutOfBoundsException e) {
            return ui.showErrorMessage("Invalid Argument: String to be matched is missing.");
        }
    }
}
