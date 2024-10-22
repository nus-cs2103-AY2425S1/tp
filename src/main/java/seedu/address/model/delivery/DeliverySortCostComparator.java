package seedu.address.model.delivery;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.SortOrder;

public class DeliverySortCostComparator extends DeliverySortComparator {
    public DeliverySortCostComparator(SortOrder sortOrder) {
        super(sortOrder.getSortOrder());
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

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("isSortByAscending", super.getIsSortByAscending()).toString();
    }
}
