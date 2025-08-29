package helperbot.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import helperbot.exception.HelperBotArgumentException;
import helperbot.exception.HelperBotFileException;

public class Deadline extends Task {

    private final LocalDate byDate;
    private final LocalTime byTime;

    public Deadline(String description, LocalDate byDate, LocalTime byTime) {
        super(description);
        this.byDate = byDate;
        this.byTime = byTime;
    }

    public static Deadline fromInput(String message) throws HelperBotArgumentException {
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
            throw new HelperBotArgumentException("Please enter date and time in YYYY-MM-DD hh:mm after /by");
        }
    }

    public static Deadline of(String[] message) throws HelperBotFileException {
        try {
            return getDeadline(message);
        } catch (IndexOutOfBoundsException e) {
            throw new HelperBotFileException("Incomplete data for Deadline");
        } catch (DateTimeParseException e) {
            throw new HelperBotFileException("Date and Time must be in YYYY-MM-DD and hh:mm respectively");
        }
    }

    private static Deadline getDeadline(String[] message) throws HelperBotFileException {
        LocalDate byDate = LocalDate.parse(message[3]);
        LocalTime byTime = null;
        if (message.length == 5) {
            byTime = LocalTime.parse(message[4]);
        }
        Deadline deadline = new Deadline(message[2], byDate, byTime);
        if (message[1].equals("1")) {
            deadline.markAsDone();
        } else if (!message[1].equals("0")) {
            throw new HelperBotFileException("Invalid status " + message[0] + " for Task");
        }
        return deadline;
    }

    public String toStrInFile() {
        return String.join(",", new String[]{"D", super.toStrInFile(),
                this.byDate.toString(), this.byTime == null ? "" : this.byTime.toString()
        });
    }

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
}