package seedu.address.model.tag.exceptions;

/**
 * Signals that the operation will result in duplicate Tags.
 */
public class DuplicateTagException extends RuntimeException {
    public DuplicateTagException() {
        super("Operation would result in duplicate tags");
    }
}

