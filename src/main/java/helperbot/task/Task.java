package helperbot.task;

import java.time.LocalDate;

import helperbot.exception.HelperBotFileException;

/**
 * Represent a task in <b>HelperBot</b>.
 */
public class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Generate a <code>Task</code> according to the user's input.
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
     * Get the icon for the status of the <code>Task</code>.
     * @return 'X' if the <code>Task</code> is done, else ' '.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done helperbot.task with X
    }

    /**
     * Mark the <code>Task</code>.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Unmark the <code>Task</code>.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Generate the string representation of the <code>Task</code>.
     * @return The string representation of the <code>Task</code>.
     */
    public String toStrInFile() {
        return String.join(",", new String[]{isDone ? "1" : "0", this.description});
    }

    /**
     * Check if the <code>task</code> due on the specific date.
     * @param date The date that the <code>Task</code> will due.
     * @return false
     */
    public boolean isSameDate(LocalDate date) {
        return false;
    }

    @Override
    public String toString() {
        return "["
                + this.getStatusIcon()
                + "] "
                + description;
    }
}