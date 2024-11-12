package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's sex in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSex(String)} (String)}
 */
public class Sex {
    public static final String MESSAGE_CONSTRAINTS =
            "The sex should be either F or M, and it should not be blank";

    public final String sexName;

    /**
     * Constructs a {@code Sex}.
     *
     * @param sex A valid sex.
     */
    public Sex(String sex) {
        requireNonNull(sex);
        checkArgument(isValidSex(sex), MESSAGE_CONSTRAINTS);
        sexName = sex;
    }

    /**
     * Returns true if a given string is a valid sex.
     */
    public static boolean isValidSex(String test) {
        return test.toLowerCase().equals("f")
                || test.toLowerCase().equals("m");
    }

    @Override
    public String toString() {
        return sexName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.address.model.person.Sex)) {
            return false;
        }

        seedu.address.model.person.Sex otherSex = (seedu.address.model.person.Sex) other;
        return sexName.toLowerCase().equals(otherSex.sexName.toLowerCase());
    }

    @Override
    public int hashCode() {
        return sexName.hashCode();
    }
}
