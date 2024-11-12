package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.SortOrder;

/**
 * Represents a comparator for deliveries that compares based on date and time.
 */
public class DeliverySortDateTimeComparator extends DeliverySortComparator {

    /**
     * Creates a DeliverySortDateTimeComparator to compare the deliveries by date and time
     *      in the specified {@code sortOrder}.
     */
    public DeliverySortDateTimeComparator(SortOrder sortOrder) {
        super(sortOrder);
        assert sortOrder != null;
    }

    /**
     * Compares the date and time of two deliveries based on the sort order.
     *
     * @param delivery1 The first delivery to be compared.
     * @param delivery2 The second delivery to be compared.
     * @return A negative integer, zero, or a positive integer as the first delivery's date and time is less than,
     *         equal to, or greater than the second delivery's date and time if the sort order is ascending.
     *         If the sort order is descending, the integer returned is inverted.
     */
    @Override
    public int compare(Delivery delivery1, Delivery delivery2) {
        requireNonNull(delivery1);
        requireNonNull(delivery2);
        assert delivery1.getDeliveryDate() != null;
        assert delivery2.getDeliveryDate() != null;
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
