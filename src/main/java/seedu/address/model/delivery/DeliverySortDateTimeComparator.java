package seedu.address.model.delivery;

import seedu.address.logic.parser.SortOrder;

/**
 * Represents a comparator for deliveries that sort based on cost.
 */
public class DeliverySortDateTimeComparator extends DeliverySortComparator {
    public DeliverySortDateTimeComparator(SortOrder sortOrder) {
        super(sortOrder);
    }

    @Override
    public int compare(Delivery delivery1, Delivery delivery2) {
        if (super.getIsSortByAscending()) {
            return delivery1.getDeliveryDate().compareTo(delivery2.getDeliveryDate());
        } else {
            return delivery2.getDeliveryDate().compareTo(delivery1.getDeliveryDate());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliverySortDateTimeComparator)) {
            return false;
        }

        DeliverySortComparator otherDeliverySortComparator = (DeliverySortComparator) other;
        return super.equals(otherDeliverySortComparator);
    }

    @Override
    public String toSortByString() {
        return "date time";
    }
}
