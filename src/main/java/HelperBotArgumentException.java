public class HelperBotArgumentException extends IllegalArgumentException {

    public HelperBotArgumentException(String error) {
        super(error);
    }

    @Override
    public String toString() {
        return "Invalid Argument: " + super.getMessage();
    }
}
