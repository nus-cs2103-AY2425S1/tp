package seedu.address.commons.exceptions;

/**
 * Represents an error during the loading of data from the sample address book.
 */
public class SampleDataLoadingException extends Exception {
    public SampleDataLoadingException(Exception cause) {
        super(cause);
    }
}
