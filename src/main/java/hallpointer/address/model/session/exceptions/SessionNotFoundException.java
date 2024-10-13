package hallpointer.address.model.session.exceptions;

/**
 * Signals that the operation is unable to find the specified session.
 */
public class SessionNotFoundException extends RuntimeException {
    public SessionNotFoundException() {
        super("Target session not found");
    }
}
