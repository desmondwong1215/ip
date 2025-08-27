public class Event extends Task {

    private final String from;
    private final String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
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
            return new Event(message.substring(6, fromIndex).trim(),
                    message.substring(fromIndex + 6, toIndex).trim(),
                    message.substring(toIndex + 4).trim());
        } catch (IndexOutOfBoundsException e) {
            throw new HelperBotArgumentException("Wrong format for Event");
        }

    }

    public static Event of(String[] message) throws HelperBotFileException {
        try {
            Event event = new Event(message[2], message[3], message[4]);
            if (message[1].equals("1")) {
                event.markAsDone();
            } else if (!message[1].equals("0")) {
                throw new HelperBotFileException("Invalid status " + message[0] + " for Task");
            }
            return event;
        } catch (IndexOutOfBoundsException e) {
            throw new HelperBotFileException("Incomplete data for Event");
        }
    }

    public String toStrInFile() {
        return String.join(",", new String[]{"E", super.toStrInFile(), this.from, this.to});
    }

    @Override
    public String toString() {
        return "[E]"
                + super.toString()
                + " (from: "
                + this.from
                + ", to: "
                + this.to + ")";
    }
}