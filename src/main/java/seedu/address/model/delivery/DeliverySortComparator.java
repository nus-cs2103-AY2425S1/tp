package seedu.address.model.delivery;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.SortOrder;

/**
 * Comparators for Delivery class
 */
public abstract class DeliverySortComparator implements Comparator<Delivery> {
    private final SortOrder sortOrder;

    public DeliverySortComparator(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public SortOrder getSortOrder() {
        return this.sortOrder;
    }

    public boolean getIsSortByAscending() {
        return this.sortOrder.getIsSortByAscending();
    }

    public String toSortOrderString() {
        return this.sortOrder.toString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("isSortByAscending", this.sortOrder.toString()).toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliverySortComparator)) {
            return false;
        }

        DeliverySortComparator otherDeliverySortComparator = (DeliverySortComparator) other;
        return this.getSortOrder().equals(otherDeliverySortComparator.getSortOrder());
    }

    public abstract String toSortByString();
}
