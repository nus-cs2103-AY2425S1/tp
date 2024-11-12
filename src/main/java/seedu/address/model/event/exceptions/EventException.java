package seedu.address.model.event.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a parse error encountered by an event.
 */
public class EventException extends IllegalValueException {
    public EventException(String message) {
        super(message);
    }
}
