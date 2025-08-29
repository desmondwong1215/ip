package helperbot.command;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Ui;

import java.io.IOException;

/**
 * Represent a command which exit from <b>HelperBot</b>.
 */
public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) {
        try {
            storage.write(tasks);
            ui.exit();
        } catch (IOException e) {
            ui.exitWithError("Error: Unable to write helperbot.task to the file.\n");
        }
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
