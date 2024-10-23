package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.List;

/**
 * Contains valid sorting options for the sort command.
 */
public enum SortOption {
    NAME("name"),
    HOURS("hours");
    // Add more sorting options if needed

    public static final List<String> VALID_SORT_OPTIONS = List.of(NAME.value, HOURS.value);

    public static final String MESSAGE_CONSTRAINTS = "Invalid sort option.\nValid options are: "
            + String.join(", ", VALID_SORT_OPTIONS);

    public static final String MESSAGE_EMPTY_SORT_OPTION = "Sort option cannot be empty.";

    private final String value;

    /**
     * Constructs a {@code SortOption}.
     *
     * @param value A valid sort option.
     */
    SortOption(String value) {
        this.value = value;
    }

    /**
     * Parses a {@code String sortOption} into a {@code SortOption}.
     * Leading and trailing whitespaces are trimmed.
     *
     * @param sortOption The sort option string to parse.
     * @return The corresponding {@code SortOption}.
     * @throws IllegalArgumentException If the sort option is invalid.
     */
    public static SortOption fromString(String sortOption) {
        requireNonNull(sortOption);
        sortOption = sortOption.toLowerCase();
        for (SortOption option : SortOption.values()) {
            if (option.value.equalsIgnoreCase(sortOption)) {
                return option;
            }
        }
        throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
    }

    @Override
    public String toString() {
        return value;
    }
}
