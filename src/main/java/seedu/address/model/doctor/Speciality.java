package seedu.address.model.doctor;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Doctor's speciality in the contacts book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSpeciality(String)}
 */
public class Speciality {

    public static final String MESSAGE_CONSTRAINTS =
        "Speciality can take any sequence of alphabetic character, and it should not be blank";

    /*
     * Matches a sequence of alphabetic characters.
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z]+$";

    public final String value;

    /**
     * Constructs a {@code Speciality}.
     *
     * @param input A valid speciality.
     */
    public Speciality(String input) {
        requireNonNull(input);
        checkArgument(isValidSpeciality(input), MESSAGE_CONSTRAINTS);
        value = input;
    }

    /**
     * Returns true if a given string is a valid speciality.
     */
    public static boolean isValidSpeciality(String test) {
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
        if (!(other instanceof Speciality)) {
            return false;
        }

        Speciality otherSpeciality = (Speciality) other;
        return value.equals(otherSpeciality.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
