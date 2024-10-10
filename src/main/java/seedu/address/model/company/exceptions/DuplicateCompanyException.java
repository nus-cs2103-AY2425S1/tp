package seedu.address.model.company.exceptions;

/**
 * Signals that the operation will result in duplicate companies.
 * (companies considered duplicates if they have the same name)
 */
public class DuplicateCompanyException extends RuntimeException {
    public DuplicateCompanyException() {
        super("Operation would result in duplicate companies");
    }
}
