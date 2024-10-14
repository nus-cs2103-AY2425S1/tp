package seedu.address.model.owner.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateOwnerException extends RuntimeException {
    public DuplicateOwnerException() {
        super("Operation would result in duplicate persons");
    }
}
