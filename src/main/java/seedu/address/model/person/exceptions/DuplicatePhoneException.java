package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Phones (Phones are considered duplicates if they have the same
 * number).
 */
public class DuplicatePhoneException extends RuntimeException {
    public DuplicatePhoneException() {
        super("Operation would result in duplicate phone numbers");
    }
}
