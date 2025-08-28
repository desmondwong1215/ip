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
            String outcome = "Got it. I've added this helperbot.task:\n\t"
                    + task
                    + "\nYou now have "
                    + tasks.size()
                    + " tasks in the list.";
            ui.print(outcome);
        } catch (HelperBotArgumentException e) {
            // /by is not entered correctly
            ui.showError(e.toString());
        }
    }
}
