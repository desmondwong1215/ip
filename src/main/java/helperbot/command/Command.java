package helperbot.command;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Ui;

/**
 * Represents a command in <b>HelperBot</b>.
 */
public abstract class Command {

    /**
     * Executes the command.
     *
     * @param tasks A list of tasks in HelperBot.
     * @param storage Storage of HelperBot.
     * @param ui Interface of HelperBot.
     */
    public abstract void execute(TaskList tasks, Storage storage, Ui ui);

    /**
     * Checks if the command is an <code>ExitCommand</code>.
     * @return <code>true</code> if it is an <code>ExitCommand</code>.
     */
    public boolean isExit() {
        return false;
    }
}