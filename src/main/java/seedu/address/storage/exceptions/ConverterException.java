package seedu.address.storage.exceptions;


/**
 * Represents an error which occurs during execution of the {@Code Converter}.
 */
public class ConverterException extends Exception {

    public ConverterException(String message) {
        super(message);
    }

    public ConverterException(String message, Throwable cause) {
        super(message, cause);
    }
}
