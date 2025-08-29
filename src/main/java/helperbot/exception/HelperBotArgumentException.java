package helperbot.exception;

/**
 * Represent an exception that handles all the invalid input from user.
 */
public class HelperBotArgumentException extends Exception {

    public HelperBotArgumentException(String error) {
        super(error);
    }

    @Override
    public String toString() {
        return "Invalid Argument: " + super.getMessage();
    }
}
