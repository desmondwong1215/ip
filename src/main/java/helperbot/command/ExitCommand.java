package helperbot.command;

import java.io.IOException;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Ui;

/**
 * Represents a command which exit from <b>HelperBot</b>.
 */
public class ExitCommand extends Command {

    @Override
    public String execute(TaskList tasks, Storage storage, Ui ui) {
        try {
            storage.write(tasks);
            return ui.showExitMessage();
        } catch (IOException e) {
            return ui.showExitErrorMessage("Error: Unable to write helperbot.task to the file.\n");
        }
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
