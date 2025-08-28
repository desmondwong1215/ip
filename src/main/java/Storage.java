import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {

    private static final String SRC = "data/HelperBot.txt";

    public static String loadFromStorage(TaskList tasks) {
        try {
            File file = new File(Storage.SRC);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                tasks.add(Task.of(scanner.nextLine()));
            }
            return "";
        } catch (FileNotFoundException e) {
            return Storage.SRC + " is not found.";
        } catch (HelperBotFileException e) {
            return e.toString();
        }
    }

    public static String writeToStorage(TaskList tasks) {
        try {
            FileWriter fw = new FileWriter(Storage.SRC);
            fw.write(tasks.toStrInFile());
            fw.close();
            return "";
        } catch (IOException e) {
            return "Error: Unable to write task to the file.";
        }
    }
}
