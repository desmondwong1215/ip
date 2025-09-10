package helperbot.command;


import helperbot.exception.HelperBotArgumentException;
import helperbot.storage.Storage;
import helperbot.task.Deadline;
import helperbot.task.Event;
import helperbot.task.Task;
import helperbot.task.TaskList;
import helperbot.task.ToDo;
import helperbot.ui.Response;

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
     * Generate a <code>AddCommand</code>.
     * @param command The type of the task.
     * @param splitMessages The input from user.
     */
    public AddCommand(String command, String splitMessages) {
        this.command = command;
        this.message = splitMessages;
    }

    @Override
    public String execute(TaskList tasks, Storage storage, Response response) {
        try {
            Task task = switch (this.command) {
            case "todo" -> ToDo.fromUserInput(this.message);
            case "deadline" -> Deadline.fromUserInput(this.message);
            case "event" -> Event.fromUserInput(this.message);
            default -> {
                ///  Tasks should be either 'todo', 'deadline', or 'event'.
                ///  Thus, default case should not be used.
                assert false: "Unhandled task: " + this.command;
                yield new Task("");
            }
            };
            tasks.add(task);
            return response.getAddCommandResponse(task, tasks.size());
        } catch (HelperBotArgumentException e) {
            /// /by is not entered correctly
            return response.getErrorMessage(e.toString());
        }
    }
}
