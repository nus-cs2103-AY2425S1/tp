package seedu.address.logic.parser.exceptions;

/**
 * Signals that there is no window currently open when an attempt is made to close one.
 * This exception is typically thrown when a method attempts to interact with a window
 * that is expected to be open but is not found.
 */
public class NoWindowException extends RuntimeException {
    public NoWindowException(String message) {
        super(message);
    }
}
