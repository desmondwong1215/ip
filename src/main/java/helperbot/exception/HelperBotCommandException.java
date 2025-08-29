package helperbot.exception;

/**
 * Represent an exception that handles all the invalid command from user.
 * <p>
 * Command is the first word of the input.
 */
public class HelperBotCommandException extends Exception {

    public HelperBotCommandException(String error) {
        super(error);
    }

    @Override
    public String toString() {
        return "Invalid Command: " + super.getMessage();
    }
}
