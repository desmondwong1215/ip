public enum Command {

    BYE,
    LIST,
    MARK,
    UNMARK,
    TODO,
    DEADLINE,
    EVENT,
    DELETE,
    UNKNOWN;

    public static Command getCommand(String command) {
        return switch (command.toLowerCase()) {
            case "bye" -> BYE;
            case "list" -> LIST;
            case "mark" -> MARK;
            case "unmark" -> UNMARK;
            case "todo" -> TODO;
            case "deadline" -> DEADLINE;
            case "event" -> EVENT;
            case "delete" -> DELETE;
            default -> UNKNOWN;
        };
    }
}