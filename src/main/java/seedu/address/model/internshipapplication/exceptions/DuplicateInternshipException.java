package seedu.address.model.internshipapplication.exceptions;

/**
 * Signals that the operation will result in duplicate Internships
 */
public class DuplicateInternshipException extends RuntimeException {
    public DuplicateInternshipException() {
        super("Operation would result in duplicate internships");
    }
}

