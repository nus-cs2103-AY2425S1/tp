package seedu.address.model.exceptions;

/**
 * Signals that assignment will result in a volunteer being assigned to two separate
 * events that occur at the same time.
 */
public class OverlappingAssignException extends RuntimeException {
    public OverlappingAssignException(String alrAssignedEvent) {
        super(String.format("The volunteer is already assigned to %s which occurs at the same time.",
                alrAssignedEvent));
    }
}
