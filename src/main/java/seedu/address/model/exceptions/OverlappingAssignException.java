package seedu.address.model.exceptions;

/**
 * Singnals that assignment will result in a volunteer being assigned to two separate
 * events that occur at the same time.
 */
public class OverlappingAssignException extends RuntimeException {
    public OverlappingAssignException() {
        super("Volunteer is already assigned to another event at that time.");
    }
}
