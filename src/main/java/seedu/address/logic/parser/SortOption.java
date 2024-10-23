package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.Person;
import seedu.address.model.person.comparators.NameComparator;
import seedu.address.model.person.comparators.VolunteerComparator;

/**
 * Contains valid sorting options for the sort command.
 */
public enum SortOption {
    NAME("name") {
        @Override
        public Comparator<Person> getComparator() {
            return new NameComparator();
        }
    },
    HOURS("hours") {
        @Override
        public Comparator<Person> getComparator() {
            return new VolunteerComparator();
        }
    };
    // Add more sorting options here if needed

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

    /**
     * Returns the appropriate comparator for the sort option.
     *
     * @return {@code Comparator<Person>} based on the sort option.
     */
    public abstract Comparator<Person> getComparator();

    @Override
    public String toString() {
        return value;
    }
}
