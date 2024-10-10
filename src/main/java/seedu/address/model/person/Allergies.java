package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Patient's allergies in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAllergies(String)}
 */
public class Allergies {
    public static final String MESSAGE_CONSTRAINTS = "Allergies can take any values";
    public static final String VALIDATION_REGEX = "[^\\s]*";

    public final String value;

    /**
     * Constructs an {@code Allergies}.
     *
     * @param allergies A valid allergies.
     */
    public Allergies(String allergies) {
        checkArgument(isValidAllergies(allergies), MESSAGE_CONSTRAINTS);
        value = allergies;
    }

    /**
     * Returns true if a given string is a valid allergies.
     */
    public static boolean isValidAllergies(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Allergies)) {
            return false;
        }

        Allergies otherAllergies = (Allergies) other;
        return value.equals(otherAllergies.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
