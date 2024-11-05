package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNameWithoutNumber(String)}
 */
public class NameWithoutNumber {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should not be blank, "
                    + "should only have one space between words, and each part may contain an apostrophe within it. "
                    + "The maximum character limit is 747.";


    /*
     * The first character of the name must be alphabetic, and the rest
     * can only be alphabetic characters or spaces.
     */
    public static final String VALIDATION_REGEX = "[a-zA-Z]+([ '][a-zA-Z]+)*( (?i)s/o|d/o)?([ '][a-zA-Z]+)*";




    public final String fullName;

    /**
     * Constructs a {@code NameWithoutNumber}.
     *
     * @param name A valid name without numbers.
     */
    public NameWithoutNumber(String name) {
        requireNonNull(name);
        checkArgument(isValidNameWithoutNumber(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name without numbers.
     */
    public static boolean isValidNameWithoutNumber(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= 747;
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
        if (!(other instanceof NameWithoutNumber)) {
            return false;
        }

        NameWithoutNumber otherName = (NameWithoutNumber) other;
        return Objects.equals(fullName, otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
}
