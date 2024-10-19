package seedu.address.model.exceptions;

/**
 * Signals that the redo command is unable to run at the current iteration of T_Assistant.
 */
public class RedoLimitException extends RuntimeException {
    public RedoLimitException() {
        super("Redo is not possible anymore");
    }
}
