package helperbot.command;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Ui;

public class FindCommand extends Command {

    private final String message;

    public FindCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) {
        try {
            ui.printTaskList(true, tasks.match(this.message.substring(5)).toString());
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Invalid Argument: String to be matched is missing.");
        }
    }
}
