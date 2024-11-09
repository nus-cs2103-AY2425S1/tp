package seedu.address.model.event.exceptions;

/**
 * Signals that the operation will result in the creation of Events that start after it ends.
 */
public class ChronologicalOrderException extends RuntimeException {
    public ChronologicalOrderException() {
        super("Events must start before it ends.");
    }
}
