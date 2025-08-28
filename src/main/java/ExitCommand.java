import java.io.IOException;

public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) {
        try {
            storage.write(tasks);
            ui.exit();
        } catch (IOException e) {
            ui.exitWithError("Error: Unable to write task to the file.\n");
        }
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
