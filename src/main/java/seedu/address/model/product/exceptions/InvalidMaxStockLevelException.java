package seedu.address.model.product.exceptions;

/**
 * Signals that the provided maximum stock level is invalid.
 */
public class InvalidMaxStockLevelException extends RuntimeException {
    public InvalidMaxStockLevelException(String message) {
        super(message);
    }
}
