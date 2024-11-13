package seedu.address.model.goodsreceipt.exceptions;

/**
 * Signals that the operation will result in a supplier that does not exist
 */
public class IllegalSupplierNameException extends RuntimeException {
    public IllegalSupplierNameException() {
        super("Operation would result in a supplier name that does not exists.");
    }
}
