package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Vendors (Vendors are considered duplicates if they have the same
 * identity).
 */
public class DuplicateVendorException extends RuntimeException {
    public DuplicateVendorException() {
        super("Operation would result in duplicate vendors");
    }
}
