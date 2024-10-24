package seedu.address.model.product.exceptions;

/**
 * Signals that the stock level is outside the allowed range defined by min and max stock levels.
 */
public class StockLevelOutOfBoundsException extends RuntimeException {
    public StockLevelOutOfBoundsException(String message) {
        super(message);
    }
}
