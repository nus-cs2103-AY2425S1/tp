package seedu.address.commons.exceptions;

/**
 * Represents an error during getting appointment details
 */
public class AppNotFoundException extends Exception {
    public AppNotFoundException(String message) {
        super(message);
    }
}
