package seedu.address.model.product.exceptions;

/**
 * Signals that the provided minimum stock level is invalid.
 */
public class InvalidMinStockLevelException extends RuntimeException {
    public InvalidMinStockLevelException(String message) {
        super(message);
    }
}
