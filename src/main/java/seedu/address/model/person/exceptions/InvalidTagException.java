package seedu.address.model.person.exceptions;

/**
 * Signals that the tag entered is of an invalid format.
 */
public class InvalidTagException extends RuntimeException {
    public InvalidTagException(String message) {
        super(message);
    }
}
