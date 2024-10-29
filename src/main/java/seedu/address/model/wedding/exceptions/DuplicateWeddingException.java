package seedu.address.model.wedding.exceptions;

/**
 * Signals that the operation will result in duplicate Wedding (Wedding are considered duplicates if they have the same
 * name, venue, date and client).
 */
public class DuplicateWeddingException extends RuntimeException {
    public DuplicateWeddingException() {
        super("Operation would result in duplicate persons");
    }
}
