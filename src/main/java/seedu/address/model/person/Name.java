package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Comparator;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid name: Only alphanumeric characters (with at least one alphabet) are allowed, "
                    + "and it should not be blank. "
                    + "Hyphens (-), slashes (/), apostrophes ('), and periods (.) are allowed, "
                    + "but not at the first character.";

    public static final String MESSAGE_LENGTH_CONSTRAINTS =
            "Name cannot be more than 50 characters.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "(?=.*[a-zA-Z])[a-zA-Z0-9][a-zA-Z0-9 .\\-'/]*";

    public static final int MAX_LENGTH = 50;

    public static final Comparator<Name> NAME_COMPARATOR = Comparator.comparing(name -> name.fullName);

    public final String fullName;


    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);

        // Step 1: Trim leading and trailing spaces, and replace multiple spaces with a single space
        String trimmedName = name.trim().replaceAll("\\s+", " ");

        // Step 2: Remove spaces around hyphens, slashes, apostrophes, and periods
        trimmedName = trimmedName.replaceAll("\\s*([\\-/'\\.])\\s*", "$1");

        checkArgument(isValidName(trimmedName), MESSAGE_CONSTRAINTS);
        checkArgument(isValidLengthName(trimmedName), MESSAGE_LENGTH_CONSTRAINTS);

        fullName = trimmedName.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string have a valid length.
     */
    public static boolean isValidLengthName(String test) {
        return test.length() <= MAX_LENGTH;
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

        // Assert that fullName is not null
        assert this.fullName != null : "fullName of the current object is null";
        assert otherName.fullName != null : "fullName of the other object is null";

        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
