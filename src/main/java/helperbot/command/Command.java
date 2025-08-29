package helperbot.command;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Ui;

/**
 * Represent a command in <b>HelperBot</b>.
 */
public abstract class Command {

    /**
     * Execute the command.
     *
     * @param tasks A list of tasks in HelperBot.
     * @param storage Storage of HelperBot.
     * @param ui Interface of HelperBot.
     */
    public abstract void execute(TaskList tasks, Storage storage, Ui ui);

    /**
     * Check if the command is an <code>ExitCommand</code>.
     * @return <code>true</code> if it is an <code>ExitCommand</code>.
     */
    public boolean isExit() {
        return false;
    }
}