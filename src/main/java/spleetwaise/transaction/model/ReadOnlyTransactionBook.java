package spleetwaise.transaction.model;

import javafx.collections.ObservableList;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * Unmodifiable view of an transaction book.
 */
public interface ReadOnlyTransactionBook {
    /**
     * Returns an unmodifiable view of the transaction book.
     */
    ObservableList<Transaction> getTransactionList();
}
