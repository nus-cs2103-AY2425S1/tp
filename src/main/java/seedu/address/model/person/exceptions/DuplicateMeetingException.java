package seedu.address.model.person.exceptions;

/**
 * Signals that an operation would result in duplicate meetings.
 */
public class DuplicateMeetingException extends RuntimeException {
    public DuplicateMeetingException() {
        super("Operation would result in duplicate meetings");
    }
}
