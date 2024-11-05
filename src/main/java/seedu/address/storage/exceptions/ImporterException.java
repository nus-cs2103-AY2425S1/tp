package seedu.address.storage.exceptions;

public class ImporterException extends Exception{

    public ImporterException(String message) {
        super(message);
    }

    public ImporterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImporterException(Throwable cause) {
        super(cause);
    }
}
