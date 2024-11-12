package seedu.address.model.exceptions;

/**
 * Signals that the operation is trying to assign a volunteer to an event that the volunteer is already assigned to,
 * or vice versa.
 */
public class DuplicateAssignException extends RuntimeException {

    public DuplicateAssignException() {
        super("Volunteer is already assigned to this event!");
    }
}
