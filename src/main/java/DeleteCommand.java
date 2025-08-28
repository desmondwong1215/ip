public class DeleteCommand extends Command {

    private final String[] message;

    public DeleteCommand(String[] message) {
        this.message = message;
    }

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) {
        int index = 0;
        try {
            index = Integer.parseInt(this.message[1]) - 1;
            Task task = tasks.remove(index);
            String outcome = "Nice! I have removed task "
                    + (index  + 1)
                    + "!\n\t"
                    + task
                    + "\nYou now have "
                    + tasks.size()
                    + " tasks in the list.";
            ui.print(outcome);
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
