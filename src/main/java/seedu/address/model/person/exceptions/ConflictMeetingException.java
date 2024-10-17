package seedu.address.model.person.exceptions;

/**
 * Signals that an operation would result in conflicting meetings.
 */
public class ConflictMeetingException extends RuntimeException {
    public ConflictMeetingException() {
        super("Operation would result in conflicting meetings");
    }
}
