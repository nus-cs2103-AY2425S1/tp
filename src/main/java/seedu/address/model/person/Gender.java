package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's gender in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {

    public static final String MESSAGE_CONSTRAINTS =
            "Gender should only be 'F' / 'f' (Female) or 'M' / 'm' (Male)";

    /*
     * The gender must be either 'F', 'f', 'M' or 'm'.
     */
    public static final String VALIDATION_REGEX = "^[FfMm]$";

    public final String value;

    /**
     * Constructs a {@code Gender}.
     *
     * @param gender A valid gender ('F', 'f', 'M' or 'm').
     *               'f' is stored as 'F'; 'm' is stored as 'M'.
     */
    public Gender(String gender) {
        requireNonNull(gender);
        checkArgument(isValidGender(gender), MESSAGE_CONSTRAINTS);
        this.value = gender.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid gender.
     */
    public static boolean isValidGender(String test) {
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
        if (!(other instanceof Gender)) {
            return false;
        }
        Gender otherGender = (Gender) other;
        return value.equals(otherGender.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
