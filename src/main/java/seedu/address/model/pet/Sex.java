package seedu.address.model.pet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Pet's sex in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSex(String)}
 */
public class Sex {

    public static final String MESSAGE_CONSTRAINTS =
            "Pet sex should be either 'M' or 'F' (case insensitive for Male and Female)";
    public static final String VALIDATION_REGEX = "^[mMfF]$";

    public final String sex;

    /**
     * Constructs a {@code Sex}.
     *
     * @param sex A valid sex.
     */
    public Sex(String sex) {
        requireNonNull(sex);
        checkArgument(isValidSex(sex), MESSAGE_CONSTRAINTS);
        this.sex = sex;
    }

    /**
     * Returns true if a given string is a valid sex.
     */
    public static boolean isValidSex(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return sex;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Sex)) {
            return false;
        }

        Sex otherSex = (Sex) other;
        return sex.equals(otherSex.sex);
    }

    @Override
    public int hashCode() {
        return sex.hashCode();
    }

}
