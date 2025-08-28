import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class TaskList {

    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void add(Task task) {
        this.tasks.add(task);
    }

    public void mark(int index) {
        this.tasks.get(index).markAsDone();
    }

    public void unmark(int index) {
        this.tasks.get(index).markAsNotDone();
    }

    public Task get(int index) {
        return this.tasks.get(index);
    }

    public TaskList getTaskOnDate(LocalDate date) {
        TaskList tasks = new TaskList();
        for (Task task: this.tasks) {
            if (task.isSameDate(date)) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    public Task remove(int index) {
        return this.tasks.remove(index);
    }

    public int size() {
        return this.tasks.size();
    }

    public String toStrInFile() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Task task: this.tasks) {
            stringBuilder.append(task.toStrInFile()).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        if (this.tasks.isEmpty()) {
            return "You do not have any task.";
        }

        StringBuilder taskDescription = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            taskDescription.append("\n").append(i + 1).append(". ").append(tasks.get(i));
        }
        return taskDescription.toString();
    }
}
