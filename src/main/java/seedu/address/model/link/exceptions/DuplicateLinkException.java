package seedu.address.model.link.exceptions;

/**
 * Signals that the operation will result in duplicate Links
 * (Links are considered duplicates if they have the same identity).
 */
public class DuplicateLinkException extends RuntimeException {
    public DuplicateLinkException() {
        super("Operation would result in duplicate links");
    }
}
