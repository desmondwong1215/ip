package helperbot.exception;

public class HelperBotFileException extends Exception {

    public HelperBotFileException(String error) {
        super(error);
    }

    @Override
    public String toString() {
        return "File Corrupted: " + super.getMessage();
    }
}
