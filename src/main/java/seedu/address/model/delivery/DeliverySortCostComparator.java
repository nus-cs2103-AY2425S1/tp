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
        float delivery1Cost = Float.parseFloat(delivery1.getDeliveryCost().value);
        float delivery2Cost = Float.parseFloat(delivery2.getDeliveryCost().value);
        if (super.getIsSortByAscending()) {
            return Float.compare(delivery1Cost, delivery2Cost);
        } else {
            return Float.compare(delivery2Cost, delivery1Cost);
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

        DeliverySortCostComparator otherDeliverySortCostComparator = (DeliverySortCostComparator) other;
        return super.getIsSortByAscending() == otherDeliverySortCostComparator.getIsSortByAscending();
    }
}
