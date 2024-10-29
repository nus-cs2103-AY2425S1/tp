package seedu.ddd.model.event.exceptions;

/**
 * Signals that the operation is unable to find the specified event.
 */
public class EventNotFoundException extends RuntimeException {
    public static final String EVENT_NOT_FOUND_MESSAGE = "Event specified cannot be found";
    public EventNotFoundException() {
        super(EVENT_NOT_FOUND_MESSAGE);
    }
}
