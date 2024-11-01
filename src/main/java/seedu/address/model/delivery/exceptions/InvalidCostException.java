package seedu.address.model.delivery.exceptions;

/**
 * Signals that the cost {@code String} saved within the {@code Cost} object has incorrect format.
 */
public class InvalidCostException extends RuntimeException {
    public InvalidCostException(String message) {
        super(message);
    }
}
