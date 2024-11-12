package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphabets, hyphens, dots, commas, forward slash "
                    + "and spaces, and be between 1 and 100 characters.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^(?=.*[\\p{L}\\p{N}])[\\p{L}\\-\\., /]{1,100}$";
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 100;

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        checkArgument(isValidLength(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Checks the length of the name is valid.
     * @param test the name to be tested.
     * @return the result of the test.
     */
    private boolean isValidLength(String test) {
        if (test.trim().isEmpty()) {
            return false;
        } else {
            return test.length() <= MAX_LENGTH;
        }
    }

    @Override
    public String toString() {
        return fullName;
    }

    public boolean isSameName(Name otherName) {
        return this.fullName.equalsIgnoreCase(otherName.fullName);
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
