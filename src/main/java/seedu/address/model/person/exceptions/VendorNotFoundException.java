package seedu.address.model.person.exceptions;

/**
 * Signals that the operation is unable to find the specified vendor.
 */
public class VendorNotFoundException extends RuntimeException {
    public VendorNotFoundException() {
        super("Vendor does not exist.");
    }
}
