package seedu.address.model.person.exceptions;

/**
 * Signals that the operation encountered a person of an invalid type.
 * This exception is thrown when a person is not an instance of the expected types (e.g., Teacher or Student).
 */
public class InvalidPersonTypeException extends RuntimeException {
    public InvalidPersonTypeException() {
        super("Invalid person type");
    }
}
