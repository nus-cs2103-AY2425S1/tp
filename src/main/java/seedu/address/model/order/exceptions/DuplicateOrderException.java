package seedu.address.model.order.exceptions;

/**
 * Signals that the operation will result in duplicate Order.
 */
public class DuplicateOrderException extends RuntimeException {
    public DuplicateOrderException() {
        super("Operation would result in a duplicate order");
    }
}
