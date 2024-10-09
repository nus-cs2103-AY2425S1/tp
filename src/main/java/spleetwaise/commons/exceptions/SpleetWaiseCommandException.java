package spleetwaise.commons.exceptions;

/**
 * Represents an exception that occurs during command execution in SpleetWaise.
 */
public class SpleetWaiseCommandException extends Exception {
    public SpleetWaiseCommandException(String message) {
        super(message);
    }

    public SpleetWaiseCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
