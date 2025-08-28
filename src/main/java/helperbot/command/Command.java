package helperbot.command;

import helperbot.storage.Storage;
import helperbot.task.TaskList;
import helperbot.ui.Ui;

public abstract class Command {

    public abstract void execute(TaskList tasks, Storage storage, Ui ui);

    public boolean isExit() {
        return false;
    }
}