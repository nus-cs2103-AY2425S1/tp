package seedu.address.model.delivery;

import java.util.Comparator;

/**
 * Comparators for Delivery class
 */
public abstract class DeliverySortComparator implements Comparator<Delivery> {
    private final boolean isSortByAscending;

    public DeliverySortComparator(boolean isSortByAscending) {
        this.isSortByAscending = isSortByAscending;
    }

    public boolean getIsSortByAscending() {
        return this.isSortByAscending;
    }
}
