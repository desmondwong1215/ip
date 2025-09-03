package helperbot.task;

import helperbot.exception.HelperBotArgumentException;
import helperbot.exception.HelperBotFileException;

/**
 * Represents a <code>ToDo</code> task.
 */
public class ToDo extends Task {

    /**
     * Generate a <code>ToDo</code>
     * @param description the name of the task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Generates a <code>ToDo</code> from user's input.
     * @param message Input from user.
     * @return <code>ToDo</code>.
     * @throws HelperBotArgumentException If HelperBot cannot recognise the argument provided.
     */
    public static ToDo fromInput(String message) throws HelperBotArgumentException {
        try {
            String detail = message.substring(5).trim();
            if (detail.isEmpty()) {
                throw new HelperBotArgumentException("Empty description.");
            }
            return new ToDo(detail);
        } catch (IndexOutOfBoundsException e) {
            throw new HelperBotArgumentException("Wrong format for ToDo.");
        }
    }

    /**
     * Generates a <code>Event</code> from file input.
     * @param message An array of <code>String</code>.
     * @return <code>Event</code>.
     * @throws HelperBotFileException If the file is corrupted.
     */
    public static ToDo of(String[] message) throws HelperBotFileException {
        try {
            ToDo toDo = new ToDo(message[2]);
            if (message[1].equals("1")) {
                toDo.markAsDone();
            } else if (!message[1].equals("0")) {
                throw new HelperBotFileException("Invalid status " + message[1] + " for Task.");
            }
            return toDo;
        } catch (IndexOutOfBoundsException e) {
            throw new HelperBotFileException("Incomplete data for Task.");
        }
    }

    /**
     * Generates a string representation of <code>ToDo</code>.
     * @return A string representation of <code>ToDo</code>.
     */
    public String toStrInFile() {
        return String.join(",", new String[]{"T", super.toStrInFile()});
    }

    @Override
    public String toString() {
        return "[T]"
                + super.toString();
    }
}
