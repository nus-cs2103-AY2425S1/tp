package seedu.address.model.tut;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the name of a Tutorial in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTutName(String)}
 */
public class TutName {

    /** Error message for invalid tutorial names. */
    public static final String MESSAGE_CONSTRAINTS = "Tutorial names should only contain alphanumeric"
            + " characters and spaces, and it should not be blank.";

    /** Regular expression to validate tutorial names that only contain alphanumeric characters. */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}]+( [\\p{Alnum}]+)*";

    /** The tutorial name of the tutorial. */
    public final String tutName;

    /**
     * Constructs a {@code TutName}.
     *
     * @param name A valid tutorial name.
     */
    public TutName(String name) {
        requireNonNull(name);
        checkArgument(isValidTutName(name), MESSAGE_CONSTRAINTS);
        tutName = name;
    }

    /**
     * Returns true if a given string is a valid tutorial name.
     *
     * @param test The string to validate.
     * @return True if the string matches both the alphanumeric regex and the validation regex.
     */
    public static boolean isValidTutName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the tutorial name as a string.
     *
     * @return The string representation of the tutorial name.
     */
    @Override
    public String toString() {
        return tutName;
    }

    /**
     * Returns true if this tutorial name is equal to another object.
     *
     * @param other The object to compare to.
     * @return True if the other object is an instance of TutName and has the same name.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TutName otherName)) {
            return false;
        }

        return tutName.equals(otherName.tutName);
    }

    /**
     * Returns the hash code for this tutorial name.
     *
     * @return The hash code of the tutorial name.
     */
    @Override
    public int hashCode() {
        return tutName.hashCode();
    }
}
