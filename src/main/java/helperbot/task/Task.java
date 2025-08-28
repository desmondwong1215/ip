package helperbot.task;

import helperbot.exception.HelperBotFileException;

import java.time.LocalDate;

public class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

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

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done helperbot.task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public String toStrInFile() {
        return String.join(",", new String[]{isDone ? "1" : "0", this.description});
    }

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