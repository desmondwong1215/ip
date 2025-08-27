public class Deadline extends Task {

    private final String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public static Deadline fromInput(String message) throws HelperBotArgumentException {
        int byIndex = message.indexOf("/by ");
        if (byIndex == -1) {
            throw new HelperBotArgumentException("Please enter the deadline after /by");
        }
        try {
            return new Deadline(message.substring(9, byIndex).trim(),
                    message.substring(byIndex + 4).trim());
        } catch (IndexOutOfBoundsException e) {
            throw new HelperBotArgumentException("Wrong format for Deadline");
        }
    }

    public static Deadline of(String[] message) throws HelperBotFileException {
        try {
            Deadline deadline = new Deadline(message[2], message[3]);
            if (message[1].equals("1")) {
                deadline.markAsDone();
            } else if (!message[1].equals("0")) {
                throw new HelperBotFileException("Invalid status " + message[0] + " for Task");
            }
            return deadline;
        } catch (IndexOutOfBoundsException e) {
            throw new HelperBotFileException("Incomplete data for Deadline");
        }
    }

    public String toStrInFile() {
        return String.join(",", new String[]{"D", super.toStrInFile(), this.by});
    }

    @Override
    public String toString() {
        return "[D]"
                + super.toString()
                + " (by: " + by + ")";
    }
}