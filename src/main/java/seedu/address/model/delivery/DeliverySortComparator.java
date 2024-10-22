package seedu.address.model.delivery;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.SortOrder;

/**
 * Comparators for Delivery class
 */
public abstract class DeliverySortComparator implements Comparator<Delivery> {
    private final boolean isSortByAscending;

    public DeliverySortComparator(SortOrder sortOrder) {
        this.isSortByAscending = sortOrder.getSortOrder();
    }

    public boolean getIsSortByAscending() {
        return this.isSortByAscending;
    }

    public String toSortOrderString() {
        return isSortByAscending ? "ascending" : "descending";
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("isSortByAscending", isSortByAscending).toString();
    }
}
