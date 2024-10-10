package spleetwaise.transaction.model.transaction.exceptions;

/**
 * Catches errors that are causes duplicates in the transaction book.
 */
public class DuplicateTransactionException extends RuntimeException {
    public DuplicateTransactionException() {
        super("Operation would result in duplicate transactions");
    }
}
