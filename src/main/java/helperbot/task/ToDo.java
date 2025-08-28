package helperbot.task;

import helperbot.exception.HelperBotArgumentException;
import helperbot.exception.HelperBotFileException;

public class ToDo extends Task {

    public ToDo(String description) {
        super(description);
    }

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

    public String toStrInFile() {
        return String.join(",", new String[]{"T", super.toStrInFile()});
    }

    @Override
    public String toString() {
        return "[T]"
                + super.toString();
    }
}