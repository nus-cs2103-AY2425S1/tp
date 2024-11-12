package seedu.address.model.client;

import java.util.Comparator;

/**
 * Comparator that compares transactions by date.
 */
public class TransactionDateComparator implements Comparator<Transaction> {
    /**
     * Compares two transactions by date and returns the comparator value which is a negative integer, zero, or
     * a positive integer as the date of t1 is more recent than, equal to, or less recent than the date of t2.
     *
     * @param t1 The first transaction to be compared.
     * @param t2 The second transaction to be compared.
     * @return The comparator value.
     */
    @Override
    public int compare(Transaction t1, Transaction t2) {
        return -t1.getDate().compareTo(t2.getDate());
    }
}
