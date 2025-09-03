package helperbot.command;


import helperbot.exception.HelperBotArgumentException;
import helperbot.storage.Storage;
import helperbot.task.Deadline;
import helperbot.task.Event;
import helperbot.task.Task;
import helperbot.task.TaskList;
import helperbot.task.ToDo;
import helperbot.ui.Ui;

/**
 * Represents a <code>Command</code> which add a new <code>Task</code> to the <code>Tasklist</code>.
 * <p>
 * Based on the first key word in the message, it can add <code>ToDo</code>, <code>Deadline</code>, <code>Event</code>
 * accordingly.
 */
public class AddCommand extends Command {

    private final String command;
    private final String message;

    /**
     * Generate a <code>AddCommand</code>
     * @param command the type of the task
     * @param message the input from user
     */
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
