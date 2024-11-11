package seedu.address.model.pet.exceptions;

/**
 * Signals that the operation will result in a pet having more than 1 owner
 */
public class InvalidOwnerNumberException extends RuntimeException {
    public InvalidOwnerNumberException() {
        super("Operation would result in a pet having more than 1 owner");
    }
}
