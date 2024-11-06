package seedu.address.model.event.exceptions;

/**
 * Signals that the operation will result in duplicate Events.
 */
public class DuplicateEventException extends RuntimeException {
    public DuplicateEventException() {
        super("Operation would result in duplicate events");
    }
}
