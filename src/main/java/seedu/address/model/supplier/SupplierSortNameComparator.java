package seedu.address.model.supplier;

import seedu.address.logic.parser.SortOrder;

/**
 * Represents a comparator for suppliers that sort based on name.
 */
public class SupplierSortNameComparator extends SupplierSortComparator {
    public SupplierSortNameComparator(SortOrder sortOrder) {
        super(sortOrder);
    }

    @Override
    public int compare(Supplier supplier1, Supplier supplier2) {
        String supplier1Name = supplier1.getName().getName().toLowerCase();
        String supplier2Name = supplier2.getName().getName().toLowerCase();
        if (super.getIsSortByAscending()) {
            return supplier1Name.compareTo(supplier2Name);
        } else {
            return supplier2Name.compareTo(supplier1Name);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SupplierSortNameComparator)) {
            return false;
        }

        SupplierSortComparator otherSupplierSortComparator = (SupplierSortComparator) other;
        return super.equals(otherSupplierSortComparator);
    }

    @Override
    public String toSortByString() {
        return "name";
    }
}
