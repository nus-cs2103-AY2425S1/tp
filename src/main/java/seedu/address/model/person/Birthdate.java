package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's BirthDate in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthdate(String)}
 */
public class Birthdate {

    public static final String MESSAGE_CONSTRAINTS =
            "Birth Date should follow the format YYYY/MM/DD";

    public final String value;

    /**
     * Constructs an {@code BirthDate}.
     *
     * @param birthdate A valid birthdate.
     */
    public Birthdate(String birthdate) {
        requireNonNull(birthdate);
        value = birthdate;
    }

    /**
     * Returns if a given string is a valid nric.
     */
    public static boolean isValidBirthdate(String test) {
        return true;
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
        if (!(other instanceof Birthdate)) {
            return false;
        }

        Birthdate otherBirthdate = (Birthdate) other;
        return value.equals(otherBirthdate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
