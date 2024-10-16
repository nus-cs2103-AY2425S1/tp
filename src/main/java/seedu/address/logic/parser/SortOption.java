package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.List;

/**
 * Contains valid sorting options for the list command.
 */
public class SortOption {
    public static final String SORT_NAME = "name";
    // Add more sorting options if needed

    public static final List<String> VALID_SORT_OPTIONS = Arrays.asList(
            SORT_NAME
            // Add other sorting options here
    );

    public static final String MESSAGE_CONSTRAINTS = "Invalid sort option.\nValid options are: "
            + String.join(", ", VALID_SORT_OPTIONS);

    private final String value;

    /**
     * Constructs a {@code SortOption}.
     *
     * @param option A valid sort option.
     */
    public SortOption(String option) {
        requireNonNull(option);
        checkArgument(isValidSortOption(option), MESSAGE_CONSTRAINTS);
        value = option;
    }

    /**
     * Returns true if a given string is a valid SortOption.
     */
    public static boolean isValidSortOption(String sortOption) {
        sortOption = sortOption.toLowerCase();
        return VALID_SORT_OPTIONS.contains(sortOption);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortOption)) {
            return false;
        }

        SortOption otherSortOption = (SortOption) other;
        return value.equals(otherSortOption.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
