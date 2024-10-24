package seedu.address.model.person;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.SortOrder;

/**
 * Comparators for Supplier class
 */
public abstract class SupplierSortComparator implements Comparator<Person> {
    private final SortOrder sortOrder;

    public SupplierSortComparator(SortOrder sortOrder) {
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
        if (!(other instanceof SupplierSortComparator)) {
            return false;
        }

        SupplierSortComparator otherSupplierSortComparator = (SupplierSortComparator) other;
        return this.getSortOrder().equals(otherSupplierSortComparator.getSortOrder());
    }

    public abstract String toSortByString();
}
