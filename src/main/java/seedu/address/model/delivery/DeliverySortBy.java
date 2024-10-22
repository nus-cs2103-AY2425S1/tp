package seedu.address.model.delivery;

import java.util.Objects;

import seedu.address.logic.parser.SortOrder;

/**
 * Represents the sorting order of a sort command.
 */
public class DeliverySortBy {
    /**
     * Represents the fields to sort by
     * 'C' represents cost, 'D' represents dateTime, and 'S' represents status
     */
    public enum SortBy {
        C, D, S;
    }

    public static final String MESSAGE_CONSTRAINTS = "Sort order should be 's' for status, 'd' for datetime or "
            + "'q' for quantity, and it should not be blank";

    /**
     * Sort order must be 'a' or 'd'.
     */
    public static final String VALIDATION_REGEX = "^[cds]$";

    private final SortBy sortBy;

    /**
     * Creates a DeliverySortBy object with the corresponding field to sort by.
     * sortBy is checked before calling this constructor.
     * @param sortBy Represents status with 's', dateTime with 'd', and cost with 'c'.
     */
    public DeliverySortBy(String sortBy) {
        switch (SortBy.valueOf(sortBy.toUpperCase())) {
        case C:
            this.sortBy = SortBy.C;
            break;
        case D:
            this.sortBy = SortBy.D;
            break;
        case S:
            this.sortBy = SortBy.S;
            break;
        default:
            this.sortBy = SortBy.S;
        }
    }

    public static DeliverySortComparator getDeliverySortComparator(SortOrder sortOrder, DeliverySortBy deliverySortBy) {
        switch (deliverySortBy.getSortBy()) {
        case C:
            return new DeliverySortCostComparator(sortOrder);
        case D:
            return new DeliverySortDateTimeComparator(sortOrder);
        case S:
            return new DeliverySortStatusComparator(sortOrder);
        default:
            return null;
        }
    }

    public SortBy getSortBy() {
        return sortBy;
    }

    /**
     * Returns true if a given string is a valid sort order
     */
    public static boolean isValidSortBy(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        switch (sortBy) {
        case C:
            return "cost";
        case D:
            return "date time";
        case S:
            return "status";
        default:
            return "ERROR";
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(sortBy);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliverySortBy)) {
            return false;
        }

        DeliverySortBy otherDeliverySortBy = (DeliverySortBy) other;
        return sortBy.equals(otherDeliverySortBy.getSortBy());
    }
}
