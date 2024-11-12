package seedu.hiredfiredpro.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.hiredfiredpro.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in HiredFiredPro.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Error: Invalid name. Please enter a valid first and last name.";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if both names ignoring case are the same.
     * This defines a case-insensitive notion of equality between two names.
     */
    public boolean isSameName(Name otherName) {
        if (otherName == this) {
            return true;
        }

        return otherName != null
                && removeExtraSpaces(this.fullName)
                    .equalsIgnoreCase(removeExtraSpaces(otherName.fullName));
    }

    /**
     * Reduces multiple spaces between words to a single space.
     */
    private String removeExtraSpaces(String name) {
        return name.trim().replaceAll("\\s+", " ");
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
