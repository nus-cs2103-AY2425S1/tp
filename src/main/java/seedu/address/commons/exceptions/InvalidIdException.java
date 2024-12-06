package seedu.address.commons.exceptions;

/**
 * Signals that the input id can't be found
 */
public class InvalidIdException extends IllegalValueException {

    /**
     * @param message should contain relevant information on the invalid id
     */
    public InvalidIdException(String message) {
        super(message);
    }
}
