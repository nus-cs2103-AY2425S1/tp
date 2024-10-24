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
import seedu.address.model.person.Volunteer;
import seedu.address.model.person.comparators.NameComparator;
import seedu.address.model.person.comparators.RoleComparator;

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
            return new RoleComparator<>(Volunteer.class);
        }

        @Override
        public String getRole() {
            return VOLUNTEER_ROLE;
        }

        @Override
        public Class<? extends Person> getRelatedClass() {
            return Volunteer.class;
        }
    },
    DONATION("donations") {
        @Override
        public Comparator<Person> getComparator() {
            return new RoleComparator<>(Donor.class);
        }

        @Override
        public String getRole() {
            return DONOR_ROLE;
        }

        @Override
        public Class<? extends Person> getRelatedClass() {
            return Donor.class;
        }
    },
    PARTNERSHIP_END_DATE("end_date") {
        @Override
        public Comparator<Person> getComparator() {
            return new RoleComparator<>(Partner.class);
        }

        @Override
        public String getRole() {
            return PARTNER_ROLE;
        }

        @Override
        public Class<? extends Person> getRelatedClass() {
            return Partner.class;
        }
    };
    // Add more sorting options here if needed

    public static final String MESSAGE_CONSTRAINTS = "Invalid sort option.\nValid options are: "
            + getValidSortOptionsAsString();

    public static final String MESSAGE_EMPTY_SORT_OPTION = "Sort option cannot be empty.";

    public static final String VOLUNTEER_ROLE = "volunteer";
    public static final String DONOR_ROLE = "donor";
    public static final String PARTNER_ROLE = "partner";
    public static final String PERSON_ROLE = "Person";

    private final String value;
    private final String role;
    private final Class<? extends Person> relatedClass;

    /**
     * Constructs a {@code SortOption}.
     *
     * @param value A valid sort option.
     */
    SortOption(String value) {
        this.value = value;
        role = PERSON_ROLE;
        relatedClass = Person.class;
    }

    public String getRole() {
        return role;
    }

    public Class<? extends Person> getRelatedClass() {
        return relatedClass;
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
