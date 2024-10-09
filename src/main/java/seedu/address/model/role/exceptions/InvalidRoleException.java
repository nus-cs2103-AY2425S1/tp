package seedu.address.model.role.exceptions;

/**
 * Signals that the operation is unable to find the specified role.
 */
public class InvalidRoleException extends Exception {
    public InvalidRoleException() {
        super("Invalid Role input");
    }
}
