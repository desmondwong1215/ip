package helperbot.command;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CheckCommand extends Command {

    private final String[] message;

    public CheckCommand(String[] message) {
        this.message = message;
    }

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) {
        try {
            LocalDate date = LocalDate.parse(this.message[1]);
            ui.print(tasks.getTaskOnDate(date).toString());
        } catch (DateTimeParseException e) {
            ui.showError("Invalid Argument: Please enter the date in YYYY-MM-DD.");
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Invalid Argument: Date is missing.");
        }
    }
}
