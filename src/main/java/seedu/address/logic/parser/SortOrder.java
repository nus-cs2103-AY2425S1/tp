package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents the sorting order of a sort command.
 */
public class SortOrder {
    /**
     * Represents the sorting order.
     * 'A' represents ascending and 'D' represents descending.
     */
    public enum SortOrderEnum {
        A, D;
    }

    public static final String MESSAGE_CONSTRAINTS =
            "Sort order should be 'a' for ascending or 'd' for descending, and it should not be blank";

    /**
     * Sort order must be 'a' or 'd'.
     */
    public static final String VALIDATION_REGEX = "^[ad]$";
    private final boolean isSortByAscending;

    /**
     * Creates a SortOrder object with the corresponding sorting order.
     *
     * @param sortOrder Represents ascending with 'a' and descending with 'd'.
     */
    public SortOrder(String sortOrder) {
        requireNonNull(sortOrder);
        checkArgument(isValidSortOrder(sortOrder), MESSAGE_CONSTRAINTS);
        switch (SortOrderEnum.valueOf(sortOrder.toUpperCase())) {
        case A:
            this.isSortByAscending = true;
            break;
        case D:
            this.isSortByAscending = false;
            break;
        default:
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if sort order is ascending else false
     */
    public boolean getIsSortByAscending() {
        return isSortByAscending;
    }

    /**
     * Returns true if a given string is a valid sort order
     */
    public static boolean isValidSortOrder(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return getIsSortByAscending() ? "ascending" : "descending";
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSortByAscending);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortOrder)) {
            return false;
        }

        SortOrder otherSortOrder = (SortOrder) other;
        return isSortByAscending == otherSortOrder.isSortByAscending;
    }
}
