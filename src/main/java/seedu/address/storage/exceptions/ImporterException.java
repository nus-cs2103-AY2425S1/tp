package seedu.address.storage.exceptions;

/**
 * Represents an error which occurs during execution of the {@Code Importer}.
 */
public class ImporterException extends Exception {

    public ImporterException(String message) {
        super(message);
    }

    public ImporterException(Throwable cause) {
        super(cause);
    }
}
