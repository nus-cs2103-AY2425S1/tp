package seedu.ddd.model.event.exceptions;

/**
 * Signals that the operation will result in duplicate Events
 * (Events are considered duplicates if they have the same identity).
 */
public class DuplicateEventException extends RuntimeException {
    public static final String DUPLICATE_EVENT_MESSAGE = "Operation would result in duplicate events";
    public DuplicateEventException() {
        super(DUPLICATE_EVENT_MESSAGE);
    }
}
