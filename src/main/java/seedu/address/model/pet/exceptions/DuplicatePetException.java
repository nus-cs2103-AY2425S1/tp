package seedu.address.model.pet.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicatePetException extends RuntimeException {
    public DuplicatePetException() {
        super("Operation would result in duplicate persons");
    }
}
