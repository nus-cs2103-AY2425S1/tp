package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Donor;
import seedu.address.model.person.Partner;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.person.Volunteer;
import seedu.address.model.person.comparators.NameComparator;
import seedu.address.model.person.comparators.RoleComparator;

/**
 * Contains valid sorting options for the sort command.
 */
public enum SortOption {
    NAME("name", Role.PERSON) {
        @Override
        public Comparator<Person> getComparator() {
            return new NameComparator();
        }
    },
    HOURS("hours", Role.VOLUNTEER) {
        @Override
        public Comparator<Person> getComparator() {
            return new RoleComparator<>(Volunteer.class);
        }
    },
    DONATION("donations", Role.DONOR) {
        @Override
        public Comparator<Person> getComparator() {
            return new RoleComparator<>(Donor.class);
        }
    },
    PARTNERSHIP_END_DATE("end_date", Role.PARTNER) {
        @Override
        public Comparator<Person> getComparator() {
            return new RoleComparator<>(Partner.class);
        }
    };
    // Add more sorting options here if needed

    public static final String MESSAGE_CONSTRAINTS = "Invalid sort option.\nValid options are: "
            + getValidSortOptionsAsString();

    public static final String MESSAGE_EMPTY_SORT_OPTION = "Sort option cannot be empty.";

    private final String value;
    private final Role role;

    /**
     * Constructs a {@code SortOption}.
     *
     * @param value A valid sort option.
     * @param role A valid Role associated to the sort option
     */
    SortOption(String value, Role role) {
        requireNonNull(value);
        requireNonNull(role);
        this.value = value;
        this.role = role;
    }

    /*
     * Returns the Role associated to the sort option as a string
     */
    public String getRoleAsString() {
        return role.toLowerCaseString();
    }

    /*
     * Returns the class related to the sort option
     */
    public Class<? extends Person> getRelatedClass() {
        return role.getRelatedClass();
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
            if (option.matchesValue(sortOption)) {
                return option;
            }
        }
        throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
    }

    /**
     * Checks if the value of this {@code SortOption} matches the given string, case-insensitively.
     *
     * @param sortOption the string to compare with.
     * @return {@code true} if the value matches the string, {@code false} otherwise.
     */
    private boolean matchesValue(String sortOption) {
        return value.equalsIgnoreCase(sortOption);
    }

    /**
     * Returns the appropriate comparator for the sort option.
     *
     * @return {@code Comparator<Person>} based on the sort option.
     */
    public abstract Comparator<Person> getComparator();

    /**
     * Returns an unmodifiable list of valid sort option strings.
     *
     * @return List of valid sort options.
     */
    private static List<String> getValidSortOptions() {
        return Collections.unmodifiableList(
                Arrays.stream(SortOption.values())
                        .map(SortOption::toString)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Returns a comma-separated string of valid sort options.
     *
     * @return String of valid sort options.
     */
    private static String getValidSortOptionsAsString() {
        return String.join(", ", getValidSortOptions());
    }

    @Override
    public String toString() {
        return value;
    }
}
