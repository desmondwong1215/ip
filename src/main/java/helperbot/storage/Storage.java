package helperbot.storage;

import helperbot.exception.HelperBotFileException;
import helperbot.task.Task;
import helperbot.task.TaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws HelperBotFileException, FileNotFoundException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(this.filePath);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            tasks.add(Task.of(scanner.nextLine()));
        }
        return tasks;
    }

    public void write(TaskList tasks) throws IOException {
        FileWriter fw = new FileWriter(this.filePath);
        fw.write(tasks.toStrInFile());
        fw.close();
    }
}
