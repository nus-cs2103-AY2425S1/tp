package spleetwaise.transaction.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import spleetwaise.address.commons.util.CollectionUtil;
import spleetwaise.transaction.model.transaction.Transaction;
import spleetwaise.transaction.model.transaction.exceptions.DuplicateTransactionException;
import spleetwaise.transaction.model.transaction.exceptions.TransactionNotFoundException;

/**
 * Wraps all data at the transaction book level.
 */
public class TransactionBook implements ReadOnlyTransactionBook {

    private final ObservableList<Transaction> transactionList;
    private final ObservableList<Transaction> transactionUnmodifiableList;

    /**
     * Creates an TransactionBook using the Transactions in the {@code existingTransactionList}.
     */
    public TransactionBook(List<Transaction> existingTransactionList) {
        transactionList = FXCollections.observableArrayList(existingTransactionList);
        transactionUnmodifiableList = FXCollections.unmodifiableObservableList(transactionList);
    }

    public TransactionBook() {
        this(new ArrayList<>());
    }

    /**
     * Creates a TransactionBook using the Transactions in the {@code existingTransactionBook}.
     */
    public TransactionBook(TransactionBook existingTransactionBook) {
        this(existingTransactionBook.transactionList);
    }

    /**
     * Creates a TransactionBook using the Transactions in the {@code toBeCopied}
     */
    public TransactionBook(ReadOnlyTransactionBook toBeCopied) {
        this();
        ObservableList<Transaction> txns = toBeCopied.getTransactionList();

        CollectionUtil.requireAllNonNull(txns);
        transactionList.setAll(txns);
    }

    /**
     * Checks if the provided transaction already has an entry in the transaction book.
     *
     * @param transaction The transaction to check against.
     * @return true if transaction exists in the book.
     */
    public boolean containsTransaction(Transaction transaction) {
        return transactionList.contains(transaction);
    }

    /**
     * Returns true if the list contains a transaction with a matching id
     */
    public boolean containsTransactionById(Transaction toCheck) {
        requireNonNull(toCheck);
        return transactionList.stream().anyMatch(toCheck::hasSameId);
    }

    /**
     * Replaces the existing data in the transaction list with the data in the replacement.
     *
     * @param replacementTransactionBook The data used to replace the current transactions.
     */
    public void setTransactionBook(ReadOnlyTransactionBook replacementTransactionBook) {
        requireNonNull(replacementTransactionBook);
        transactionList.clear();
        transactionList.addAll(replacementTransactionBook.getTransactionList());
    }

    /**
     * Replaces the transaction {@code target} in the list with {@code replacement}. {@code target} must exist in the
     * list. The txn identity of {@code replacement} must not be the same as another existing txn in the list.
     */
    public void setTransaction(Transaction target, Transaction replacement) {
        CollectionUtil.requireAllNonNull(target, replacement);
        int i = transactionList.indexOf(target);
        if (i == -1) {
            throw new TransactionNotFoundException();
        }

        if (!target.equals(replacement) && containsTransaction(replacement)) {
            throw new DuplicateTransactionException();
        }

        transactionList.set(i, replacement);
    }

    /**
     * Adds a transaction entry to the transaction list.
     *
     * @param transaction The transaction to be added.
     * @throws DuplicateTransactionException If the transaction already exists.
     */
    public void addTransaction(Transaction transaction) throws DuplicateTransactionException {
        requireNonNull(transaction);
        if (containsTransaction(transaction)) {
            throw new DuplicateTransactionException();
        }
        transactionList.add(transaction);
    }

    /**
     * Removes {@code key} from this {@code TransactionBook}. {@code key} must exist in the transaction book.
     *
     * @return whether the removal was successful
     */
    public boolean removeTransaction(Transaction key) {
        requireNonNull(key);
        return transactionList.remove(key);
    }

    /**
     * Removes all txns involving person with {@code personId}.
     */
    public void deleteTransactionsOfPersonId(String personId) {
        requireNonNull(personId);
        List<Transaction> txnList = transactionList.stream().filter((t) -> !t.isByPersonId(personId)).toList();
        transactionList.clear();
        transactionList.addAll(txnList);
    }

    @Override
    public ObservableList<Transaction> getTransactionList() {
        return transactionUnmodifiableList;
    }

    @Override
    public String toString() {
        return transactionList.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TransactionBook)) {
            return false;
        }

        TransactionBook otherTransactionBook = (TransactionBook) other;
        return transactionList.equals(otherTransactionBook.transactionList);
    }

    @Override
    public int hashCode() {
        return transactionList.hashCode();
    }
}
