package helperbot.command;

import helperbot.exception.HelperBotArgumentException;
import helperbot.storage.Storage;
import helperbot.task.*;
import helperbot.ui.Ui;

public class AddCommand extends Command {

    private final String command;
    private final String message;

    public AddCommand(String command, String message) {
        this.command = command;
        this.message = message;
    }

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) {
        try {
            Task task = switch (this.command) {
                case "todo" -> ToDo.fromInput(this.message);
                case "deadline" -> Deadline.fromInput(this.message);
                // helperbot.command is Event
                default -> Event.fromInput(this.message);
            };
            tasks.add(task);
            ui.showAdd(task, tasks.size());
        } catch (HelperBotArgumentException e) {
            // /by is not entered correctly
            ui.showError(e.toString());
        }
    }
}
