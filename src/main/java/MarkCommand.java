public class MarkCommand extends Command {

    private final String[] message;
    private final boolean mark;

    public MarkCommand(String[] message, boolean mark) {
        this.message = message;
        this.mark = mark;
    }

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) {
        int index = 0;

        try {
            index = Integer.parseInt(this.message[1]) - 1;
            if (this.mark) {
                // mark the task as done
                tasks.mark(index);
                ui.showMarked(index, tasks.get(index));
            } else {
                // mark the task as not done
                tasks.unmark(index);
                ui.showUnmarked(index, tasks.get(index));
            }
        } catch (IndexOutOfBoundsException e) {
            if (this.message.length == 1) {
                // the message length < 2, index is not provided
                ui.showError("Invalid Argument: Please enter the index of the task after " + this.message[0] + ".");
            } else {
                // index >= tasks.size(), task is not found
                ui.showError("Invalid Argument: Task " + (index + 1) + " is not found.");
            }
        } catch (NumberFormatException e) {
            // the second input cannot be parsed as an integer
            ui.showError("Invalid Argument: " + this.message[1] + " cannot be parsed as an integer.");
        }
    }
}
