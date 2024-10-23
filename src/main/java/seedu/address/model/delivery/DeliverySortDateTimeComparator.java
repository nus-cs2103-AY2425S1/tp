package seedu.address.model.delivery;

import java.time.LocalDateTime;

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
        LocalDateTime delivery1DateTime = delivery1.getDeliveryDate().time;
        LocalDateTime delivery2DateTime = delivery2.getDeliveryDate().time;
        if (super.getIsSortByAscending()) {
            return delivery1DateTime.compareTo(delivery2DateTime);
        } else {
            return delivery2DateTime.compareTo(delivery1DateTime);
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
