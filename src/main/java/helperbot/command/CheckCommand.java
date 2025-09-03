package helperbot.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Ui;

/**
 * Represents a command that find all the <code>Task</code> which due on the date specified (if applicable).
 */
public class CheckCommand extends Command {

    private final String[] message;

    /**
     * Generate a <code>CheckCommand</code>
     * @param message the input from user
     */
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
