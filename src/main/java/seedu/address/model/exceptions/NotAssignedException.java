package seedu.address.model.exceptions;

/**
 * Signals that the operation is trying to remove a volunteer from an event that the volunteer is not assigned to,
 * or vice versa.
 */
public class NotAssignedException extends RuntimeException {

    public NotAssignedException() {
        super("Volunteer is not assigned to this event!");
    }
}
