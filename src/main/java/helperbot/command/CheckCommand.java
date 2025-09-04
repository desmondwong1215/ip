package helperbot.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Ui;

/**
 * Represents a command that find all the <code>Task</code> which due on the date specified (if applicable).
 */
public class CheckCommand extends Command {

    private final String[] splitMessages;

    /**
     * Generate a <code>CheckCommand</code>
     * @param splitMessages the input from user
     */
    public CheckCommand(String[] splitMessages) {
        this.splitMessages = splitMessages;
    }

    @Override
    public String execute(TaskList tasks, Storage storage, Ui ui) {
        try {
            LocalDate[] dates = new LocalDate[this.splitMessages.length - 1];
            for (int i = 1; i < this.splitMessages.length; i++) {
                dates[i - 1] = LocalDate.parse(this.splitMessages[i]);
            }
            return ui.printTaskList(true, tasks.getTaskOnDate(dates).toString());
        } catch (DateTimeParseException e) {
            return ui.showErrorMessage("Invalid Argument: Please enter the date in YYYY-MM-DD.");
        } catch (IndexOutOfBoundsException e) {
            return ui.showErrorMessage("Invalid Argument: Date is missing.");
        }
    }
}
