package seedu.address.logic.parser;

import java.util.Objects;

/**
 * Represents the sorting order of a sort command.
 */
public class SortOrder {

    public static final String MESSAGE_CONSTRAINTS =
            "Sort order should be 'a' for ascending or 'd' for descending, and it should not be blank";

    /**
     * Sort order must be 'a' or 'd'.
     */
    public static final String VALIDATION_REGEX = "^[ad]$";
    private final boolean isSortByAscending;

    /**
     * Creates a SortOrder object with the corresponding sorting order.
     * sortOrder is checked before calling this constructor.
     * @param sortOrder Represents ascending with 'a' and descending with 'd'.
     */
    public SortOrder(String sortOrder) {
        if (sortOrder.equals("a")) {
            this.isSortByAscending = true;
        } else {
            this.isSortByAscending = false;
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
