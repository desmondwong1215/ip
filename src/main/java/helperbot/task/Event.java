package helperbot.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import helperbot.exception.HelperBotArgumentException;
import helperbot.exception.HelperBotFileException;

/**
 * Represents a <code>Event</code> task.
 */
public class Event extends Task {

    private final LocalDate fromDate;
    private final LocalTime fromTime;
    private final LocalDate toDate;
    private final LocalTime toTime;

    /**
     * Generate a <code>Event</code>
     * @param description The name of the task.
     * @param fromDate The start date of the event.
     * @param fromTime The start time of the event.
     * @param toDate The end date of the event.
     * @param toTime The end time of the event.
     */
    public Event(String description, LocalDate fromDate, LocalTime fromTime, LocalDate toDate,
                 LocalTime toTime) throws HelperBotArgumentException {
        super(description);
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.toTime = toTime;
    }

    /**
     * Generates a <code>Event</code> from user's input.
     * @param message Input from user.
     * @return <code>Event</code>.
     * @throws HelperBotArgumentException If HelperBot cannot recognise the argument provided.
     */
    public static Event fromUserInput(String message) throws HelperBotArgumentException {
        int fromIndex = message.indexOf("/from ");
        int toIndex = message.indexOf("/to ");
        if (fromIndex == -1 || toIndex == -1) {
            throw new HelperBotArgumentException("Please enter start date and end data after "
                    + "/from and /to respectively");
        } else if (fromIndex > toIndex) {
            throw new HelperBotArgumentException("Please enter /from before entering /to");
        }
        try {
            ///  Ignore "/from ", which are 6 characters.
            String dateTime = message.substring(fromIndex + 6, toIndex).trim();
            ///  Date should be in the format of YYYY-MM-DD, thus the first 10 chars refer date.
            ///  The rest of the substring should refer to the time (if present).
            LocalDate fromDate = LocalDate.parse(dateTime.substring(0, 10));
            LocalTime fromTime = Event.getTime(dateTime.substring(10).trim());
            dateTime = message.substring(toIndex + 4).trim();
            LocalDate toDate = LocalDate.parse(dateTime.substring(0, 10));
            LocalTime toTime = Event.getTime(dateTime.substring(10).trim());
            if (Event.isToBeforeFrom(fromDate, fromTime, toDate, toTime)) {
                throw new HelperBotArgumentException("From datetime must be before to datetime");
            }
            return new Event(message.substring(6, fromIndex).trim(),
                    fromDate, fromTime, toDate, toTime);
        } catch (IndexOutOfBoundsException e) {
            throw new HelperBotArgumentException("Wrong format for Event");
        } catch (DateTimeParseException e) {
            throw new HelperBotArgumentException("Invalid date or time. "
                    + "Please enter date and time in YYYY-MM-DD hh:mm after /from and /to");
        }
    }

    /**
     * Generates a <code>Event</code> from file input.
     * @param message An array of <code>String</code>.
     * @return <code>Event</code>.
     * @throws HelperBotFileException If the file is corrupted.
     */
    public static Event of(String[] message) throws HelperBotFileException {
        try {
            return getEvent(message);
        } catch (IndexOutOfBoundsException e) {
            throw new HelperBotFileException("Incomplete data for Event");
        } catch (DateTimeParseException e) {
            throw new HelperBotFileException("Date and Time must be in YYYY-MM-DD and hh:mm respectively");
        }
    }

    /**
     * Generates a string representation of <code>Event</code>.
     * @return A string representation of <code>Event</code>.
     */
    public String toSavaFormat() {
        return String.join(",", new String[]{"E", super.toSavaFormat(),
                this.fromDate.toString(), this.fromTime == null ? "" : this.fromTime.toString(),
                this.toDate.toString(), this.toTime == null ? "" : this.toTime.toString()
        });
    }

    /**
     * Checks if the <code>Event</code> due on the specified date.
     * @param date The date that <code>Event</code> will due.
     * @return true if <code>Event</code> due on <code>date</code>.
     */
    @Override
    public boolean isSameDate(LocalDate date) {
        return this.toDate.isEqual(date);
    }

    @Override
    public String toString() {
        return "[E]"
                + super.toString()
                + " (from: "
                + this.fromDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + (this.fromTime == null ? "" : ", " + this.fromTime)
                + ") (to: "
                + this.toDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + (this.toTime == null ? "" : ", " + this.toTime)
                + ")";
    }

    @Override
    public Event update(String message) throws HelperBotArgumentException {
        String[] newMessage = new String[]{"event", this.getDescription(), "/from",
                this.fromDate.toString() + " " + (this.fromTime == null ? "" : this.fromTime.toString()), "/to",
                this.toDate.toString() + " " + (this.toTime == null ? "" : this.toTime.toString())};
        int fromIndex = message.indexOf("/from ");
        int toIndex = message.indexOf("/to ");
        if (fromIndex == -1 && toIndex == -1) {
            newMessage[1] = message;
        } else if (fromIndex == -1) {
            if (toIndex != 0) {
                newMessage[1] = message.substring(0, toIndex);
            }
            newMessage[5] = message.substring(toIndex + 4);
        } else if (toIndex == -1) {
            if (fromIndex != 0) {
                newMessage[1] = message.substring(0, fromIndex);
            }
            newMessage[3] = message.substring(fromIndex + 6);
        } else {
            if (fromIndex != 0) {
                newMessage[1] = message.substring(0, fromIndex);
            }
            newMessage[3] = message.substring(fromIndex + 6, toIndex);
            newMessage[5] = message.substring(toIndex + 4);
        }
        System.out.println(String.join(" ", newMessage));
        return Event.fromUserInput(String.join(" ", newMessage));
    }

    private static Event getEvent(String[] message) throws HelperBotFileException {
        LocalDate fromDate = LocalDate.parse(message[3]);
        LocalDate toDate = LocalDate.parse(message[5]);
        LocalTime fromTime = null;
        LocalTime toTime = null;
        if (!message[4].isEmpty()) {
            fromTime = LocalTime.parse(message[4]);
        }
        if (message.length == 7) {
            toTime = LocalTime.parse(message[6]);
        }
        if (Event.isToBeforeFrom(fromDate, fromTime, toDate, toTime)) {
            throw new HelperBotFileException("From datetime must be before to datetime");
        }
        try {
            Event event = new Event(message[2], fromDate, fromTime, toDate, toTime);
            if (message[1].equals("1")) {
                event.markAsDone();
            } else if (!message[1].equals("0")) {
                throw new HelperBotFileException("Invalid status " + message[0] + " for Task");
            }
            return event;
        } catch (HelperBotArgumentException e) {
            throw new HelperBotFileException("Empty description.");
        }
    }

    private static boolean isToBeforeFrom(LocalDate fromDate, LocalTime fromTime,
                                          LocalDate toDate, LocalTime toTime) {
        if (fromDate.isAfter(toDate)) {
            return true;
        }
        return fromDate.isEqual(toDate) && fromTime != null && toTime != null && fromTime.isAfter(toTime);
    }

    private static LocalTime getTime(String message) {
        if (message.isEmpty()) {
            return null;
        } else {
            return LocalTime.parse(message);
        }
    }
}
