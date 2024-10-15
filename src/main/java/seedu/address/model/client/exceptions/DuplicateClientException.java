package seedu.address.model.client.exceptions;

/**
 * Signals that the operation will result in duplicate Clients (Clients are considered duplicates if they have the same
 * identity and are of the same type (either Buyer or Seller).
 */
public class DuplicateClientException extends RuntimeException {
    public DuplicateClientException() {
        super("Operation would result in duplicate persons");
    }
}
