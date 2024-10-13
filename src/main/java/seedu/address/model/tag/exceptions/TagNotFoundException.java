package seedu.address.model.tag.exceptions;

/**
 * Signals that the operation is unable to find the specified Tag.
 */
public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException() {
        super("Tag not found in list");
    }
}

