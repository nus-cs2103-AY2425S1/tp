package seedu.address.model.wedding.exceptions;

/**
 * Signals that the operation will result in duplicate {@code Wedding}s.
 * Weddings are considered duplicates if they have the same {@code WeddingName}.
 */
public class DuplicateWeddingException extends RuntimeException {
    public DuplicateWeddingException() {
        super("Operation would result in duplicate weddings");
    }
}
