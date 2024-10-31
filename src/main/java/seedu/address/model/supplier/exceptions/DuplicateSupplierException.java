package seedu.address.model.supplier.exceptions;

/**
 * Signals that the operation will result in duplicate Suppliers (Suppliers are considered duplicates
 * if they have the same identity).
 */
public class DuplicateSupplierException extends RuntimeException {
    public DuplicateSupplierException() {
        super("Operation would result in duplicate suppliers");
    }
}
