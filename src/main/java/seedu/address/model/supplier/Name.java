package seedu.address.model.supplier;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Supplier's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {
    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^/][^/]*";

    public final String fullName;
    private final String normalizedName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);

        fullName = name.trim().replaceAll("\\s+", " ");

        normalizedName = fullName.toLowerCase();

        if (!isValidName(name)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getNormalizedName() {
        return normalizedName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Name
                && normalizedName.equals(((Name) other).normalizedName));
    }

    @Override
    public int hashCode() {
        return normalizedName.hashCode();
    }

    @Override
    public String toString() {
        return fullName;
    }
}
