package seedu.address.model.job.exceptions;

/**
 * Signals that the operation will result in duplicate Jobs (Jobs are considered duplicates if they have the same
 * name and company).
 */
public class DuplicateJobException extends RuntimeException {
    public DuplicateJobException() {
        super("Operation would result in duplicate jobs");
    }
}


