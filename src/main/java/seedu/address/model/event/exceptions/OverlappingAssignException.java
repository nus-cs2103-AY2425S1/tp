package seedu.address.model.event.exceptions;

public class OverlappingAssignException extends RuntimeException {
    public OverlappingAssignException() {
        super("Volunteer is already assigned to another event at that time.");
    }
}
