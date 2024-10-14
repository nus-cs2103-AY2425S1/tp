package seedu.address.model.client.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals there is something wrong with the initialisation of an Insurance Plan Object.
 */
public class InsurancePlanException extends IllegalValueException {
    public InsurancePlanException(String message) {
        super(message);
    }
}
