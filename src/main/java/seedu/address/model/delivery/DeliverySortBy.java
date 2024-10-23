package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

import seedu.address.logic.parser.SortOrder;

/**
 * Represents the field to sort by in a sort delivery command.
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
     * Field to sort by must be 'c', 'd' or 's'.
     */
    public static final String VALIDATION_REGEX = "^[cds]$";

    private final SortBy sortBy;

    /**
     * Creates a DeliverySortBy object with the corresponding field to sort by.
     * @param sortBy Represents status with 's', dateTime with 'd', and cost with 'c'.
     */
    public DeliverySortBy(String sortBy) {
        requireNonNull(sortBy);
        checkArgument(isValidSortBy(sortBy), MESSAGE_CONSTRAINTS);
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
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns the corresponding comparator of {@code sortBy} which compares by the {@code sortOrder}.
     */
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
     * Returns true if a given string is a valid field to sort by.
     */
    public static boolean isValidSortBy(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the corresponding field that is being sorted by.
     */
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
