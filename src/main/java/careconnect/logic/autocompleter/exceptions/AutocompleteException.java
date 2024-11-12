package careconnect.logic.autocompleter.exceptions;

import careconnect.commons.exceptions.IllegalValueException;

/**
 * Represents a autocomplete error encountered by a autocompleter.
 */
public class AutocompleteException extends IllegalValueException {

    public AutocompleteException(String message) {
        super(message);
    }
    // This constructor is added for consistency sake, currently is not being utilized.
    public AutocompleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
