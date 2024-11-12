package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.SortOrder;

/**
 * Represents a comparator for deliveries that compares based on status.
 */
public class DeliverySortStatusComparator extends DeliverySortComparator {

    /**
     * Creates a DeliverySortStatusComparator to compare the deliveries by status in the specified {@code sortOrder}.
     */
    public DeliverySortStatusComparator(SortOrder sortOrder) {
        super(sortOrder);
        assert sortOrder != null;
    }

    /**
     * Compares the status of two deliveries based on the sort order.
     *
     * @param delivery1 the first delivery to be compared.
     * @param delivery2 the second delivery to be compared.
     * @return a negative integer, zero, or a positive integer as the first delivery's status is less than,
     *      equal to, or greater than the second delivery's status alphabetically if the sort order is ascending.
     *      If the sort order is descending, the integer returned is inverted.
     */
    @Override
    public int compare(Delivery delivery1, Delivery delivery2) {
        requireNonNull(delivery1);
        requireNonNull(delivery2);
        assert delivery1.getDeliveryStatus() != null;
        assert delivery2.getDeliveryStatus() != null;
        Status delivery1Status = delivery1.getDeliveryStatus();
        Status delivery2Status = delivery2.getDeliveryStatus();
        if (super.getIsSortByAscending()) {
            return Integer.compare(getStatusOrder(delivery1Status), getStatusOrder(delivery2Status));
        } else {
            return Integer.compare(getStatusOrder(delivery2Status), getStatusOrder(delivery1Status));
        }
    }

    /**
     * Returns an integer corresponding to the {@code status} based on ascending alphabetical order.
     */
    private int getStatusOrder(Status status) {
        switch (status) {
        case CANCELLED:
            return 0;
        case DELIVERED:
            return 1;
        case PENDING:
            return 2;
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
