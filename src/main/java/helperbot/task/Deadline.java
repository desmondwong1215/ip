package helperbot.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import helperbot.exception.HelperBotArgumentException;
import helperbot.exception.HelperBotFileException;

/**
 * Represents a <code>Deadline</code> task.
 */
public class Deadline extends Task {

    private final LocalDate byDate;
    private final LocalTime byTime;

    /**
     * Generates a <code>Deadline</code>
     * @param description the name of the <code>Task</code>
     * @param byDate the date of the deadline
     * @param byTime the time of the deadline
     */
    public Deadline(String description, LocalDate byDate, LocalTime byTime) throws HelperBotArgumentException {
        super(description);
        this.byDate = byDate;
        this.byTime = byTime;
    }

    /**
     * Generates a <code>Deadline</code> from user's input.
     * @param message Input from user.
     * @return <code>Deadline</code>.
     * @throws HelperBotArgumentException If HelperBot cannot recognise the argument provided.
     */
    public static Deadline fromUserInput(String message) throws HelperBotArgumentException {
        int byIndex = message.indexOf("/by ");
        if (byIndex == -1) {
            throw new HelperBotArgumentException("Please enter the deadline after /by");
        }
        try {
            String dateTime = message.substring(byIndex + 4).trim();
            LocalDate byDate = LocalDate.parse(dateTime.substring(0, 10));
            LocalTime byTime = null;
            String time = dateTime.substring(10).trim();
            if (!time.isEmpty()) {
                byTime = LocalTime.parse(time);
            }
            return new Deadline(message.substring(9, byIndex).trim(), byDate, byTime);
        } catch (IndexOutOfBoundsException e) {
            throw new HelperBotArgumentException("Wrong format for Deadline");
        } catch (DateTimeParseException e) {
            throw new HelperBotArgumentException("Invalid date or time. "
                    + "Please enter date and time in YYYY-MM-DD hh:mm after /by");
        }
    }

    /**
     * Generates a <code>Deadline</code> from file input.
     * @param message An array of <code>String</code>.
     * @return <code>Deadline</code>.
     * @throws HelperBotFileException If the file is corrupted.
     */
    public static Deadline of(String[] message) throws HelperBotFileException {
        try {
            return getDeadline(message);
        } catch (IndexOutOfBoundsException e) {
            throw new HelperBotFileException("Incomplete data for Deadline");
        } catch (DateTimeParseException e) {
            throw new HelperBotFileException("Date and Time must be in YYYY-MM-DD and hh:mm respectively");
        }
    }

    /**
     * Generates a string representation of <code>Deadline</code>.
     * @return A string representation of <code>Deadline</code>.
     */
    public String toSavaFormat() {
        return String.join(",", new String[]{"D", super.toSavaFormat(),
                this.byDate.toString(), this.byTime == null ? "" : this.byTime.toString()
        });
    }

    /**
     * Checks if the <code>Deadline</code> due on the specified date.
     * @param date The date that <code>Deadline</code> will due.
     * @return true if <code>Deadline</code> due on <code>date</code>.
     */
    @Override
    public boolean isSameDate(LocalDate date) {
        return this.byDate.equals(date);
    }

    @Override
    public String toString() {
        return "[D]"
                + super.toString()
                + " (by: "
                + this.byDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + (this.byTime == null ? "" : ", " + this.byTime)
                + ")";
    }

    @Override
    public Deadline update(String message) throws HelperBotArgumentException {
        System.out.println(message + "hello");
        StringBuilder newMessage = new StringBuilder("deadline ");
        int byIndex = message.indexOf("/by ");
        if (byIndex == -1) {
            newMessage.append(message)
                    .append(" /by ")
                    .append(this.byDate.toString())
                    .append(" ")
                    .append(this.byTime == null ? "" : this.byTime.toString());
        } else if (byIndex == 0) {
            newMessage.append(this.getDescription())
                    .append(" ")
                    .append(message);
        } else {
            newMessage.append(message);
        }
        return Deadline.fromUserInput(newMessage.toString());
    }

    private static Deadline getDeadline(String[] message) throws HelperBotFileException {
        LocalDate byDate = LocalDate.parse(message[3]);
        LocalTime byTime = null;
        if (message.length == 5) {
            byTime = LocalTime.parse(message[4]);
        }
        try {
            Deadline deadline = new Deadline(message[2], byDate, byTime);
            if (message[1].equals("1")) {
                deadline.markAsDone();
            } else if (!message[1].equals("0")) {
                throw new HelperBotFileException("Invalid status " + message[0] + " for Task");
            }
            return deadline;
        } catch (HelperBotArgumentException e) {
            throw new HelperBotFileException("Empty description.");
        }
    }
}
