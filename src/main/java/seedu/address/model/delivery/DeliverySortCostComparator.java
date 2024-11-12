package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.SortOrder;

/**
 * Represents a comparator for deliveries that compares based on cost.
 */
public class DeliverySortCostComparator extends DeliverySortComparator {

    /**
     * Creates a DeliverySortCostComparator to compare the deliveries by cost in the specified {@code sortOrder}.
     */
    public DeliverySortCostComparator(SortOrder sortOrder) {
        super(sortOrder);
        assert sortOrder != null;
    }

    /**
     * Compares the cost of two deliveries based on the sort order.
     *
     * @param delivery1 the first delivery to be compared.
     * @param delivery2 the second delivery to be compared.
     * @return a negative integer, zero, or a positive integer as the first delivery's cost is less than,
     *      equal to, or greater than the second delivery's cost if the sort order is ascending.
     *      If the sort order is descending, the integer returned is inverted.
     */
    @Override
    public int compare(Delivery delivery1, Delivery delivery2) {
        requireNonNull(delivery1);
        requireNonNull(delivery2);
        assert delivery1.getDeliveryCost() != null;
        assert delivery2.getDeliveryCost() != null;
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
