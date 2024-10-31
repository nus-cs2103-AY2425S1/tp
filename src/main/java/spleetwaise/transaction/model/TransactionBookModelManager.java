package spleetwaise.transaction.model;

import static java.util.Objects.requireNonNull;
import static spleetwaise.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import spleetwaise.address.commons.core.LogsCenter;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * Represents the in-memory model of the transaction data.
 */
public class TransactionBookModelManager implements TransactionBookModel {

    private static final Logger logger = LogsCenter.getLogger(TransactionBookModelManager.class);
    private Predicate<Transaction> currPredicate = TransactionBookModel.PREDICATE_SHOW_ALL_TXNS;

    private final TransactionBook transactionBook;
    private final FilteredList<Transaction> filteredTransactions;

    /**
     * Initializes a TransactionBookModelManager with the given transactionBook.
     */
    public TransactionBookModelManager(TransactionBook transactionBook) {
        requireNonNull(transactionBook);

        logger.fine("Initializing Transaction Model...");

        this.transactionBook = new TransactionBook(transactionBook);
        filteredTransactions = new FilteredList<>(this.transactionBook.getTransactionList());
    }

    /**
     * Initializes a TransactionBookModelManager with the readonly transactionBook.
     */
    public TransactionBookModelManager(ReadOnlyTransactionBook transactionBook) {
        requireNonNull(transactionBook);

        logger.fine("Initializing Transaction Model...");

        this.transactionBook = new TransactionBook(transactionBook);
        filteredTransactions = new FilteredList<>(this.transactionBook.getTransactionList());
    }

    public TransactionBookModelManager() {
        this(new TransactionBook());
    }

    @Override
    public ReadOnlyTransactionBook getTransactionBook() {
        return transactionBook;
    }

    @Override
    public void setTransactionBook(ReadOnlyTransactionBook replacementBook) {
        requireNonNull(replacementBook);
        transactionBook.setTransactionBook(replacementBook);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactionBook.addTransaction(transaction);
        updateFilteredTransactionList();
    }

    @Override
    public boolean hasTransaction(Transaction transaction) {
        requireNonNull(transaction);
        return transactionBook.containsTransaction(transaction);
    }

    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return filteredTransactions;
    }

    @Override
    public void updateFilteredTransactionList() {
        filteredTransactions.setPredicate(currPredicate);
    }

    @Override
    public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
        currPredicate = predicate;
        filteredTransactions.setPredicate(predicate);
    }

    @Override
    public void deleteTransactionsOfPersonId(String personId) {
        requireNonNull(personId);
        transactionBook.deleteTransactionsOfPersonId(personId);
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
        requireNonNull(transaction);
        transactionBook.removeTransaction(transaction);
        updateFilteredTransactionList();
    }

    @Override
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireAllNonNull(target, editedTransaction);

        transactionBook.setTransaction(target, editedTransaction);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionBookModel)) {
            return false;
        }

        TransactionBookModelManager otherModelManager = (TransactionBookModelManager) other;
        return transactionBook.equals(otherModelManager.transactionBook)
                && filteredTransactions.equals(otherModelManager.filteredTransactions);
    }

}
