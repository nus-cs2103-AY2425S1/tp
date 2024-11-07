package seedu.address.model.person;

import java.util.Comparator;

/**
 * Comparator that compares transactions by date.
 */
public class TransactionDateComparator implements Comparator<Transaction> {
    /**
     * Compares two transactions by date.
     * @param t1 the first transaction to be compared.
     * @param t2 the second transaction to be compared.
     * @return  the comparator value which is a negative integer, zero, or a positive integer
     *     as the date of t1 is more recent than, equal to, or less recent than the date of t2.
     */
    @Override
    public int compare(Transaction t1, Transaction t2) {
        return -t1.getDate().compareTo(t2.getDate());
    }
}
