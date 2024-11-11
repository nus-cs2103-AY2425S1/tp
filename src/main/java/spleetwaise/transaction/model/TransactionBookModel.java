package spleetwaise.transaction.model;

import java.util.function.Predicate;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * The API of the transaction component.
 */
public interface TransactionBookModel {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Transaction> PREDICATE_SHOW_ALL_TXNS = unused -> true;

    /**
     * Returns the transaction book.
     */
    ReadOnlyTransactionBook getTransactionBook();

    /**
     * Replaces address book data with the data in {@code replacementBook}.
     */
    void setTransactionBook(ReadOnlyTransactionBook replacementBook);

    /**
     * Adds the given transaction. {@code transaction} must not already exist in the address book.
     */
    void addTransaction(Transaction transaction);

    /**
     * Returns true if a transaction with the same details as an existing transaction exist in the transaction book.
     */
    boolean hasTransaction(Transaction transaction);

    /**
     * Returns an unmodifiable view of the filtered transaction list.
     */
    ObservableList<Transaction> getFilteredTransactionList();

    /**
     * Returns the current predicate used to filter transactions.
     */
    ObjectProperty<Predicate<Transaction>> getCurrentPredicate();

    /**
     * Updates the filter of the filtered transaction list to reuse existing filter. By default, existing filter is show
     * all transactions.
     */
    void updateFilteredTransactionList();

    /**
     * Updates the filter of the filtered transaction list to filter by the given {@code predicate}. Set to null to
     * clear existing filters.
     */
    void updateFilteredTransactionList(Predicate<Transaction> predicate);

    /**
     * Deletes transactions of a person with the given id. Used for when a person is deleted.
     */
    void deleteTransactionsOfPersonId(String personId);

    /**
     * Deletes the given transaction. Transaction must be present in the transactionBook.
     */
    void deleteTransaction(Transaction target);

    /**
     * Replaces the given transaction. Transaction must be present in the transactionBook.
     */
    void setTransaction(Transaction target, Transaction editedTransaction);
}
