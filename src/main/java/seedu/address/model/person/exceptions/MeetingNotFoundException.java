package seedu.address.model.person.exceptions;

/**
 * Signals that the operation is unable to find the meeting to delete or edit.
 */
public class MeetingNotFoundException extends RuntimeException {

    public MeetingNotFoundException() {
        super("This meeting cannot be found.");
    }
}
