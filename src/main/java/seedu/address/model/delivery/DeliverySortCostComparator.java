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
        double delivery1Cost = Double.parseDouble(delivery1.getDeliveryCost().value);
        double delivery2Cost = Double.parseDouble(delivery2.getDeliveryCost().value);
        if (super.getIsSortByAscending()) {
            return Double.compare(delivery1Cost, delivery2Cost);
        } else {
            return Double.compare(delivery2Cost, delivery1Cost);
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
