package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's Sex in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSex(String)}
 */
public class Sex {

    public static final String MESSAGE_CONSTRAINTS =
            "Sex should either be uppercase characters M or F";

    public static final String VALIDATION_REGEX = "^[MF]$";

    public final String value;

    /**
     * Constructs an {@code Sex}.
     *
     * @param sex A valid sex.
     */
    public Sex(String sex) {
        requireNonNull(sex);
        checkArgument(isValidSex(sex), MESSAGE_CONSTRAINTS);
        value = sex;
    }

    public static boolean isValidSex(String test) {
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
        if (!(other instanceof Sex)) {
            return false;
        }

        Sex otherSex = (Sex) other;
        return value.equals(otherSex.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
