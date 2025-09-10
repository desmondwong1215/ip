package helperbot.command;

import java.io.IOException;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Response;

/**
 * Represents a command which exit from <b>HelperBot</b>.
 */
public class ExitCommand extends Command {

    @Override
    public String execute(TaskList tasks, Storage storage, Response response) {
        try {
            storage.write(tasks);
            return response.getExitMessage();
        } catch (IOException e) {
            return response.getExitErrorMessage("Error: Unable to write helperbot.task to the file.\n");
        }
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
