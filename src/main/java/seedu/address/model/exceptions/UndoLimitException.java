package seedu.address.model.exceptions;

/**
 * Signals that the undo command is unable to run at the current iteration of T_Assistant.
 */
public class UndoLimitException extends RuntimeException {
    public UndoLimitException() {
        super("Undo is not possible anymore");
    }
}
