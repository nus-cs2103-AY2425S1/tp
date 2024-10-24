package seedu.address.model.product.exceptions;

/**
 * Signals that the provided stock level is invalid.
 */
public class InvalidStockLevelException extends RuntimeException {
    public InvalidStockLevelException(String message) {
        super(message);
    }
}
