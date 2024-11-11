package seedu.address.model.person.exceptions;

/**
 * Signals that the dateTime format given is incorrect
 */
public class TimeParseException extends RuntimeException {
    public TimeParseException() {
        super("Invalid date time format given");
    }
}
