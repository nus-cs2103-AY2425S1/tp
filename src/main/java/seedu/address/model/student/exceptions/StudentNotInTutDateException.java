package seedu.address.model.student.exceptions;

/**
 * Signals that the student is not in specific tutorial session on the date.
 */
public class StudentNotInTutDateException extends RuntimeException {

    public StudentNotInTutDateException() {
        super("Student is not present in the tutorial session on given date.");
    }
}
