package seedu.address.model.person.exceptions;

/**
 * Signals that the role entered is of an invalid format.
 */
public class InvalidRoleException extends RuntimeException {
    public InvalidRoleException(String message) {
        super(message);
    }
}
