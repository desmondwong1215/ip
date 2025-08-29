package helperbot.command;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a command that find all the <code>Task</code> which due on the date specified (if applicable).
 */
public class CheckCommand extends Command {

    private final String[] message;

    public CheckCommand(String[] message) {
        this.message = message;
    }

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) {
        try {
            LocalDate date = LocalDate.parse(this.message[1]);
            ui.printTaskList(true, tasks.getTaskOnDate(date).toString());
        } catch (DateTimeParseException e) {
            ui.showError("Invalid Argument: Please enter the date in YYYY-MM-DD.");
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Invalid Argument: Date is missing.");
        }
    }
}
