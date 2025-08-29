package helperbot.task;

import java.time.LocalDate;
import java.util.ArrayList;

public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
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

    public TaskList match(String description) {
        TaskList tasks = new TaskList();
        for (Task task: this.tasks) {
            if (task.match(description)) {
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
        StringBuilder taskDescription = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            taskDescription.append("\n").append(i + 1).append(". ").append(tasks.get(i));
        }
        return taskDescription.toString();
    }
}
