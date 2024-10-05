package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's gender in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {

    public static final String MESSAGE_CONSTRAINTS =
            "Gender should only be male or female. And should not be empty";

    /*
     * The first character of the gender should only be "male" or "female"
     */
    public static final String VALIDATION_REGEX = "^(male|female)$";

    public final String gender;

    /**
     * Constructs a {@code Gender}.
     *
     * @param gender A valid gender.
     */
    public Gender(String gender) {
        requireNonNull(gender);
        checkArgument(isValidGender(gender), MESSAGE_CONSTRAINTS);
        this.gender = gender;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidGender(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return gender;
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
        return gender.equals(otherGender.gender);
    }

    @Override
    public int hashCode() {
        return gender.hashCode();
    }

}
