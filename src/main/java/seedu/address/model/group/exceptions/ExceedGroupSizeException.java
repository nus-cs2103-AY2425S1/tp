package seedu.address.model.group.exceptions;

/**
 * Signals that there will be more people in the group than the maximum size allowed.
 */
public class ExceedGroupSizeException extends RuntimeException {
    public ExceedGroupSizeException() {
        super("Group exceeded the maximum size");
    }
}
