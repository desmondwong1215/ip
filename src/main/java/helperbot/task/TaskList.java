package helperbot.task;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Represent a list of <code>Task</code> in <b>HelperBot</b>.
 */
public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Add a task to the list.
     * @param task Task to be added.
     */
    public void add(Task task) {
        this.tasks.add(task);
    }

    /**
     * Mark the task with index <code>index</code> to done.
     * @param index The index of the <code>Task</code>.
     */
    public void mark(int index) {
        this.tasks.get(index).markAsDone();
    }

    /**
     * Mark the task with index <code>index</code> to not done.
     * @param index The index of the <code>Task</code>.
     */
    public void unmark(int index) {
        this.tasks.get(index).markAsNotDone();
    }

    /**
     * Get the <code>Task</code> with index <code>Index</code>.
     * @param index The index of the <code>Task</code>.
     * @return <code>Task</code>.
     */
    public Task get(int index) {
        return this.tasks.get(index);
    }

    /**
     * Generate a <code>TaskList</code> where all the <code>Task</code> due on the specific date.
     * @param date The date that <code>Task</code> will due.
     * @return <code>TaskList</code>.
     */
    public TaskList getTaskOnDate(LocalDate date) {
        TaskList tasks = new TaskList();
        for (Task task: this.tasks) {
            if (task.isSameDate(date)) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    /**
     * Generate <code>TaskList</code> containing all the <code>Task</code> match <code>description</code>.
     * @param description The keyword to be matched.
     * @return <code>TaskList</code> containing all the <code>Task</code> match <code>description</code>.
     */
    public TaskList match(String description) {
        TaskList tasks = new TaskList();
        for (Task task: this.tasks) {
            if (task.match(description)) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    /**
     * Remove the <code>Task</code> with index <code>Index</code>.
     * @param index The index of the <code>Task</code>.
     * @return <code>Task</code>.
     */
    public Task remove(int index) {
        return this.tasks.remove(index);
    }

    /**
     * Get the number of <code>Task</code> in <code>TaskList</code>.
     * @return the size of <code>TaskList</code>.
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Generate the string representation of the <code>TaskList</code>.
     * @return The string representation of the <code>TaskList</code>.
     */
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
