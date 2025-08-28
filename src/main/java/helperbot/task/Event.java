package helperbot.task;

import helperbot.exception.HelperBotArgumentException;
import helperbot.exception.HelperBotFileException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {

    private final LocalDate fromDate;
    private final LocalTime fromTime;
    private final LocalDate toDate;
    private final LocalTime toTime;

    public Event(String description, LocalDate fromDate, LocalTime fromTime, LocalDate toDate, LocalTime toTime) {
        super(description);
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.toTime = toTime;
    }

    public static Event fromInput(String message) throws HelperBotArgumentException {
        int fromIndex = message.indexOf("/from ");
        int toIndex = message.indexOf("/to ");
        if (fromIndex == -1 || toIndex == -1) {
            throw new HelperBotArgumentException("Please enter start date and end data after " +
                    "/from and /to respectively");
        } else if (fromIndex > toIndex) {
            throw new HelperBotArgumentException("Please enter /from before entering /to");
        }
        try {
            String dateTime = message.substring(fromIndex + 6, toIndex).trim();
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
            throw new HelperBotArgumentException("Please enter date and time in YYYY-MM-DD hh:mm after /from and /to");
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

    public static Event of(String[] message) throws HelperBotFileException {
        try {
            return getEvent(message);
        } catch (IndexOutOfBoundsException e) {
            throw new HelperBotFileException("Incomplete data for Event");
        } catch (DateTimeParseException e) {
            throw new HelperBotFileException("Date and Time must be in YYYY-MM-DD and hh:mm respectively");
        }
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
        Event event = new Event(message[2], fromDate, fromTime, toDate, toTime);
        if (message[1].equals("1")) {
            event.markAsDone();
        } else if (!message[1].equals("0")) {
            throw new HelperBotFileException("Invalid status " + message[0] + " for Task");
        }
        return event;
    }

    public String toStrInFile() {
        return String.join(",", new String[]{"E", super.toStrInFile(), 
                this.fromDate.toString(), this.fromTime == null ? "" : this.fromTime.toString(),
                this.toDate.toString(), this.toTime == null ? "" : this.toTime.toString()
        });
    }

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
}