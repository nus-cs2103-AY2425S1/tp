package seedu.address.model.job.exceptions;

/**
 * Signals that the operation is unable to find the specified job.
 */
public class JobNotFoundException extends RuntimeException {

    public JobNotFoundException() {}

    /**
     * @param message should contain relevant information on the failed constraint(s).
     */
    public JobNotFoundException(String message) {
        super(message);
    }
}
