package seedu.address.model.delivery.exceptions;

/**
 * Signals that the operation will result in duplicate Deliveries (Delivery are considered duplicates if they have
 * the same data and identity fields).
 */
public class DuplicateDeliveryException extends RuntimeException {
    public DuplicateDeliveryException() {
        super("Operation would result in duplicate deliveries");
    }
}
