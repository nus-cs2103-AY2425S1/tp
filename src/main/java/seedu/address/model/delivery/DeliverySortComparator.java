package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.SortOrder;

/**
 * Represents a delivery comparator.
 */
public abstract class DeliverySortComparator implements Comparator<Delivery> {
    private final SortOrder sortOrder;

    /**
     * Creates a DeliverySortComparator to sort the deliveries in the specified {@code sortOrder}.
     */
    public DeliverySortComparator(SortOrder sortOrder) {
        requireNonNull(sortOrder);
        this.sortOrder = sortOrder;
    }

    public boolean getIsSortByAscending() {
        return this.sortOrder.getIsSortByAscending();
    }

    /**
     * Returns the toString of the sort order.
     */
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
        return this.sortOrder.equals(otherDeliverySortComparator.sortOrder);
    }

    public abstract String toSortByString();
}
