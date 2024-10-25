package seedu.address.model.person.exceptions;

/**
 * Signals that the total weightage of grades exceeds the allowed limit.
 */
public class ExceedTotalWeightageException extends Exception {

    /**
     * Constructs a new ExceedTotalWeightageException with a default error message.
     */
    public ExceedTotalWeightageException() {
        super("The total weightage of grades exceeds the allowed limit.");
    }
}
