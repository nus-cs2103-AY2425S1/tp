package seedu.address.model.wedding.exceptions;

/**
 * Signals that the operation will result in duplicate Weddings (Weddings are considered duplicates
 * if they have the same identity).
 */
public class DuplicateWeddingException extends RuntimeException {
    public DuplicateWeddingException() {
        super("Operation would result in duplicate weddings");
    }
}
