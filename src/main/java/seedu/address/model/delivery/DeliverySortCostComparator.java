package seedu.address.model.delivery;

import seedu.address.logic.parser.SortOrder;

/**
 * Represents a comparator for deliveries that sort based on cost.
 */
public class DeliverySortCostComparator extends DeliverySortComparator {
    public DeliverySortCostComparator(SortOrder sortOrder) {
        super(sortOrder);
    }

    @Override
    public int compare(Delivery delivery1, Delivery delivery2) {
        if (super.getIsSortByAscending()) {
            return delivery1.getDeliveryCost().compareTo(delivery2.getDeliveryCost());
        } else {
            return delivery2.getDeliveryCost().compareTo(delivery1.getDeliveryCost());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliverySortCostComparator)) {
            return false;
        }

        DeliverySortComparator otherDeliverySortComparator = (DeliverySortComparator) other;
        return super.equals(otherDeliverySortComparator);
    }

    @Override
    public String toSortByString() {
        return "cost";
    }
}
