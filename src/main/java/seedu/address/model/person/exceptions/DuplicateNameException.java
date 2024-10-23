package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Names (Persons are considered duplicates if they have the same
 * name).
 */
public class DuplicateNameException extends RuntimeException {
    public DuplicateNameException() {
        super("Operation would result in duplicate names");
    }
}
