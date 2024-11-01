package seedu.address.model.client.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that there is something wrong with initialisation of a claim object.
 */
public class ClaimException extends IllegalValueException {
    public ClaimException(String message) {
        super(message);
    }
}
