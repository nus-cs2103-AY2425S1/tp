package seedu.address.commons.exceptions;

/**
 * Signals that the format of the import file has errors.
 */
public class ImproperFormatException extends Exception {
    /**
     * @param message should contain relevant information on the error(s)
     */
    public ImproperFormatException(String message) {
        super(message);
    }
}
