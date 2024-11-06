package seedu.address.model.delivery;

import seedu.address.logic.parser.SortOrder;

/**
 * Represents a comparator for deliveries that sort based on status then dateTime.
 */
public class DeliverySortStatusComparator extends DeliverySortComparator {

    public DeliverySortStatusComparator(SortOrder sortOrder) {
        super(sortOrder);
    }

    /**
     * Compares two delivery object by status, returning the order of CANCELLED -> DELIVERED -> PENDING
     */
    @Override
    public int compare(Delivery delivery1, Delivery delivery2) {
        Status delivery1Status = delivery1.getDeliveryStatus();
        Status delivery2Status = delivery2.getDeliveryStatus();
        if (super.getIsSortByAscending()) {
            return Integer.compare(getStatusOrder(delivery1Status), getStatusOrder(delivery2Status));
        } else {
            return Integer.compare(getStatusOrder(delivery2Status), getStatusOrder(delivery1Status));
        }
    }

    /**
     * Returns the order of the status in the order of CANCELLED -> DELIVERED -> PENDING (Alphabetical).
     * The integer returned increases in the order.
     */
    private int getStatusOrder(Status status) {
        switch (status) {
        case CANCELLED:
            return 0;
        case PENDING:
            return 2;
        case DELIVERED:
            return 1;
        default:
            throw new IllegalArgumentException("Unknown status: " + status);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliverySortStatusComparator)) {
            return false;
        }

        DeliverySortComparator otherDeliverySortComparator = (DeliverySortComparator) other;
        return super.equals(otherDeliverySortComparator);
    }

    @Override
    public String toSortByString() {
        return "status";
    }
}
