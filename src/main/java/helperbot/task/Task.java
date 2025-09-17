package helperbot.task;

import java.time.LocalDate;

import helperbot.exception.HelperBotArgumentException;
import helperbot.exception.HelperBotFileException;

/**
 * Represents a task in <b>HelperBot</b>.
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Generates a <code>Task</code>.
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Generates a <code>Task</code> according to the user's input.
     * @param message The input from user.
     * @return <code>Task</code>
     * @throws HelperBotFileException If the file is corrupted
     */
    public static Task of(String message) throws HelperBotFileException {
        String[] splitMessage = message.split(",");
        return switch (splitMessage[0].trim().toUpperCase()) {
        case "T" -> ToDo.of(splitMessage);
        case "D" -> Deadline.of(splitMessage);
        case "E" -> Event.of(splitMessage);
        default -> throw new HelperBotFileException(
                splitMessage[0].trim().toUpperCase() + " is not Task."
            );
        };
    }

    /**
     * Gets the description.
     * @return The description.
     */
    protected String getDescription() {
        return this.description;
    }

    /**
     * Gets the icon for the status of the <code>Task</code>.
     * @return 'X' if the <code>Task</code> is done, else ' '.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the <code>Task</code>.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Unmarks the <code>Task</code>.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Generates the string representation of the <code>Task</code>.
     * @return The string representation of the <code>Task</code>.
     */
    public String toSavaFormat() {
        return String.join(",", new String[]{isDone ? "1" : "0", this.description});
    }

    /**
     * Checks if the <code>task</code> due on the specific date.
     * @param date The date that the <code>Task</code> will due.
     * @return false
     */
    public boolean isSameDate(LocalDate date) {
        return false;
    }

    /**
     * Checks if the <code>Task</code>'s description contain following keyword.
     * @param description The keyword to be matched.
     * @return true if matched.
     */
    public boolean match(String description) {
        return this.description.contains(description);
    }

    /**
     * Updates the content of the task.
     * @param message The new content.
     * @return The updated task.
     * @throws HelperBotArgumentException If the arguments provided are in wrong format
     */
    public Task update(String message) throws HelperBotArgumentException {
        return this;
    }

    @Override
    public String toString() {
        return "["
                + this.getStatusIcon()
                + "] "
                + description;
    }
}
