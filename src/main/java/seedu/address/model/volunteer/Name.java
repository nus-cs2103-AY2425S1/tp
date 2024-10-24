package seedu.address.model.volunteer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

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
        String trimmedName = name.trim();
        checkArgument(isValidName(trimmedName), MESSAGE_CONSTRAINTS);
        fullName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
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
        return fullName.equalsIgnoreCase(otherName.fullName);
    }

    /**
     * Returns true if both names are the same, ignoring case and leading/trailing spaces.
     * This method compares the full name of both {@code Name} objects after trimming whitespace
     * and performs a case-insensitive comparison.
     *
     * @param otherName The other {@code Name} to compare with.
     * @return true if both names are the same (ignoring case and whitespace), false otherwise.
     */
    public boolean isSameName(Name otherName) {
        if (otherName == this) {
            return true; // same object
        }
        if (otherName == null) {
            return false; // null check
        }
        return this.fullName.trim().equalsIgnoreCase(otherName.fullName.trim());
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
