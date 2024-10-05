package seedu.internbuddy.model.company.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateCompanyException extends RuntimeException {
    public DuplicateCompanyException() {
        super("Operation would result in duplicate companies");
    }
}
