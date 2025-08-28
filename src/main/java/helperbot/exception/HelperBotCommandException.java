package helperbot.exception;

public class HelperBotCommandException extends Exception {

    public HelperBotCommandException(String error) {
        super(error);
    }

    @Override
    public String toString() {
        return "Invalid Command: " + super.getMessage();
    }
}
