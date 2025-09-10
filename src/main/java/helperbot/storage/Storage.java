package helperbot.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import helperbot.exception.HelperBotFileException;
import helperbot.task.Task;
import helperbot.task.TaskList;

/**
 * Represents a class that is responsible for loading and writing all the <code>Task</code> from and to a specific file.
 */
public class Storage {

    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads all the <code>Task</code> from the file.
     * @return An <code>ArrayList<\Task\></code>.
     * @throws HelperBotFileException If file is corrupted.
     * @throws FileNotFoundException if filePath is not valid.
     */
    public ArrayList<Task> load() throws HelperBotFileException, FileNotFoundException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(this.filePath);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            tasks.add(Task.of(scanner.nextLine()));
        }
        return tasks;
    }

    /**
     * Writes all the <code>Task</code> to the file.
     * @param tasks An <code>ArrayList<\Task\></code>.
     * @throws IOException If error occurs when writing the file.
     */
    public void write(TaskList tasks) throws IOException {
        FileWriter fw = new FileWriter(this.filePath);
        fw.write(tasks.toSaveFormat());
        fw.close();
    }
}
